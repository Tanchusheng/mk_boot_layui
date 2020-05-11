package com.swjd.modules.system.entity.vo;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * @Description:树形菜单
 * @Author: Tan.c.s
 * @Date: Created in 2020/5/8 16:10
 * @Version：1.0
 */
public class TreeMenu {
    private Long id;

    /*父ID*/
    private Long pid;

    /*标题*/
    private String title;

    private String icon;

    private String href;

    /*是否展开*/
    private Boolean spread = false;

    List<TreeMenu> children = Lists.newArrayList();

    public TreeMenu(Boolean spread) {
        this.spread = false;
    }

    public TreeMenu(Long id, Long pid, String title,String icon, String href) {
        this.id = id;
        this.pid = pid;
        this.title = title;
        this.icon = icon;
        this.href = href;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public Boolean getSpread() {
        return spread;
    }

    public void setSpread(Boolean spread) {
        this.spread = spread;
    }

    public List<TreeMenu> getChildren() {
        return children;
    }

    public void setChildren(List<TreeMenu> children) {
        this.children = children;
    }
}
