package com.xkcoding.springbootdemorabcshiromybatis.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "mybatis_shiro_user")
public class MybatisShiroUser {
    /**
     * 用户id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 加密后的密码
     */
    private String password;

    /**
     * 密码加密的盐值
     */
    private String salt;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 真实姓名
     */
    private String realname;

    /**
     * 手机号
     */
    private String telephone;

    /**
     * 邮箱
     */
    private String mail;

    /**
     * 用户所在部门的id
     */
    @Column(name = "dept_id")
    private Integer deptId;

    /**
     * 状态，-1：删除，0：冻结状态，1：正常
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 最后一次登录的时间
     */
    @Column(name = "last_time")
    private Date lastTime;

    /**
     * 最后一次登录的ip地址
     */
    @Column(name = "last_ip")
    private String lastIp;

    /**
     * 操作者
     */
    private String operator;

    /**
     * 最后一次更新时间
     */
    @Column(name = "operate_time")
    private Date operateTime;

    /**
     * 最后一次更新操作者的ip地址
     */
    @Column(name = "operate_ip")
    private String operateIp;

    /**
     * 获取用户id
     *
     * @return id - 用户id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置用户id
     *
     * @param id 用户id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取用户名
     *
     * @return username - 用户名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置用户名
     *
     * @param username 用户名
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取加密后的密码
     *
     * @return password - 加密后的密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置加密后的密码
     *
     * @param password 加密后的密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取密码加密的盐值
     *
     * @return salt - 密码加密的盐值
     */
    public String getSalt() {
        return salt;
    }

    /**
     * 设置密码加密的盐值
     *
     * @param salt 密码加密的盐值
     */
    public void setSalt(String salt) {
        this.salt = salt;
    }

    /**
     * 获取头像
     *
     * @return avatar - 头像
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * 设置头像
     *
     * @param avatar 头像
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    /**
     * 获取真实姓名
     *
     * @return realname - 真实姓名
     */
    public String getRealname() {
        return realname;
    }

    /**
     * 设置真实姓名
     *
     * @param realname 真实姓名
     */
    public void setRealname(String realname) {
        this.realname = realname;
    }

    /**
     * 获取手机号
     *
     * @return telephone - 手机号
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * 设置手机号
     *
     * @param telephone 手机号
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     * 获取邮箱
     *
     * @return mail - 邮箱
     */
    public String getMail() {
        return mail;
    }

    /**
     * 设置邮箱
     *
     * @param mail 邮箱
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     * 获取用户所在部门的id
     *
     * @return dept_id - 用户所在部门的id
     */
    public Integer getDeptId() {
        return deptId;
    }

    /**
     * 设置用户所在部门的id
     *
     * @param deptId 用户所在部门的id
     */
    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    /**
     * 获取状态，-1：删除，0：冻结状态，1：正常
     *
     * @return status - 状态，-1：删除，0：冻结状态，1：正常
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态，-1：删除，0：冻结状态，1：正常
     *
     * @param status 状态，-1：删除，0：冻结状态，1：正常
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取备注
     *
     * @return remark - 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注
     *
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取最后一次登录的时间
     *
     * @return last_time - 最后一次登录的时间
     */
    public Date getLastTime() {
        return lastTime;
    }

    /**
     * 设置最后一次登录的时间
     *
     * @param lastTime 最后一次登录的时间
     */
    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }

    /**
     * 获取最后一次登录的ip地址
     *
     * @return last_ip - 最后一次登录的ip地址
     */
    public String getLastIp() {
        return lastIp;
    }

    /**
     * 设置最后一次登录的ip地址
     *
     * @param lastIp 最后一次登录的ip地址
     */
    public void setLastIp(String lastIp) {
        this.lastIp = lastIp;
    }

    /**
     * 获取操作者
     *
     * @return operator - 操作者
     */
    public String getOperator() {
        return operator;
    }

    /**
     * 设置操作者
     *
     * @param operator 操作者
     */
    public void setOperator(String operator) {
        this.operator = operator;
    }

    /**
     * 获取最后一次更新时间
     *
     * @return operate_time - 最后一次更新时间
     */
    public Date getOperateTime() {
        return operateTime;
    }

    /**
     * 设置最后一次更新时间
     *
     * @param operateTime 最后一次更新时间
     */
    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    /**
     * 获取最后一次更新操作者的ip地址
     *
     * @return operate_ip - 最后一次更新操作者的ip地址
     */
    public String getOperateIp() {
        return operateIp;
    }

    /**
     * 设置最后一次更新操作者的ip地址
     *
     * @param operateIp 最后一次更新操作者的ip地址
     */
    public void setOperateIp(String operateIp) {
        this.operateIp = operateIp;
    }
}