package com.github.wolfgang.operation.core.annotation;

import com.github.wolfgang.operation.core.Constants;

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
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ReceiverConfig {

    /**
     * 默认15分钟过期
     *
     */
    long expireInMs() default Constants.RECEIVER_EXPIRED_TIME_IN_MS;

    /**
     * the receiver identifier name
     */
    String name() default "";
}
