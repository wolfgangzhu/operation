package com.github.wolfgang.operation.core.model;

/**
 * @author wolfgang
 * @date 2020-02-17 18:18:48
 * @version $ Id: InstanceStatus.java, v 0.1  wolfgang Exp $
 */
public class InstanceStatus {

    private String          instanceId;
    private String          instanceType;
    private OperationStatus operationStatus;
    private String          bizKey;

    public InstanceStatus(String instanceId, String instanceType, OperationStatus operationStatus, String bizKey) {
        this.instanceId = instanceId;
        this.instanceType = instanceType;
        this.operationStatus = operationStatus;
        this.bizKey = bizKey;
    }

    public String getBizKey() {
        return bizKey;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public String getInstanceType() {
        return instanceType;
    }

    public OperationStatus getOperationStatus() {
        return operationStatus;
    }
}
