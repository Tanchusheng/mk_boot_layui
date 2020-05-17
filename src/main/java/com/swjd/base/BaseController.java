package com.swjd.base;

import com.swjd.modules.system.entity.User;
import com.swjd.modules.system.service.MenuService;
import com.swjd.modules.system.service.UserService;
import com.swjd.realm.AuthRealm.ShiroUser;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName BaseController
 * @Description TODO
 * @Author Tan
 * @Date 2020/5/17 16:45
 * @Version 1.0
 */
public class BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    protected MenuService menuService;
    /**
     * 功能描述:获取当前用户信息
     * @Param: []
     * @return: User
     **/
    public User getCurrentUser(){
        ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        if (shiroUser==null){
            return null;
        }

        User loginUser =userService.queryUserById(shiroUser.getId());
        return loginUser;
    }
}
