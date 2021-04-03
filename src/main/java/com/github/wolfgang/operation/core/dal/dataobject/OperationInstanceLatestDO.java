package com.github.wolfgang.operation.core.dal.dataobject;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "`operation_instance_latest`")
public class OperationInstanceLatestDO {
    /**
     * 主键
     */
    @Id
    @Column(name = "`id`")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 最新的oper
     */
    @Column(name = "`oper_id`")
    private Integer operId;

    /**
     * 实例类型
     */
    @Column(name = "`instance_type`")
    private String instanceType;

    /**
     * 实例编号
     */
    @Column(name = "`instance_id`")
    private String instanceId;

    /**
     * 操作状态
     */
    @Column(name = "`oper_status`")
    private String operStatus;

    @Column(name = "`biz_key`")
    private String bizKey;

    public static final String ID = "id";

    public static final String OPER_ID = "operId";

    public static final String INSTANCE_TYPE = "instanceType";

    public static final String INSTANCE_ID = "instanceId";

    public static final String OPER_STATUS = "operStatus";

    public String getBizKey() {
        return bizKey;
    }

    public void setBizKey(String bizKey) {
        this.bizKey = bizKey;
    }

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取最新的oper
     *
     * @return oper_id - 最新的oper
     */
    public Integer getOperId() {
        return operId;
    }

    /**
     * 设置最新的oper
     *
     * @param operId 最新的oper
     */
    public void setOperId(Integer operId) {
        this.operId = operId;
    }

    /**
     * 获取实例类型
     *
     * @return instance_type - 实例类型
     */
    public String getInstanceType() {
        return instanceType;
    }

    /**
     * 设置实例类型
     *
     * @param instanceType 实例类型
     */
    public void setInstanceType(String instanceType) {
        this.instanceType = instanceType == null ? null : instanceType.trim();
    }

    /**
     * 获取实例编号
     *
     * @return instance_id - 实例编号
     */
    public String getInstanceId() {
        return instanceId;
    }

    /**
     * 设置实例编号
     *
     * @param instanceId 实例编号
     */
    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId == null ? null : instanceId.trim();
    }

    /**
     * 获取操作状态
     *
     * @return oper_status - 操作状态
     */
    public String getOperStatus() {
        return operStatus;
    }

    /**
     * 设置操作状态
     *
     * @param operStatus 操作状态
     */
    public void setOperStatus(String operStatus) {
        this.operStatus = operStatus == null ? null : operStatus.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", operId=").append(operId);
        sb.append(", instanceType=").append(instanceType);
        sb.append(", instanceId=").append(instanceId);
        sb.append(", operStatus=").append(operStatus);
        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        OperationInstanceLatestDO other = (OperationInstanceLatestDO) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId())) && (this.getOperId() == null ?
                other.getOperId() == null : this.getOperId().equals(other.getOperId())) && (this.getInstanceType() == null ?
                other.getInstanceType() == null : this.getInstanceType().equals(other.getInstanceType())) && (this.getInstanceId() == null ?
                other.getInstanceId() == null : this.getInstanceId().equals(other.getInstanceId())) && (this.getOperStatus() == null ?
                other.getOperStatus() == null : this.getOperStatus().equals(other.getOperStatus()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getOperId() == null) ? 0 : getOperId().hashCode());
        result = prime * result + ((getInstanceType() == null) ? 0 : getInstanceType().hashCode());
        result = prime * result + ((getInstanceId() == null) ? 0 : getInstanceId().hashCode());
        result = prime * result + ((getOperStatus() == null) ? 0 : getOperStatus().hashCode());
        return result;
    }
}