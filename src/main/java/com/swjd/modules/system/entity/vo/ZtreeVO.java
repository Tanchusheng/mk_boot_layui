package com.swjd.modules.system.entity.vo;

import java.io.Serializable;
import java.util.List;

/**
 * @Description:树形菜单
 * @Author: Tan.c.s
 * @Date: Created in 2020/5/8 16:13
 * @Version：1.0
 */
public class ZtreeVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private Long pid;

    private String name;

    private String url;

    private Boolean open = true;

    private Boolean isParent;

    private String icon;

    private List<ZtreeVO> children;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public Boolean getParent() {
        return isParent;
    }

    public void setParent(Boolean parent) {
        isParent = parent;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<ZtreeVO> getChildren() {
        return children;
    }

    public void setChildren(List<ZtreeVO> children) {
        this.children = children;
    }
}
