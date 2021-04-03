package com.github.wolfgang.operation.core.model;

/**
 * @author wolfgang
 * @date 2020-02-17 14:46:00
 * @version $ Id: ReceiverConfigBean.java, v 0.1  wolfgang Exp $
 */
public class ReceiverConfigBean {
    private long   expireInMs;
    private String receiverClass;
    private String name;

    public ReceiverConfigBean(long expireInMs, String receiverClass, String name) {
        this.expireInMs = expireInMs;
        this.receiverClass = receiverClass;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getExpireInMs() {
        return expireInMs;
    }

    public void setExpireInMs(long expireInMs) {
        this.expireInMs = expireInMs;
    }

    public String getReceiverClass() {
        return receiverClass;
    }

    public void setReceiverClass(String receiverClass) {
        this.receiverClass = receiverClass;
    }
}
