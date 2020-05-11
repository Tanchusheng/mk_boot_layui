package com.swjd.base;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;

import java.util.List;

/**
 * @Description:数据entity类
 * @Author: Tan.c.s
 * @Date: Created in 2020/5/8 15:53
 * @Version：1.0
 */
public class TreeEntity <T extends Model> extends DataEntity<T>{
    private static final long serialVersionUID = 1L;

    /**
     * varchar(64) NULL父id
     */
    @TableField(value = "parent_id")
    protected Long parentId;

    /**
     * 节点层次（第一层，第二层，第三层....）
     */
    protected Integer level;

    /**
     * varchar(1000) NULL路径
     */
    @TableField(value = "parent_ids")
    protected String parentIds;

    /**
     * int(11) NULL排序
     */
    protected Integer sort;

    @TableField(exist = false)
    protected List<T> children;

    @TableField(exist = false)
    protected T parentTree;

    public TreeEntity() {
        super();
        this.sort = 30;
    }

    public TreeEntity(Long id) {
        super(id);
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public List<T> getChildren() {
        return children;
    }

    public void setChildren(List<T> children) {
        this.children = children;
    }

    public T getParentTree() {
        return parentTree;
    }

    public void setParentTree(T parentTree) {
        this.parentTree = parentTree;
    }
}
