package com.github.wolfgang.operation.core;

import com.github.wolfgang.operation.core.model.MapOperationParameters;

/**
 * @author wolfgang
 * @date 2020-02-16 15:28:20
 * @version $ Id: OperationParametersBuilder.java, v 0.1  wolfgang Exp $
 */
public class OperationParametersBuilder {
    OperationParameters operationParameters;

    public OperationParametersBuilder() {
        operationParameters = new MapOperationParameters();
    }

    public OperationParametersBuilder keyValue(String key, Object value) {
        operationParameters.put(key, value);
        return this;
    }

    public OperationParameters build() {
        return operationParameters;
    }
}
