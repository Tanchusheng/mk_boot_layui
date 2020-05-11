package com.swjd.realm;


import com.google.common.base.Objects;
import com.google.common.collect.Sets;
import com.swjd.modules.system.entity.Menu;
import com.swjd.modules.system.entity.Role;
import com.swjd.modules.system.entity.User;
import com.swjd.modules.system.service.UserService;
import com.swjd.util.Constants;
import com.swjd.util.Encodes;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.Set;

/**
 * @Description:自定义登录认证、权限认证
 * @Author: Tan.c.s
 * @Date: Created in 2020/5/9 16:27
 * @Version：1.0
 */
@Component(value = "authRealm")
public class AuthRealm extends AuthorizingRealm {
    @Autowired
    @Lazy
    private UserService userService;

    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        ShiroUser shiroUser = (ShiroUser)principalCollection.getPrimaryPrincipal();
        User user = userService.findUserByLoginName(shiroUser.loginName);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Set<Role> roles = user.getRoleLists();
        Set<String> roleNames = Sets.newHashSet();
        for(Role role:roles){
            if(StringUtils.isNotBlank(role.getName())){
                roleNames.add(role.getName());
            }
        }

        Set<Menu> menus = user.getMenus();

        Set<String> permissions = Sets.newHashSet();
        for (Menu menu:menus){
            if(StringUtils.isNotBlank(menu.getPermission())){
                permissions.add(menu.getPermission());
            }
        }
        info.setRoles(roleNames);
        info.setStringPermissions(permissions);
        return info;
    }


    /**
     * 认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = (String)token.getPrincipal();
        User user = userService.findUserByLoginName(username);

        if(null==user){
            throw new UnknownAccountException();//没找到帐号
        }
        if(Boolean.TRUE.equals(user.getLocked())) {
            throw new LockedAccountException(); //帐号锁定
        }

        byte[] salt = Encodes.decodeHex(user.getSalt());
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                new ShiroUser(user.getId(),user.getLoginName(),user.getNickName(), user.getIcon()),
                user.getPassword(), //密码
                ByteSource.Util.bytes(salt),
                getName()  //realm name
        );
        return authenticationInfo;
    }

    public void removeUserAuthorizationInfoCache(String username) {
        SimplePrincipalCollection pc = new SimplePrincipalCollection();
        pc.add(username, super.getName());
        super.clearCachedAuthorizationInfo(pc);
    }

    /**
     * 设定Password校验的Hash算法与迭代次数.
     */
    @PostConstruct
    public void initCredentialsMatcher() {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(Constants.HASH_ALGORITHM);
        matcher.setHashIterations(Constants.HASH_INTERATIONS);
        setCredentialsMatcher(matcher);
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息.
     */
    public static class ShiroUser implements Serializable {
        private static final long serialVersionUID = -1373760761780840081L;
        public Long id;
        public String loginName;
        public String nickName;
        public String icon;

        public ShiroUser(Long id, String loginName, String nickName,String icon) {
            this.id = id;
            this.loginName = loginName;
            this.nickName = nickName;
            this.icon=icon;
        }

        public String getloginName() {
            return loginName;
        }
        public String getNickName() {
            return nickName;
        }
        public String getIcon() {
            return icon;
        }
        public Long getId() {
            return id;
        }



        /**
         * 本函数输出将作为默认的<shiro:principal/>输出.
         */
        @Override
        public String toString() {
            return nickName;
        }

        /**
         * 重载hashCode,只计算loginName;
         */
        @Override
        public int hashCode() {
            return Objects.hashCode(loginName);
        }

        /**
         * 重载equals,只计算loginName;
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            ShiroUser other = (ShiroUser) obj;
            if (loginName == null) {
                return other.loginName == null;
            } else return loginName.equals(other.loginName);
        }
    }
}
