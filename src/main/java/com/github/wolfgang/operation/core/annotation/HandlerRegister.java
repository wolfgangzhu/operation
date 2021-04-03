package com.github.wolfgang.operation.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.github.wolfgang.operation.core.HandlerCenter.DEFAULT_GROUP;

/**
 * @author wolfgang
 * @date 2020-11-24 22:10:31
 * @version $ Id: HandlerRegist.java, v 0.1  wolfgang Exp $
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HandlerRegister {

    String value();

    String group() default DEFAULT_GROUP;
}
