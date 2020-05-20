package com.swjd.modules.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.swjd.modules.system.entity.Role;

import java.util.List;

/**
 * @Description:
 * @Author: Tan.c.s
 * @Date: Created in 2020/5/19 9:48
 * @Version：1.0
 */
public interface RoleService extends IService<Role> {

    //根据角色名称统计记录
    int getRoleNameCount(String name);

    //保存角色信息
    Role saveRole(Role role);

    //更新角色信息
    void updateRole(Role role);

    //根据角色Id获取角色信息
    Role getRoleById(Long id);

    //删除角色信息
    void deleteRole(Role role);

    //获取所有角色列表
    List<Role> selectAll();


}
