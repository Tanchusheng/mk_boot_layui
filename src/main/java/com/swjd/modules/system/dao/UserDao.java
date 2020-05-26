package com.swjd.modules.system.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.swjd.modules.system.entity.Role;
import com.swjd.modules.system.entity.User;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.Map;
import java.util.Set;

/**
 * @Description:
 * @Author: Tan.c.s
 * @Date: Created in 2020/5/9 17:18
 * @Version：1.0
 */
public interface UserDao extends BaseMapper<User> {
    User queryUserByMap(Map<String,Object> map);

    //保存用户和角色关系
    void  saveUserRoles(@Param("userId") Long id, @Param("roleIds") Set<Role> roles);

    void dropUserRolesByUserId(@Param("userId") Long userId);


}
