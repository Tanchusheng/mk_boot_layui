package com.swjd.modules.system.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.swjd.modules.system.entity.User;

import java.util.Map;

/**
 * @Description:
 * @Author: Tan.c.s
 * @Date: Created in 2020/5/9 17:18
 * @Version：1.0
 */
public interface UserDao extends BaseMapper<User> {
    User queryUserByMap(Map<String,Object> map);
}
