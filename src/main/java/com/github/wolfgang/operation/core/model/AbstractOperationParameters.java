package com.github.wolfgang.operation.core.model;

import com.github.wolfgang.operation.core.OperationParameters;

/**
 * @author wolfgang
 * @date 2020-02-15 23:57:58
 * @version $ Id: AbstractOperParameters.java, v 0.1  wolfgang Exp $
 */
public abstract class AbstractOperationParameters implements OperationParameters {

    @Override
    public Boolean getBoolean(String key) {
        return getBoolean(key, null);
    }

    @Override
    public Boolean getBoolean(String key, Boolean defaultValue) {
        Object object = get(key);
        if (object == null) {
            return defaultValue;
        }
        if (object instanceof Boolean) {
            return ((Boolean) object);
        }
        return Boolean.valueOf(object.toString());
    }

    @Override
    public Integer getInteger(String key) {
        return getInteger(key, null);
    }

    @Override
    public Integer getInteger(String key, Integer defaultValue) {
        Object object = get(key);
        if (object == null) {
            return defaultValue;
        }
        if (object instanceof Integer) {
            return ((Integer) object);
        }
        return Integer.valueOf(object.toString());
    }

    @Override
    public Long getLong(String key) {
        return getLong(key, null);
    }

    @Override
    public Long getLong(String key, Long defaultValue) {
        Object object = get(key);
        if (object == null) {
            return defaultValue;
        }
        if (object instanceof Long) {
            return ((Long) object);
        }
        return Long.valueOf(object.toString());
    }

    @Override
    public Double getDouble(String key) {
        return getDouble(key, null);
    }

    @Override
    public Double getDouble(String key, Double defaultValue) {
        Object object = get(key);
        if (object == null) {
            return defaultValue;
        }
        if (object instanceof Double) {
            return ((Double) object);
        }
        return Double.valueOf(object.toString());
    }

    @Override
    public String getString(String key) {
        return getString(key, null);
    }

    @Override
    public String getString(String key, String defaultValue) {
        Object object = get(key);
        if (object == null) {
            return defaultValue;
        }
        if (object instanceof String) {
            return ((String) object);
        }
        return String.valueOf(object.toString());
    }
}
