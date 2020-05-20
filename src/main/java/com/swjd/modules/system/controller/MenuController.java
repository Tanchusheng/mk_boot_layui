package com.swjd.modules.system.controller;

import com.baomidou.mybatisplus.mapper.Condition;
import com.swjd.base.BaseController;
import com.swjd.modules.system.entity.Menu;
import com.swjd.util.RestResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName MenuController
 * @Description TODO
 * @Author Tan
 * @Date 2020/5/17 17:34
 * @Version 1.0
 */
@RestController
@RequestMapping("/api/system/menu")
public class MenuController extends BaseController {

    /**
     * 功能描述:获取全部菜单列表
     * @Param: []
     * @return: com.swjd.util.RestResponse
     **/
    @RequestMapping("treeList")
    public RestResponse treeList(){
        Map<String,Object> map = new HashMap<>();
        map.put("parentId",null);
        map.put("delFlag",false);
        return RestResponse.success().setData(menuService.queryAllMenu(map));
    }
    /**
     * 功能描述:添加菜单，包括添加父级菜单和子菜单
     * @Param: [menu]
     * @return: com.swjd.util.RestResponse
     **/
    @RequiresPermissions("sys:menu:add")
    @RequestMapping("insertMenu")
    public RestResponse insertMenu(Menu menu){
        if(StringUtils.isBlank(menu.getName())){
            return RestResponse.failure("菜单名称不能为空");
        }

        if(menuService.getCountByName(menu.getName())>0){
            return RestResponse.failure("菜单名称已存在");
        }

        if(StringUtils.isNotBlank(menu.getPermission())){
            if(menuService.getCountByPermission(menu.getPermission())>0){
                return RestResponse.failure("权限标识已经存在");
            }
        }

        //处理菜单排序
        if(menu.getParentId()==null){
            menu.setLevel(1);
            Object o = menuService.selectObj(Condition.create().setSqlSelect("max(sort)").isNull("parent_id"));
            int sort = 0;
            if(null!=o){
                sort = (Integer) o+10;
            }
            menu.setSort(sort);
        }else{
            Menu parentMenu = menuService.selectById(menu.getParentId());
            if(null==parentMenu){
                return RestResponse.failure("父菜单不存在");
            }

            menu.setParentIds(parentMenu.getParentIds());
            menu.setLevel(parentMenu.getLevel()+1);
            Object o = menuService.selectObj(Condition.create()
                    .setSqlSelect("max(sort)")
                    .eq("parent_id",menu.getParentId()));
            int sort = 0;
            if(null!=o){
                sort = (Integer) o+10;
            }
            menu.setSort(sort);
        }
        menuService.saveOrUpdateMenu(menu);
        return RestResponse.success();
    }

    /**
     * 功能描述:编辑菜单
     * @Param: [menu]
     * @return: com.swjd.util.RestResponse
     **/
    @RequiresPermissions("sys:menu:edit")
    @RequestMapping("editMenu")
    public RestResponse editMenu(Menu menu){
        if(null==menu.getId()){
            return RestResponse.failure("菜单ID不能为空");
        }

        if (StringUtils.isBlank(menu.getName())) {
            return RestResponse.failure("菜单名称不能为空");
        }

        Menu oldMenu = menuService.selectById(menu.getId());

        if(!oldMenu.getName().equals(menu.getName())){
            if(menuService.getCountByName(menu.getName())>0){
                return RestResponse.failure("菜单名称已存在");
            }

        }

        if (StringUtils.isNotBlank(menu.getPermission())) {
            if(!oldMenu.getPermission().equals(menu.getPermission())) {
                if (menuService.getCountByPermission(menu.getPermission()) > 0) {
                    return RestResponse.failure("权限标识已经存在");
                }
            }
        }

        if(null==menu.getSort()){
            return RestResponse.failure("排序值不能为空");
        }

        menuService.saveOrUpdateMenu(menu);
        return RestResponse.success();

    }
    @RequiresPermissions("sys:menu:delete")
    @RequestMapping("deleteMenu")
    public RestResponse deleteMenu(@RequestParam(value = "id",required = true)Long id){
        if(null==id){
            return RestResponse.failure("菜单ID不能为空");
        }
        Menu menu = menuService.selectById(id);
        menu.setDelFlag(true);
        menuService.saveOrUpdateMenu(menu);

        return RestResponse.success();
    }

}
