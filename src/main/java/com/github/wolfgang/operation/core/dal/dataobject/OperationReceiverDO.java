package com.github.wolfgang.operation.core.dal.dataobject;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author wolfgang
 * @date 2020-02-29 19:08:44
 * @version $ Id: OperationReceiverDO.java, v 0.1  wolfgang Exp $
 */
@Table(name = "operation_receiver")
public class OperationReceiverDO {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    @Column(name = "operation_id")
    private Integer operationId;

    @Column(name = "receiver_class")
    private String receiverClass;

    @Column(name = "receiver_name")
    private String receiverName;

    @Column(name = "expire_mills")
    private Long expireMills;

    @Column(name = "expire_time")
    private Date expireTime;

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
     * @return receiver_class
     */
    public String getReceiverClass() {
        return receiverClass;
    }

    /**
     * @param receiverClass
     */
    public void setReceiverClass(String receiverClass) {
        this.receiverClass = receiverClass;
    }

    /**
     * @return receiver_name
     */
    public String getReceiverName() {
        return receiverName;
    }

    /**
     * @param receiverName
     */
    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    /**
     * @return expire_mills
     */
    public Long getExpireMills() {
        return expireMills;
    }

    /**
     * @param expireMills
     */
    public void setExpireMills(Long expireMills) {
        this.expireMills = expireMills;
    }

    /**
     * @return expire_time
     */
    public Date getExpireTime() {
        return expireTime;
    }

    /**
     * @param expireTime
     */
    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }
}