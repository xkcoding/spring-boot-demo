package com.xkcoding.springbootdemorabcshiromybatis.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "mybatis_shiro_log")
public class MybatisShiroLog {
    /**
     * 日志记录id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 权限更新的类型，1：部门，2：用户，3：权限模块，4：权限，5：角色，6：角色用户关系，7：角色权限关系
     */
    private Integer type;

    /**
     * 基于type后指定的对象id，比如用户、权限、角色等表的主键
     */
    @Column(name = "target_id")
    private Integer targetId;

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
     * 当前是否复原过，0：没有复原过，1：复原过
     */
    private Integer status;

    /**
     * 旧值
     */
    @Column(name = "old_value")
    private String oldValue;

    /**
     * 新值
     */
    @Column(name = "new_value")
    private String newValue;

    /**
     * 获取日志记录id
     *
     * @return id - 日志记录id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置日志记录id
     *
     * @param id 日志记录id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取权限更新的类型，1：部门，2：用户，3：权限模块，4：权限，5：角色，6：角色用户关系，7：角色权限关系
     *
     * @return type - 权限更新的类型，1：部门，2：用户，3：权限模块，4：权限，5：角色，6：角色用户关系，7：角色权限关系
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置权限更新的类型，1：部门，2：用户，3：权限模块，4：权限，5：角色，6：角色用户关系，7：角色权限关系
     *
     * @param type 权限更新的类型，1：部门，2：用户，3：权限模块，4：权限，5：角色，6：角色用户关系，7：角色权限关系
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取基于type后指定的对象id，比如用户、权限、角色等表的主键
     *
     * @return target_id - 基于type后指定的对象id，比如用户、权限、角色等表的主键
     */
    public Integer getTargetId() {
        return targetId;
    }

    /**
     * 设置基于type后指定的对象id，比如用户、权限、角色等表的主键
     *
     * @param targetId 基于type后指定的对象id，比如用户、权限、角色等表的主键
     */
    public void setTargetId(Integer targetId) {
        this.targetId = targetId;
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

    /**
     * 获取当前是否复原过，0：没有复原过，1：复原过
     *
     * @return status - 当前是否复原过，0：没有复原过，1：复原过
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置当前是否复原过，0：没有复原过，1：复原过
     *
     * @param status 当前是否复原过，0：没有复原过，1：复原过
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取旧值
     *
     * @return old_value - 旧值
     */
    public String getOldValue() {
        return oldValue;
    }

    /**
     * 设置旧值
     *
     * @param oldValue 旧值
     */
    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    /**
     * 获取新值
     *
     * @return new_value - 新值
     */
    public String getNewValue() {
        return newValue;
    }

    /**
     * 设置新值
     *
     * @param newValue 新值
     */
    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }
}