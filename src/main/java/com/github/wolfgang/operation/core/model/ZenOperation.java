package com.github.wolfgang.operation.core.model;

import com.github.wolfgang.operation.core.OperationParameters;

/**
 * @author wolfgang
 * @date 2020-02-14 19:35:01
 * @version $ Id: ZenOperation.java, v 0.1  wolfgang Exp $
 */
public class ZenOperation {
    private Integer             id;
    private ZenInstances        instances;
    private String              bizKey;
    private OperationParameters parameters;

    public OperationParameters getParameters() {
        return parameters;
    }

    public void setParameters(OperationParameters parameters) {
        this.parameters = parameters;
    }

    public String getBizKey() {
        return bizKey;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ZenInstances getInstances() {
        return instances;
    }

    public void setInstances(ZenInstances instances) {
        this.instances = instances;
    }

    public Integer getId() {
        return id;
    }

    public void setBizKey(String bizKey) {
        this.bizKey = bizKey;
    }

}
