package com.github.wolfgang.operation.core.model;

/**
 * @author wolfgang
 * @date 2019-12-26 11:00:27
 * @version $ Id: OperStatus.java, v 0.1  wolfgang Exp $
 */
public enum OperationStatus {
    /**
     * 开始
     */
    START(false),

    /**
     * 失败
     */
    FAIL(true),

    /**
     * 成功
     */
    SUCCESS(true),

    /**
     * 操作过期
     */
    EXPIRED(true),

    /**
     * indicate task is finished
     */
    NO_TASK(true);

    boolean done;

    OperationStatus(boolean done) {
        this.done = done;
    }

    public boolean isDone() {
        return done;
    }

}
