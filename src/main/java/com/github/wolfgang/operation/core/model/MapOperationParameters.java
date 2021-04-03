package com.github.wolfgang.operation.core.model;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @author wolfgang
 * @date 2020-02-16 00:07:41
 * @version $ Id: MapOperationParameters.java, v 0.1  wolfgang Exp $
 */
public class MapOperationParameters extends AbstractOperationParameters {
    private Map<String, Object> params;

    public MapOperationParameters(Map<String, Object> params) {
        this.params = params;
    }

    public MapOperationParameters() {
        params = Maps.newLinkedHashMap();
    }

    @Override
    public boolean isEmpty() {
        return params.isEmpty();
    }

    @Override
    public Object put(String key, Object value) {
        return params.put(key, value);
    }

    @Override
    public Object get(String key) {
        return params.get(key);
    }

    @Override
    public Map<String, Object> toMap() {
        return params;
    }

    @Override
    public Object getFirst() {
        return params.values().stream().findFirst().get();
    }
}
