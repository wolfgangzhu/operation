package com.github.wolfgang.operation.core.processor;

import com.github.wolfgang.operation.core.OperationCallTemplate;
import com.github.wolfgang.operation.core.annotation.DelegateTaskEnhance;
import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.autoproxy.AbstractBeanFactoryAwareAdvisingPostProcessor;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.beans.factory.BeanFactory;

/**
 * @author wolfgang
 * @date 2021-03-09 22:32:05
 * @version $ Id: DelegateTaskHandlerProcessor.java, v 0.1  wolfgang Exp $
 */
public class DelegateTaskHandlerProcessor extends AbstractBeanFactoryAwareAdvisingPostProcessor {

    OperationCallTemplate operationCallTemplate;

    public DelegateTaskHandlerProcessor(OperationCallTemplate operationCallTemplate) {
        this.operationCallTemplate = operationCallTemplate;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        super.setBeanFactory(beanFactory);
        this.advisor = new AbstractPointcutAdvisor() {

            @Override
            public Advice getAdvice() {
                return new DelegateTaskInvocationHandler(operationCallTemplate);
            }

            @Override
            public Pointcut getPointcut() {
                return new AnnotationMatchingPointcut(null, DelegateTaskEnhance.class, true);
            }
        };
    }

}
