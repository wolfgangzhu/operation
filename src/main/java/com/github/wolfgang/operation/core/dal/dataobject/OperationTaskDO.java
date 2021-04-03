package com.github.wolfgang.operation.core.dal.dataobject;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author wolfgang
 * @date 2020-02-29 23:00:56
 * @version $ Id: OperationTaskDO.java, v 0.1  wolfgang Exp $
 */
@Table(name = "operation_task")
public class OperationTaskDO {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    @Column(name = "operation_id")
    private Integer operationId;

    @Column(name = "task_id")
    private String taskId;

    @Column(name = "task_caller")
    private String taskCaller;

    @Column(name = "task_name")
    private String taskName;

    @Column(name = "is_done")
    private Boolean isDone;

    @Column(name = "`exception`")
    private byte[] exception;

    @Column(name = "last_call_time")
    private Date lastCallTime;

    /**
     * 执行的周期毫秒
     */
    @Column(name = "period")
    private Long period;

    @Column(name = "task_status")
    private String taskStatus;

    public final static String OPERATION_ID = "operationId";

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return operation_id
     */
    public Integer getOperationId() {
        return operationId;
    }

    /**
     * @param operationId
     */
    public void setOperationId(Integer operationId) {
        this.operationId = operationId;
    }

    /**
     * @return task_id
     */
    public String getTaskId() {
        return taskId;
    }

    /**
     * @param taskId
     */
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    /**
     * @return task_caller
     */
    public String getTaskCaller() {
        return taskCaller;
    }

    /**
     * @param taskCaller
     */
    public void setTaskCaller(String taskCaller) {
        this.taskCaller = taskCaller;
    }

    /**
     * @return task_name
     */
    public String getTaskName() {
        return taskName;
    }

    /**
     * @param taskName
     */
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    /**
     * @return is_done
     */
    public Boolean getIsDone() {
        return isDone;
    }

    /**
     * @param isDone
     */
    public void setIsDone(Boolean isDone) {
        this.isDone = isDone;
    }

    /**
     * @return exception
     */
    public byte[] getException() {
        return exception;
    }

    /**
     * @param exception
     */
    public void setException(byte[] exception) {
        this.exception = exception;
    }

    /**
     * @return last_call_time
     */
    public Date getLastCallTime() {
        return lastCallTime;
    }

    /**
     * @param lastCallTime
     */
    public void setLastCallTime(Date lastCallTime) {
        this.lastCallTime = lastCallTime;
    }

    /**
     * 获取执行的周期毫秒
     *
     * @return period - 执行的周期毫秒
     */
    public Long getPeriod() {
        return period;
    }

    /**
     * 设置执行的周期毫秒
     *
     * @param period 执行的周期毫秒
     */
    public void setPeriod(Long period) {
        this.period = period;
    }

    /**
     * @return task_status
     */
    public String getTaskStatus() {
        return taskStatus;
    }

    /**
     * @param taskStatus
     */
    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }
}