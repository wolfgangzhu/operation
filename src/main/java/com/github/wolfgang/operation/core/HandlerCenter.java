package com.github.wolfgang.operation.core;

import com.github.wolfgang.operation.core.annotation.HandlerRegister;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wolfgang
 * @date 2020-11-24 22:13:33
 * @version $ Id: HandlerCenter.java, v 0.1  wolfgang Exp $
 */
@Service
public class HandlerCenter implements InitializingBean, ApplicationContextAware {
    public static final String DEFAULT_GROUP = "@DEFAULT_GROUP@";

    static  Map<String, Map<String, Object>> taskHandlerMMap = new HashMap<>();
    private ApplicationContext               applicationContext;

    public static Object getByType(String group, String type) {
        return taskHandlerMMap.getOrDefault(group, new HashMap<>(0)).get(type);
    }

    public static Object getByType(String type) {
        return getByType(DEFAULT_GROUP, type);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String, Object> beans = applicationContext.getBeansWithAnnotation(HandlerRegister.class);
        for (Object value : beans.values()) {
            HandlerRegister annotation = ClassUtils.getUserClass(value).getAnnotation(HandlerRegister.class);
            taskHandlerMMap.computeIfAbsent(annotation.group(), k -> new HashMap<>()).put(annotation.value(), value);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        this.applicationContext = applicationContext;
    }
}
