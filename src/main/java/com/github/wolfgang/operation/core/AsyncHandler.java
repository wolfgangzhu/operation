package com.github.wolfgang.operation.core;

import com.github.wolfgang.operation.core.receiver.CallbackReceiver;

/**
 * @author wolfgang
 * @date 2020-02-14 19:25:52
 * @version $ Id: AsyncHandler.java, v 0.1  wolfgang Exp $
 */
public interface AsyncHandler<T> extends AsyncCaller<T>, CallbackReceiver {
}
