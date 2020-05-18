package com.swjd.modules.system.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.swjd.modules.system.entity.Menu;
import com.swjd.modules.system.entity.vo.ShowMenu;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: Tan.c.s
 * @Date: Created in 2020/5/18 10:13
 * @Version：1.0
 */
public interface MenuDao extends BaseMapper<Menu> {

    List<ShowMenu> selectShowMenuByUser(Map<String,Object> map);

    List<Menu> getMenus(Map map);
}
