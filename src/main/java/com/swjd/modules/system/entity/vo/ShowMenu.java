package com.swjd.modules.system.entity.vo;

import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.List;

/**
 * @Description:菜单辅助类
 * @Author: Tan.c.s
 * @Date: Created in 2020/5/8 16:07
 * @Version：1.0
 */
public class ShowMenu implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    /*父ID*/
    private Long pid;

    /*标题*/
    private String title;

    private String icon;

    private String href;

    /*是否展开*/
    private Boolean spread = false;

    private List<ShowMenu> children = Lists.newArrayList();

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

    public List<ShowMenu> getChildren() {
        return children;
    }

    public void setChildren(List<ShowMenu> children) {
        this.children = children;
    }
}
