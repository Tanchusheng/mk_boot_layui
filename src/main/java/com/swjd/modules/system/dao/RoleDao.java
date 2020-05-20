package com.swjd.modules.system.dao;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.swjd.modules.system.entity.Menu;
import com.swjd.modules.system.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

/**
 * @Description:
 * @Author: Tan.c.s
 * @Date: Created in 2020/5/19 9:50
 * @Version：1.0
 */
public interface RoleDao extends BaseMapper<Role> {

    //保存角色和菜单关系
    void saveRoleMenus(@Param("roleId") Long id,@Param("menus") Set<Menu> menus);

    //删除角色和菜单关系
    void dropRoleMenus(@Param("roleId") Long roleId);

    //根据角色Id获取角色信息
    Role selectRoleById(@Param("id") Long id);

    //删除角色和用户的关系
    void dropRoleUsers(@Param("roleId") Long roleId);
}
