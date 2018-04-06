package com.wangsen.user.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

/**
 * <p>
 * 权限表
 * </p>
 *
 * @author wangsen
 * @since 2018-03-25
 */
public class SysPermission implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 权限名称
     */
    private String name;
    /**
     * 权限代码
     */
    private String code;
    /**
     * 描述
     */
    private String description;
    /**
     * 父id
     */
    private Long parentId;
    /**
     * 状态，0：可用，1：不可用
     */
    private Integer status;
    /**
     * 所属应用id
     */
    private Long appId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    @Override
    public String toString() {
        return "SysPermission{" +
        ", id=" + id +
        ", name=" + name +
        ", code=" + code +
        ", description=" + description +
        ", parentId=" + parentId +
        ", status=" + status +
        ", appId=" + appId +
        "}";
    }
}
