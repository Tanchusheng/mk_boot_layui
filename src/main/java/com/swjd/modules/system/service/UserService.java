package com.swjd.modules.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.swjd.modules.system.entity.User;

/**
 * @Description:用户信息 服务类
 * @Author: Tan.c.s
 * @Date: Created in 2020/5/9 16:57
 * @Version：1.0
 */
public interface UserService extends IService<User> {

    User findUserByLoginName(String name);

    User findUserById(Long id);
}
