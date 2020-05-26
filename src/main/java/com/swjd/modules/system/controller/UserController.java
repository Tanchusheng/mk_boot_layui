package com.swjd.modules.system.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Maps;
import com.swjd.base.BaseController;
import com.swjd.base.SysUser;
import com.swjd.modules.system.entity.Role;
import com.swjd.modules.system.entity.User;
import com.swjd.modules.system.entity.vo.ShowMenu;
import com.swjd.redis.CacheUtils;
import com.swjd.util.Constants;
import com.swjd.util.LayerData;
import com.swjd.util.RestResponse;
import com.swjd.util.ToolUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName UserController
 * @Description TODO
 * @Author Tan
 * @Date 2020/5/17 16:43
 * @Version 1.0
 */
@Controller
@RequestMapping("api/system/user")
public class UserController extends BaseController {

    /**
     * 功能描述:获取用户所拥有的菜单列表
     * @Param: []
     * @return: com.swjd.util.RestResponse
     **/
    @RequestMapping("/getUserMenu")
    @ResponseBody
    public RestResponse getUserMenu(){
        Long userId = SysUser.id();
        List<ShowMenu> menulist = menuService.getShowMenuByUser(userId);
        return RestResponse.success().setData(menulist);
    }

    /**
     * 功能描述:获取用户列表
     * @Date: 22:06 2020/5/22
     * @Param: [page, limit, request]
     * @return: com.swjd.util.RestResponse
     **/
    @RequiresPermissions("sys:user:list")
    @RequestMapping("list")
    @ResponseBody
    public LayerData<User> queryUser(@RequestParam(value = "page",defaultValue = "1") Integer page,
                                     @RequestParam(value = "limit",defaultValue = "10") Integer limit,
                                     ServletRequest request){
        Map map = WebUtils.getParametersStartingWith(request,"s_");
        LayerData<User> userLayerData = new LayerData<>();
        EntityWrapper<User> userEntityWrapper = new EntityWrapper();
        if(!map.isEmpty()){
            String keys = (String)map.get("key");
            if(StringUtils.isNotBlank(keys)){
                userEntityWrapper.like("login_name",keys).or().like("tel", keys).or().like("email", keys);
            }
        }
        Page<User> userPage = userService.selectPage(new Page<>(page,limit),userEntityWrapper);
        userLayerData.setCount(userPage.getTotal());
        userLayerData.setData(userPage.getRecords());
        return userLayerData;
    }

    /**
     * 功能描述：获取所有角色列表
     * @return
     */
    @GetMapping("getAllRoles")
    @ResponseBody
    public RestResponse getAllRoles(){
        List<Role> roleList = roleService.selectAll();
        return RestResponse.success().setData(roleList);
    }

    /**
     * 功能描述：添加用户信息
     * @param user
     * @return
     */
    @RequiresPermissions("sys:user:add")
    @PostMapping("add")
    @ResponseBody
    public RestResponse add(@RequestBody User user){
        if(StringUtils.isBlank(user.getLoginName())){
            return RestResponse.failure("登录名不能为空");
        }

        if(user.getRoleLists()==null||user.getRoleLists().size()==0){
            return  RestResponse.failure("用户角色至少选择一个");
        }

        if(userService.userCount(user.getLoginName())>0){
            return RestResponse.failure("登录名称已经存在");
        }

        if(StringUtils.isNotBlank(user.getEmail())){
            if(userService.userCount(user.getEmail())>0){
                return RestResponse.failure("该邮箱已被使用");
            }
        }

        if(StringUtils.isNoneBlank(user.getTel())){
            if(userService.userCount(user.getTel())>0){
                return RestResponse.failure("该手机号已被绑定");
            }
        }

        user.setPassword(Constants.DEFAULT_PASSWORD);
        userService.saveUser(user);
        if(user.getId() == null || user.getId() == 0){
            return RestResponse.failure("保存用户信息出错");
        }

        //保存用户角色关系
        userService.saveUserRoles(user.getId(),user.getRoleLists());
        return RestResponse.success();

    }

    /**
     * 功能描述：根据用户id获取角色
     * @param id
     * @return
     */
    @GetMapping("getRolesByUser")
    @ResponseBody
    public RestResponse getRolesByUser(Long id){
        User user = userService.queryUserById(id);
        StringBuffer roleIds = new StringBuffer();
        if(user!=null){
            Set<Role> roleSet = user.getRoleLists();
            if (roleSet != null && roleSet.size() > 0) {
                for (Role r : roleSet) {
                    roleIds.append(r.getId().toString()).append(",");
                }
            }
        }

        List<Role> roleList = roleService.selectAll();
        Map<String,Object> resultMap = Maps.newHashMap();
        resultMap.put("roleIds",roleIds);
        resultMap.put("roleList",roleList);
        return RestResponse.success().setData(resultMap);

    }

    /**
     * 功能描述：更新用户信息
     * @param user
     * @return
     */
    @RequiresPermissions("sys:user:edit")
    @PostMapping("edit")
    @ResponseBody
    public RestResponse edit(@RequestBody User user){
        if(user.getId() == 0 || user.getId() == null){
            return RestResponse.failure("用户ID不能为空");
        }

        if(StringUtils.isBlank(user.getLoginName())){
            return RestResponse.failure("登录名不能为空");
        }

        if(user.getRoleLists() == null || user.getRoleLists().size() == 0){
            return  RestResponse.failure("用户角色至少选择一个");
        }

        User oldUser = userService.queryUserById(user.getId());
        if(StringUtils.isNotBlank(user.getEmail())){
            if(!user.getEmail().equals(oldUser.getEmail())){
                if(userService.userCount(user.getEmail())>0){
                    return RestResponse.failure("该邮箱已被使用");
                }
            }
        }

        if(StringUtils.isNotBlank(user.getLoginName())){
            if(!user.getLoginName().equals(oldUser.getLoginName())) {
                if (userService.userCount(user.getLoginName()) > 0) {
                    return RestResponse.failure("该登录名已存在");
                }
            }
        }

        if(StringUtils.isNotBlank(user.getTel())){
            if(!user.getTel().equals(oldUser.getTel())) {
                if (userService.userCount(user.getTel()) > 0) {
                    return RestResponse.failure("该手机号已经被绑定");
                }
            }
        }

        userService.updateUser(user);
        //先解除用户跟角色的关系
        userService.dropUserRolesByUserId(user.getId());
        if(user.getId() == null || user.getId() == 0){
            return RestResponse.failure("保存用户信息出错");
        }

        userService.saveUserRoles(user.getId(),user.getRoleLists());
        return RestResponse.success();

    }

    /**
     * 功能描述：删除用户信息（单条记录）
     * @param id
     * @return
     */
    @RequiresPermissions("sys:user:delete")
    @PostMapping("delete")
    @ResponseBody
    public RestResponse delete(@RequestParam(value = "id",required = false)Long id){

        if(id==null|| id==0||id==1){
            return RestResponse.failure("参数错误");
        }

        User user = userService.queryUserById(id);

        if(user==null){
            return RestResponse.failure("用户不存在");

        }

        userService.deleteUser(user);
        return RestResponse.success();
    }

    /**
     * 功能描述：批量删除用户数据
     * @param users
     * @return
     */
    @RequiresPermissions("sys:user:delete")
    @PostMapping("deleteSome")
    @ResponseBody
    public RestResponse deleteSome(@RequestBody List<User> users){
        if(users == null || users.size()==0){
            return RestResponse.failure("请选择需要删除的用户");
        }

        for (User u :users){
            if(u.getId()==1){
                return RestResponse.failure("不能删除超级管理员");
            }else {
                userService.deleteUser(u);
            }
        }
        return RestResponse.success();
    }

    /**
     * 功能描述：修改用户密码
     * @param oldPwd
     * @param newPwd
     * @param confirmPwd
     * @return
     */
    @RequiresPermissions("sys:user:changePassword")
    @PostMapping("changePassword")
    @ResponseBody
    public RestResponse changePassword(@RequestParam(value = "oldPwd",required = false)String oldPwd,
                                       @RequestParam(value = "newPwd",required = false)String newPwd,
                                       @RequestParam(value = "confirmPwd",required = false)String confirmPwd){

        if(StringUtils.isBlank(oldPwd)){
            return RestResponse.failure("旧密码不能为空");
        }

        if(StringUtils.isBlank(newPwd)){
            return RestResponse.failure("新密码不能为空");
        }

        if(StringUtils.isBlank(confirmPwd)){
            return RestResponse.failure("确认密码不能为空");
        }

        if(!confirmPwd.equals(newPwd)){
            return RestResponse.failure("两次输入密码不一致");
        }

        User user = userService.queryUserById(SysUser.id());

        //输入的旧密码是否正确
        String pw = ToolUtil.entryptPassword(oldPwd,user.getSalt()).split(",")[0];
        if(!user.getPassword().equals(pw)){
            return RestResponse.failure("旧密码错误");
        }

        user.setPassword(newPwd);
        ToolUtil.entryptPassword(user);
        userService.updateUser(user);
        return RestResponse.success();

    }

    /**
     * 功能描述：用户个人信息修改
     * @param user
     * @return
     */
    @PostMapping("saveUserInfo")
    @ResponseBody
    public RestResponse saveUserInfo(User user){



        if (StringUtils.isBlank(user.getLoginName())){
            return RestResponse.failure("登录名不能为空");
        }

        User oldUser = userService.queryUserById(SysUser.id());

        if(StringUtils.isNotBlank(user.getEmail())){
            if(!user.getEmail().equals(oldUser.getEmail())){
                if(userService.userCount(user.getEmail())>0){
                    return RestResponse.failure("该邮箱已经使用");
                }
            }
        }

        if(StringUtils.isNotBlank(user.getTel())){
            if(!user.getTel().equals(oldUser.getTel())){
                if(userService.userCount(user.getTel())>0){
                    return RestResponse.failure("该手机号已经使用");
                }
            }
        }

        userService.updateUser(user);
        return RestResponse.success();

    }

    /**
     * 功能描述：清理用户缓存
     * @return
     */
    @GetMapping("clearUserCache")
    @ResponseBody
    public RestResponse clearUserCache(){
        CacheUtils cacheUtils = new CacheUtils();
        cacheUtils.clearUserCache();
        return RestResponse.success("清理缓存成功").setCode(0);
    }
}
