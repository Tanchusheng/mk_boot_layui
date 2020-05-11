package com.swjd.base;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.FieldStrategy;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @Description:
 * @Author: Tan.c.s
 * @Date: Created in 2020/5/8 14:06
 * @Version：1.0
 */
public abstract class DataEntity<T extends Model> extends BaseEntity<T> {
    private static final long serialVersionUID = 1L;

    /*创建者*/
    @TableField(value = "create_by",fill = FieldFill.INSERT)
    protected Long createId;

    /*创建时间*/
    @TableField(value = "create_date",fill = FieldFill.INSERT)
    protected Date createDate;

    /*修改人*/
    @TableField(value = "update_id",fill = FieldFill.INSERT_UPDATE)
    protected Long updateId;

    /*修改时间*/
    @TableField(value = "update_date",fill = FieldFill.INSERT_UPDATE)
    protected Date updateDate;

    /*删除标记  Y正常 N删除 A审核*/
    @TableField(value = "del_flag")
    protected Boolean delFlag;

    /*备注*/
    @TableField(strategy = FieldStrategy.IGNORED)
    protected String remarks;

    public DataEntity() {
        super();
        this.delFlag = false;
    }

    public DataEntity(Long id) {
        super(id);
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getCreateId() {
        return createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-mm-dd HH:mm:ss",timezone = "GMT+8")
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getUpdateId() {
        return updateId;
    }

    public void setUpdateId(Long updateId) {
        this.updateId = updateId;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-mm-dd HH:mm:ss",timezone = "GMT+8")
    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Boolean getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Boolean delFlag) {
        this.delFlag = delFlag;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
