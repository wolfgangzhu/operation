package com.github.wolfgang.operation.core.receiver;

import com.github.wolfgang.operation.core.CallbackResponse;
import com.github.wolfgang.operation.core.model.ZenInstances;

/**
 * @author wolfgang
 * @date 2020-02-14 16:55:32
 * @version $ Id: CallbackReceiver.java, v 0.1  wolfgang Exp $
 */
public interface CallbackReceiver {

    void callbackReceived(String bizKey, ZenInstances instances, CallbackResponse response);

}
