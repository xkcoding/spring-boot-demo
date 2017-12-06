package com.xkcoding.springbootdemorabcshiromybatis.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "mybatis_shiro_acl")
public class MybatisShiroAcl {
    /**
     * 权限id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 权限编号
     */
    private String code;

    /**
     * 权限名称
     */
    private String name;

    /**
     * 权限图标
     */
    private String icon;

    /**
     * 上级权限id
     */
    @Column(name = "parent_id")
    private Integer parentId;

    /**
     * 权限层级，默认为0，层级直接用逗号（半角）隔开
     */
    private String level;

    /**
     * 权限的url, 可以填正则表达式
     */
    private String url;

    /**
     * 类型，1：菜单，2：按钮，3：其他
     */
    private Integer type;

    /**
     * 状态，0：冻结，1：正常
     */
    private Integer status;

    /**
     * 权限在当前层级下的顺序，由小到大
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
     * 获取权限id
     *
     * @return id - 权限id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置权限id
     *
     * @param id 权限id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取权限编号
     *
     * @return code - 权限编号
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置权限编号
     *
     * @param code 权限编号
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取权限名称
     *
     * @return name - 权限名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置权限名称
     *
     * @param name 权限名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取权限图标
     *
     * @return icon - 权限图标
     */
    public String getIcon() {
        return icon;
    }

    /**
     * 设置权限图标
     *
     * @param icon 权限图标
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * 获取上级权限id
     *
     * @return parent_id - 上级权限id
     */
    public Integer getParentId() {
        return parentId;
    }

    /**
     * 设置上级权限id
     *
     * @param parentId 上级权限id
     */
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取权限层级，默认为0，层级直接用逗号（半角）隔开
     *
     * @return level - 权限层级，默认为0，层级直接用逗号（半角）隔开
     */
    public String getLevel() {
        return level;
    }

    /**
     * 设置权限层级，默认为0，层级直接用逗号（半角）隔开
     *
     * @param level 权限层级，默认为0，层级直接用逗号（半角）隔开
     */
    public void setLevel(String level) {
        this.level = level;
    }

    /**
     * 获取权限的url, 可以填正则表达式
     *
     * @return url - 权限的url, 可以填正则表达式
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置权限的url, 可以填正则表达式
     *
     * @param url 权限的url, 可以填正则表达式
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 获取类型，1：菜单，2：按钮，3：其他
     *
     * @return type - 类型，1：菜单，2：按钮，3：其他
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置类型，1：菜单，2：按钮，3：其他
     *
     * @param type 类型，1：菜单，2：按钮，3：其他
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取状态，0：冻结，1：正常
     *
     * @return status - 状态，0：冻结，1：正常
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态，0：冻结，1：正常
     *
     * @param status 状态，0：冻结，1：正常
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取权限在当前层级下的顺序，由小到大
     *
     * @return seq - 权限在当前层级下的顺序，由小到大
     */
    public Integer getSeq() {
        return seq;
    }

    /**
     * 设置权限在当前层级下的顺序，由小到大
     *
     * @param seq 权限在当前层级下的顺序，由小到大
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