package com.swjd.modules.system.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.swjd.base.DataEntity;

import java.util.Set;

/**
 * @Description:角色实体类
 * @Author: Tan.c.s
 * @Date: Created in 2020/5/8 16:02
 * @Version：1.0
 */
@TableName("sys_role")
public class Role extends DataEntity<Role> {
    private static final long serialVersionUID = 1L;

    /**
     * 角色名称
     */
    private String name;

    @TableField(exist = false)
    private Set<Menu> menuSet;

    @TableField(exist = false)
    private Set<User> userSet;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Menu> getMenuSet() {
        return menuSet;
    }

    public void setMenuSet(Set<Menu> menuSet) {
        this.menuSet = menuSet;
    }

    public Set<User> getUserSet() {
        return userSet;
    }

    public void setUserSet(Set<User> userSet) {
        this.userSet = userSet;
    }

}
