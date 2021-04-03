package com.github.wolfgang.operation.core.service.helper;

import com.github.wolfgang.operation.core.OperationParameters;

/**
 * @author wolfgang
 * @date 2020-02-16 11:25:43
 * @version $ Id: ParameterSerializer.java, v 0.1  wolfgang Exp $
 */
public interface ParameterSerializer {
    byte[] serialize(OperationParameters parameters);

    OperationParameters deserialize(byte[] parameters);
}
