package com.swjd.base;

import com.swjd.realm.AuthRealm;
import org.apache.shiro.SecurityUtils;

/**
 * @Description:用户信息
 * @Author: Tan.c.s
 * @Date: Created in 2020/5/9 8:38
 * @Version：1.0
 */
public class SysUser {
    public static String icon() {
        return ShiroUser().getIcon();
    }

    public static Long id() {
        return ShiroUser().getId();
    }

    public static String loginName() {
        return ShiroUser().getloginName();
    }

    public static String nickName(){
        return ShiroUser().getNickName();
    }

    public static AuthRealm.ShiroUser ShiroUser() {
        AuthRealm.ShiroUser user = (AuthRealm.ShiroUser) SecurityUtils.getSubject().getPrincipal();
        return user;
    }
}
