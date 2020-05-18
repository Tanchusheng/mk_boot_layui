package com.swjd.base;

import com.swjd.modules.system.entity.User;
import com.swjd.modules.system.service.MenuService;
import com.swjd.modules.system.service.UserService;
import com.swjd.realm.AuthRealm.ShiroUser;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description:
 * @Author: Tan.c.s
 * @Date: Created in 2020/5/18 10:15
 * @Version：1.0
 */
public class BaseController {

    @Autowired
    protected UserService userService;

    @Autowired
    protected MenuService menuService;

//    @Autowired
//    protected RoleService roleService;
//
//    @Autowired
//    protected LogService logService;

    /**
     * 功能描述：获取当前用户信息
     * @return
     */
    public User getCurrentUser(){

        ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        if(null==shiroUser){
            return null ;
        }

        User loginUser = userService.selectById(shiroUser.getId());
        return loginUser;
    }
}

