package com.github.wolfgang.operation.core.processor;

import com.github.wolfgang.operation.core.OperationCallTemplate;
import com.github.wolfgang.operation.core.annotation.DelegateTaskEnhance;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.support.AopUtils;
import org.springframework.core.BridgeMethodResolver;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Method;

/**
 * @author wolfgang
 * @date 2021-03-10 09:06:48
 * @version $ Id: DelegateTaskInvocationHandler.java, v 0.1  wolfgang Exp $
 */
public class DelegateTaskInvocationHandler implements MethodInterceptor {

    OperationCallTemplate operationCallTemplate;

    public DelegateTaskInvocationHandler(OperationCallTemplate operationCallTemplate) {
        this.operationCallTemplate = operationCallTemplate;
    }

    @Override
    public Object invoke(MethodInvocation invocation) {

        Class<?> targetClass = (invocation.getThis() != null ? AopUtils.getTargetClass(invocation.getThis()) : null);
        Method specificMethod = ClassUtils.getMostSpecificMethod(invocation.getMethod(), targetClass);
        final Method userDeclaredMethod = BridgeMethodResolver.findBridgedMethod(specificMethod);

        DelegateTaskEnhance delegateAnnotation = AnnotatedElementUtils.findMergedAnnotation(userDeclaredMethod, DelegateTaskEnhance.class);
        if (delegateAnnotation == null) {
            delegateAnnotation = AnnotatedElementUtils.findMergedAnnotation(userDeclaredMethod.getDeclaringClass(),
                    DelegateTaskEnhance.class);
        }

        return operationCallTemplate.asyncTaskCall(delegateAnnotation.bizKey(), new DelegateEnhancer(invocation),
                delegateAnnotation.checker());

    }
}
