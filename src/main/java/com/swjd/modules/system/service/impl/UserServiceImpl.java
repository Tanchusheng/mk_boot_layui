package com.swjd.modules.system.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Maps;
import com.swjd.modules.system.dao.UserDao;
import com.swjd.modules.system.entity.User;
import com.swjd.modules.system.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

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
    public User findUserById(Long id) {
        Map<String,Object> map = Maps.newHashMap();
        map.put("id",id);
        return baseMapper.queryUserByMap(map);
    }
}
