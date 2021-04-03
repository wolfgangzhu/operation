package com.github.wolfgang.operation.core.model;

import java.util.Objects;

/**
 * @author wolfgang
 * @date 2020-02-14 18:33:54
 * @version $ Id: OperInstance.java, v 0.1  wolfgang Exp $
 */
public class ZenInstance {

    private String type;
    private String instanceId;

    public ZenInstance(String type, String instanceId) {
        this.type = type;
        this.instanceId = instanceId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ZenInstance that = (ZenInstance) o;
        return Objects.equals(type, that.type) && Objects.equals(instanceId, that.instanceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, instanceId);
    }
}
