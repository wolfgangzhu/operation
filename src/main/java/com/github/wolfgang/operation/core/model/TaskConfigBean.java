package com.github.wolfgang.operation.core.model;

/**
 * @author wolfgang
 * @date 2020-02-17 14:46:00
 * @version $ Id: ReceiverConfigBean.java, v 0.1  wolfgang Exp $
 */
public class TaskConfigBean {

    private long   periodInMs;
    private String taskClass;
    private String name;

    public TaskConfigBean(long periodInMs, String taskClass, String name) {
        this.periodInMs = periodInMs;
        this.taskClass = taskClass;
        this.name = name;
    }

    public long getPeriodInMs() {
        return periodInMs;
    }

    public void setPeriodInMs(long periodInMs) {
        this.periodInMs = periodInMs;
    }

    public String getTaskClass() {
        return taskClass;
    }

    public void setTaskClass(String taskClass) {
        this.taskClass = taskClass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
