package com.swjd.modules.system.service.impl;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.swjd.modules.system.dao.RoleDao;
import com.swjd.modules.system.entity.Role;
import com.swjd.modules.system.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description:
 * @Author: Tan.c.s
 * @Date: Created in 2020/5/19 9:49
 * @Version：1.0
 */
@Service
@Transactional(readOnly = true,rollbackFor = Exception.class)
public class RoleServiceImpl extends ServiceImpl<RoleDao, Role> implements RoleService {

    /**
     * 根据角色名称统计记录
     *
     * @param name
     * @return
     */
    @Override
    public int getRoleNameCount(String name) {
        EntityWrapper<Role> wrapper = new EntityWrapper();
        wrapper.eq("name", name);
        return baseMapper.selectCount(wrapper);
    }

    /**
     * 功能描述：保存角色信息
     *
     * @param role
     * @return
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    @Override
    public Role saveRole(Role role) {
        baseMapper.insert(role);
        //保存角色所具有的权限菜单
        baseMapper.saveRoleMenus(role.getId(), role.getMenuSet());
        return role;
    }

    /**
     * 功能描述：更新角色信息
     *
     * @param role
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    @Override
    public void updateRole(Role role) {
        baseMapper.updateById(role);
        baseMapper.dropRoleMenus(role.getId());
        baseMapper.saveRoleMenus(role.getId(), role.getMenuSet());
    }

    /**
     * 功能描述：根据角色Id获取角色信息
     *
     * @param id
     * @return
     */
    @Override
    public Role getRoleById(Long id) {
        Role role = baseMapper.selectRoleById(id);
        return role;
    }

    /**
     * 功能描述：删除角色信息
     *
     * @param role
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    @Override
    public void deleteRole(Role role) {
        role.setDelFlag(true);
        //更新角色
        baseMapper.updateById(role);
        //删除角色和菜单关系
        baseMapper.dropRoleMenus(role.getId());
        //删除角色和用户的关系
        baseMapper.dropRoleUsers(role.getId());
    }


    /**
     * 功能描述：获取所有角色列表
     *
     * @return
     */
    @Override
    public List<Role> selectAll() {
        EntityWrapper<Role> wrapper = new EntityWrapper<>();
        wrapper.eq("del_flag", false);
        List<Role> roleList = baseMapper.selectList(wrapper);
        return roleList;
    }
}
