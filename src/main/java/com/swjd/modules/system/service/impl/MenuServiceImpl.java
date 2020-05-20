package com.swjd.modules.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.swjd.modules.system.dao.MenuDao;
import com.swjd.modules.system.entity.Menu;
import com.swjd.modules.system.entity.vo.ShowMenu;
import com.swjd.modules.system.service.MenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName MenuServiceImpl
 * @Description TODO
 * @Author Tan
 * @Date 2020/5/17 16:58
 * @Version 1.0
 */
@Service
@Transactional(readOnly = true,rollbackFor = Exception.class)
public class MenuServiceImpl extends ServiceImpl<MenuDao, Menu> implements MenuService {
    @Override
    public List<ShowMenu> getShowMenuByUser(Long id) {
        Map<String,Object> map = new HashMap<>();
        map.put("userId",id);
        map.put("parentId",null);
        return baseMapper.queryShowMenuByUser(map);
    }

    @Override
    public List<Menu> queryAllMenu(Map<String, Object> map) {
        return baseMapper.getMenus(map);
    }

    @Override
    public int getCountByName(String name) {
        EntityWrapper<Menu> wrapper = new EntityWrapper();
        wrapper.eq("del_flag",false);
        wrapper.eq("name",name);
        return baseMapper.selectCount(wrapper);
    }

    @Override
    public int getCountByPermission(String permission) {
        EntityWrapper<Menu> wrapper = new EntityWrapper();
        wrapper.eq("del_flag",false);
        wrapper.eq("permission",permission);
        return baseMapper.selectCount(wrapper);
    }

    @Transactional(readOnly = false,rollbackFor = Exception.class)
    @Override
    public void saveOrUpdateMenu(Menu menu) {
        insertOrUpdate(menu);
    }
}
