package com.github.wolfgang.operation.core.receiver;

/**
 * @author wolfgang
 * @date 2020-02-14 16:55:32
 * @version $ Id: CallbackReceiver.java, v 0.1  wolfgang Exp $
 */
public interface RemoteCheckCallbackReceiver extends CallbackReceiver {

    boolean checkCallStatus();

}
