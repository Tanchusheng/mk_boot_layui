package com.swjd.modules.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.swjd.modules.system.entity.Menu;
import com.swjd.modules.system.entity.vo.ShowMenu;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: Tan.c.s
 * @Date: Created in 2020/5/18 10:14
 * @Version：1.0
 */
public interface MenuService extends IService<Menu> {

    List<ShowMenu> getShowMenuByUser(Long id);

    List<Menu> selectAllMenus(Map<String,Object> map);

    int getCountByName(String name);

    int getCountByPermission(String permission);

    void saveOrUpdateMenu(Menu menu);
}
