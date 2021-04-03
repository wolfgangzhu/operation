package com.github.wolfgang.operation.core.annotation;

import com.github.wolfgang.operation.core.Constants;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author wolfgang
 * @date 2020-02-29 22:42:10
 * @version $ Id: TaskConfig.java, v 0.1  wolfgang Exp $
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TaskConfig {

    long periodInMs() default Constants.DEFAULT_TASK_INTERVAL_IN_MS;

    String name();

}
