package com.swjd.modules.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.swjd.modules.system.entity.Role;
import com.swjd.modules.system.entity.User;

import java.util.Set;

/**
 * @Description:用户信息 服务类
 * @Author: Tan.c.s
 * @Date: Created in 2020/5/9 16:57
 * @Version：1.0
 */
public interface UserService extends IService<User> {

    User findUserByLoginName(String name);

    User queryUserById(Long id);

    int userCount(String param);

    User saveUser(User user);

    //保存用户和角色的关系
    void saveUserRoles(Long id, Set<Role> roleSet);

    //更新用户信息
    User updateUser(User user);

    //删除用户和角色关系
    void  dropUserRolesByUserId(Long id);

    //删除单条记录
    void deleteUser(User user);
}
