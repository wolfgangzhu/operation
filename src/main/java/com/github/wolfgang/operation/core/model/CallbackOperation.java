package com.github.wolfgang.operation.core.model;

/**
 * @author wolfgang
 * @date 2020-02-14 19:35:01
 * @version $ Id: ZenOperation.java, v 0.1  wolfgang Exp $
 */
public class CallbackOperation extends ZenOperation {

    private ReceiverConfigBean receiverConfigBean;

    public ReceiverConfigBean getReceiverConfigBean() {
        return receiverConfigBean;
    }

    public void setReceiverConfigBean(ReceiverConfigBean receiverConfigBean) {
        this.receiverConfigBean = receiverConfigBean;
    }
}
