package com.xkcoding.springbootdemorabcshiromybatis.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "mybatis_shiro_dept")
public class MybatisShiroDept {
    /**
     * 部门id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 部门名称
     */
    private String name;

    /**
     * 上级部门id，顶层是0
     */
    @Column(name = "parent_id")
    private Integer parentId;

    /**
     * 部门层级，默认为0，层级直接用逗号（半角）隔开
     */
    private String level;

    /**
     * 部门在当前层级下的顺序，由小到大
     */
    private Integer seq;

    /**
     * 备注
     */
    private String remark;

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
     * 获取部门id
     *
     * @return id - 部门id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置部门id
     *
     * @param id 部门id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取部门名称
     *
     * @return name - 部门名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置部门名称
     *
     * @param name 部门名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取上级部门id，顶层是0
     *
     * @return parent_id - 上级部门id，顶层是0
     */
    public Integer getParentId() {
        return parentId;
    }

    /**
     * 设置上级部门id，顶层是0
     *
     * @param parentId 上级部门id，顶层是0
     */
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取部门层级，默认为0，层级直接用逗号（半角）隔开
     *
     * @return level - 部门层级，默认为0，层级直接用逗号（半角）隔开
     */
    public String getLevel() {
        return level;
    }

    /**
     * 设置部门层级，默认为0，层级直接用逗号（半角）隔开
     *
     * @param level 部门层级，默认为0，层级直接用逗号（半角）隔开
     */
    public void setLevel(String level) {
        this.level = level;
    }

    /**
     * 获取部门在当前层级下的顺序，由小到大
     *
     * @return seq - 部门在当前层级下的顺序，由小到大
     */
    public Integer getSeq() {
        return seq;
    }

    /**
     * 设置部门在当前层级下的顺序，由小到大
     *
     * @param seq 部门在当前层级下的顺序，由小到大
     */
    public void setSeq(Integer seq) {
        this.seq = seq;
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