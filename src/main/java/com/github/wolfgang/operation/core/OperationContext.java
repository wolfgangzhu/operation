package com.github.wolfgang.operation.core;

import com.github.wolfgang.operation.core.model.ZenOperation;

/**
 * @author wolfgang
 * @date 2019-12-26 11:44:02
 * @version $ Id: OperContextHolder.java, v 0.1  wolfgang Exp $
 */
public class OperationContext {

    private static final ThreadLocal<OperationContext> LOCAL = ThreadLocal.withInitial(OperationContext::new);

    private ZenOperation operation;

    private OperationContext() {

    }

    public OperationParameters getParameters() {
        return operation.getParameters();
    }

    public static OperationContext getContext() {
        return LOCAL.get();
    }

    public ZenOperation getOperation() {
        return operation;
    }

    public void setOperation(ZenOperation operation) {
        this.operation = operation;
    }

    public Integer getOperationId() {
        if (operation == null) {
            return null;
        }
        return operation.getId();
    }

    public static void removeContext() {
        LOCAL.remove();
    }
}
