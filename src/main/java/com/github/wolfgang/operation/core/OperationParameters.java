package com.github.wolfgang.operation.core;

import java.util.Map;

/**
 * @author wolfgang
 * @date 2020-02-14 20:10:42
 * @version $ Id: ZenOperParameters.java, v 0.1  wolfgang Exp $
 */
public interface OperationParameters {

    boolean isEmpty();

    Object put(String key, Object value);

    Object get(String key);

    Boolean getBoolean(String key);

    Boolean getBoolean(String key, Boolean defaultValue);

    Integer getInteger(String key);

    Integer getInteger(String key, Integer defaultValue);

    Long getLong(String key);

    Long getLong(String key, Long defaultValue);

    Double getDouble(String key);

    Double getDouble(String key, Double defaultValue);

    String getString(String key);

    String getString(String key, String defaultValue);

    Map<String, Object> toMap();

    Object getFirst();

}
