package com.github.wolfgang.operation.core;

import com.github.wolfgang.operation.core.model.CallbackResponseAdaptor;

/**
 * @author wolfgang
 * @date 2020-02-14 16:47:46
 * @version $ Id: CallbackResponse.java, v 0.1  wolfgang Exp $
 */
public interface CallbackResponse {
    CallbackResponse FAIL = new CallbackResponseAdaptor(false, null);

    CallbackResponse EXPIRE = new CallbackResponseAdaptor(false, "expire");

    /**
     * 是否成功
     */
    boolean isSuccess();

    /**
     * 是否成功,不成功
     */
    String getResult();

    /**
     * 返回的数据
     * @return
     */
    Object getData();
}
