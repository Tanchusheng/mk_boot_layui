package com.swjd.modules.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.swjd.modules.system.dao.UserDao;
import com.swjd.modules.system.entity.Role;
import com.swjd.modules.system.entity.User;
import com.swjd.modules.system.service.UserService;
import com.swjd.util.ToolUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Description:
 * @Author: Tan.c.s
 * @Date: Created in 2020/5/9 17:09
 * @Version：1.0
 */
@Service("userService")
@Transactional(readOnly = true ,rollbackFor = Exception.class)
public class UserServiceImpl extends ServiceImpl<UserDao,User> implements UserService{
    @Override
    public User findUserByLoginName(String name) {
        Map<String,Object> map= new HashMap<>();
        map.put("loginName",name);
        User user=baseMapper.queryUserByMap(map);
        return user;
    }

    @Override
    public User queryUserById(Long id) {
        Map<String,Object> map = new HashMap<>();
        map.put("id",id);
        return baseMapper.queryUserByMap(map);
    }
    /**
     * 功能描述：统计登录名称
     * @param param
     * @return
     */
    @Override
    public int userCount(String param) {
        EntityWrapper<User> wrapper = new EntityWrapper<>();
        wrapper.eq("login_name",param).or().eq("email",param).or().eq("tel",param);
        int count = baseMapper.selectCount(wrapper);
        return count;
    }

    /**
     * 功能描述：保存用户信息
     * @param user
     * @return
     */
    @Override
    @Transactional(readOnly = false,rollbackFor = Exception.class)
    public User saveUser(User user) {
        ToolUtil.entryptPassword(user);
        user.setLocked(false);
        baseMapper.insert(user);
        return queryUserById(user.getId());
    }

    /**
     * 保存用户和角色关系
     * @param id
     * @param roleSet
     */
    @Override
    @Transactional(readOnly = false,rollbackFor = Exception.class)
    public void saveUserRoles(Long id, Set<Role> roleSet) {
        baseMapper.saveUserRoles(id,roleSet);
    }

    /**
     * 功能描述：更新用户信息
     * @param user
     * @return
     */
    @Override
    @Transactional(readOnly = false,rollbackFor = Exception.class)
    public User updateUser(User user) {
        baseMapper.updateById(user);
        return user;
    }

    /**
     * 功能描述：删除用户和角色关系
     * @param id
     */
    @Override
    @Transactional(readOnly = false,rollbackFor = Exception.class)
    public void dropUserRolesByUserId(Long id) {
        baseMapper.dropUserRolesByUserId(id);
    }

    /**
     * 删除用户信息（单条）
     * @param user
     */
    @Override
    @Transactional(readOnly = false,rollbackFor = Exception.class)
    public void deleteUser(User user) {
        user.setDelFlag(true);
        user.updateById();
    }

}
