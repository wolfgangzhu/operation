package com.github.wolfgang.operation.core.processor;

import com.github.wolfgang.operation.core.OperationParameters;
import com.github.wolfgang.operation.core.TaskCaller;
import com.github.wolfgang.operation.core.model.TaskId;
import com.github.wolfgang.operation.core.model.ZenInstances;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @author wolfgang
 * @date 2021-03-09 22:44:20
 * @version $ Id: DelegateEnhancer.java, v 0.1  wolfgang Exp $
 */
public class DelegateEnhancer implements TaskCaller {

    private MethodInvocation methodInvocation;

    public DelegateEnhancer(MethodInvocation methodInvocation) {

        this.methodInvocation = methodInvocation;
    }

    @Override
    public TaskId execute(ZenInstances instances, OperationParameters parameters) {
        try {

            Object result = methodInvocation.proceed();
            if (result instanceof TaskId) {
                return ((TaskId) result);
            } else {
                return null;
            }
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
