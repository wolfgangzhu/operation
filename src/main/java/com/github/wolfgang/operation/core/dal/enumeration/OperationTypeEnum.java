package com.github.wolfgang.operation.core.dal.enumeration;

import com.github.wolfgang.operation.core.mybatis.EnumTypeCode;

/**
 * @author wolfgang
 * @date 2020-02-29 18:20:00
 * @version $ Id: OperationType.java, v 0.1  wolfgang Exp $
 */
public enum OperationTypeEnum implements EnumTypeCode {
    /**
     * 回调
     */
    callback(0),

    /**
     * 任务
     */
    task(1);

    int code;

    OperationTypeEnum(int code) {
        this.code = code;
    }

    @Override
    public int getCode() {
        return code;
    }
}
