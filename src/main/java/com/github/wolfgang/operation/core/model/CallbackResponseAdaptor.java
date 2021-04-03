package com.github.wolfgang.operation.core.model;

import com.github.wolfgang.operation.core.CallbackResponse;

/**
 * @author wolfgang
 * @date 2020-02-14 16:47:46
 * @version $ Id: CallbackResponse.java, v 0.1  wolfgang Exp $
 */
public class CallbackResponseAdaptor implements CallbackResponse {

    private boolean success;
    private String  msg;

    public CallbackResponseAdaptor(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }

    @Override
    public boolean isSuccess() {
        return success;
    }

    @Override
    public String getResult() {
        return msg;
    }

    @Override
    public Object getData() {
        return null;
    }
}
