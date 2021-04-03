package com.github.wolfgang.operation.core.dal.dataobject;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "`operation_callback`")
public class OperationCallbackDO {

    @Id
    @Column(name = "`id`")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 接收时间
     */
    @Column(name = "`receive_time`")
    private Date receiveTime;

    /**
     * 0代表未执行，1代表已执行
     */
    @Column(name = "`success`")
    private Boolean success;

    /**
     * operation_id
     */
    @Column(name = "`operation_id`")
    private Integer operationId;

    @Column(name = "`operation_id`")
    private byte[] data;

    @Column(name = "`msg`")
    private String msg;
    //
    //@Column(name = "`callbackType`")
    //private String callbackType;

    @Column(name = "`properties`")
    private byte[] properties;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getOperationId() {
        return operationId;
    }

    public void setOperationId(Integer operationId) {
        this.operationId = operationId;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public byte[] getProperties() {
        return properties;
    }

    public void setProperties(byte[] properties) {
        this.properties = properties;
    }
}