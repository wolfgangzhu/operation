package com.github.wolfgang.operation.core.annotation;

import com.github.wolfgang.operation.core.task.OperationTaskChecker;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 该注解是放在
 * @author wolfgang
 * @date 2020-02-17 14:40:00
 * @version $ Id: ReceiverConfig.java, v 0.1  wolfgang Exp $
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DelegateTaskEnhance {

    String bizKey();

    String instanceId() default "";

    Class<? extends OperationTaskChecker> checker();

}
