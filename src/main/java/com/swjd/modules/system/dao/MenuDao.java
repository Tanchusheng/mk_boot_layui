package com.swjd.modules.system.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.swjd.modules.system.entity.Menu;
import com.swjd.modules.system.entity.vo.ShowMenu;

import java.util.List;
import java.util.Map;

/**
 * @ClassName MenuDao
 * @Description TODO
 * @Author Tan
 * @Date 2020/5/17 16:59
 * @Version 1.0
 */
public interface MenuDao extends BaseMapper<Menu> {

    List<ShowMenu> queryShowMenuByUser(Map<String,Object> map);

    List<Menu> getMenus(Map<String,Object> map);
}
