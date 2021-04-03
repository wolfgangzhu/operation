package com.github.wolfgang.operation.core.model;

/**
 * @author wolfgang
 * @date 2020-02-29 23:46:32
 * @version $ Id: TaskResult.java, v 0.1  wolfgang Exp $
 */
public class TaskResult {
    private boolean         done;
    private OperationStatus status;
    private String          msg;
    private Object          data;

    public static final TaskResult UNFINISH = new TaskResult(false, null, null);

    public TaskResult(boolean done, OperationStatus status, String msg) {
        this.done = done;
        this.status = status;
        this.msg = msg;
    }

    public TaskResult(boolean done, OperationStatus status, String msg, Object data) {
        this.done = done;
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public OperationStatus getStatus() {
        return status;
    }

    public void setStatus(OperationStatus status) {
        this.status = status;
    }
}
