package com.wangsen.user.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author wangsen
 * @since 2018-03-19
 */
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 登录用户名
     */
    private String userName;
    /**
     * 密码
     */
    private String password;
    /**
     * 真实姓名
     */
    private String realName;
    /**
     * 账号状态
     */
    private Integer status;
    /**
     * 微信openid
     */
    private String openId;
    /**
     * 来源哪个系统。(后期会进行与第三方系统做用户数据对接，默认为当前系统用户中心)
     */
    private Integer fromSystem;

    @TableField(value="create_time",fill = FieldFill.INSERT)
    private Date createTime;
    private Date updateTime;

    /**
     * 账号状态
     */
    private String salt;

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Integer getFromSystem() {
        return fromSystem;
    }

    public void setFromSystem(Integer fromSystem) {
        this.fromSystem = fromSystem;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "SysUser{" +
        ", id=" + id +
        ", userName=" + userName +
        ", status=" + status +
        ", openId=" + openId +
        ", fromSystem=" + fromSystem +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        "}";
    }
}
