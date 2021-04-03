package com.github.wolfgang.operation.core.dal.dataobject;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "`operation_parameters`")
public class OperationParametersDO {
    @Id
    @Column(name = "`id`")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 0代表未执行，1代表已执行
     */
    @Column(name = "`operation_id`")
    private Integer operId;

    /**
     * 0代表未执行，1代表已执行
     */
    @Column(name = "`params`")
    private byte[] parameters;

    public static final String OPERATION_ID = "operId";

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOperId() {
        return operId;
    }

    public void setOperId(Integer operId) {
        this.operId = operId;
    }

    public byte[] getParameters() {
        return parameters;
    }

    public void setParameters(byte[] parameters) {
        this.parameters = parameters;
    }
}