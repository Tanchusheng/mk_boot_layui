package com.swjd.modules.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.swjd.modules.system.entity.Menu;
import com.swjd.modules.system.entity.vo.ShowMenu;

import java.util.List;
import java.util.Map;

/**
 * @ClassName MenuService
 * @Description TODO
 * @Author Tan
 * @Date 2020/5/17 16:57
 * @Version 1.0
 */
public interface MenuService extends IService<Menu> {

    List<ShowMenu> getShowMenuByUser(Long id);

    List<Menu> queryAllMenu(Map<String,Object> map);

    int getCountByName(String name);

    int getCountByPermission(String permission);

    void saveOrUpdateMenu(Menu menu);
}
