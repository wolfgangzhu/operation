package com.github.wolfgang.operation.core;

import com.github.wolfgang.operation.core.model.OperationStatus;

import javax.persistence.Id;

/**
 * @author wolfgang
 * @date 2020-02-14 19:03:53
 * @version $ Id: LastOperation.java, v 0.1  wolfgang Exp $
 */
public class LastOperation {
    /**
     * 主键
     */
    @Id
    private Integer id;

    /**
     * 最新的oper
     */
    private Integer operId;

    /**
     * 实例类型
     */
    private String instanceType;

    /**
     * 实例编号
     */
    private String instanceId;

    /**
     * 操作状态
     */
    private OperationStatus operStatus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOperId() {
        return operId;
    }

    public void setOperId(Integer operId) {
        this.operId = operId;
    }

    public String getInstanceType() {
        return instanceType;
    }

    public void setInstanceType(String instanceType) {
        this.instanceType = instanceType;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public OperationStatus getOperStatus() {
        return operStatus;
    }

    public void setOperStatus(OperationStatus operStatus) {
        this.operStatus = operStatus;
    }
}
