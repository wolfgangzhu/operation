package com.github.wolfgang.operation.core.service.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.wolfgang.operation.core.OperationParameters;
import com.github.wolfgang.operation.core.model.MapOperationParameters;
import com.google.common.base.Throwables;

import java.io.IOException;
import java.util.Map;

/**
 * @author wolfgang
 * @date 2020-02-16 11:25:43
 * @version $ Id: ParameterSerializer.java, v 0.1  wolfgang Exp $
 */
public class DefaultParameterSerializer implements ParameterSerializer {
    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public byte[] serialize(OperationParameters parameters) {
        Map<String, Object> param = parameters.toMap();
        try {
            return mapper.writeValueAsBytes(param);
        } catch (JsonProcessingException e) {
            throw Throwables.propagate(e);
        }
    }

    @Override
    public OperationParameters deserialize(byte[] parameters) {

        Map<String, Object> result = deserializeByteArray(parameters);
        return new MapOperationParameters(result);
    }

    private Map<String, Object> deserializeByteArray(byte[] parameters) {
        if (parameters == null) {
            return null;
        }
        try {
            return mapper.readValue(parameters, new TypeReference<Map<String, Object>>() {
            });
        } catch (IOException e) {
            throw Throwables.propagate(e);
        }
    }
}
