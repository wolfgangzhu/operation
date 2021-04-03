package com.github.wolfgang.operation.core.model;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wolfgang
 * @date 2020-03-01 16:08:22
 * @version $ Id: SingleValueOperationParameters.java, v 0.1  wolfgang Exp $
 */
public class SingleValueOperationParameters extends AbstractOperationParameters {

    private Object value;

    public SingleValueOperationParameters() {
    }

    public SingleValueOperationParameters(Object value) {
        this.value = value;
    }

    @Override
    public boolean isEmpty() {
        return value == null;
    }

    @Override
    public Object put(String key, Object value) {
        Object preValue = value;
        this.value = value;
        return preValue;
    }

    @Override
    public Object get(String key) {
        return value;
    }

    @Override
    public Map<String, Object> toMap() {
        HashMap<String, Object> map = new HashMap<>(1);
        map.put("key", value);
        return map;
    }

    @Override
    public Object getFirst() {
        return value;
    }
}
