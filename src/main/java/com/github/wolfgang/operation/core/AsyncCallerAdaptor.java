package com.github.wolfgang.operation.core;

import com.github.wolfgang.operation.core.model.ZenInstances;

/**
 * @author wolfgang
 * @date 2020-02-14 16:43:48
 * @version $ Id: AysncExecutor.java, v 0.1  wolfgang Exp $
 */
public class AsyncCallerAdaptor<T> implements AsyncCaller<T> {

    @Override
    public void preExecute(ZenInstances instanceId) {

    }

    @Override
    public T execute(ZenInstances instances, OperationParameters parameters) {
        return null;
    }

    public void afterExecute(T result) {

    }
}
