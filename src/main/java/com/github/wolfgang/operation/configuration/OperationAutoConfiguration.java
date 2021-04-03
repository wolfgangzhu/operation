package com.github.wolfgang.operation.configuration;

import com.github.wolfgang.operation.configuration.spring.SpringCallbackReceiverParserFactoryImpl;
import com.github.wolfgang.operation.core.OperationCallTemplate;
import com.github.wolfgang.operation.core.OperationUserService;
import com.github.wolfgang.operation.core.OperationUserServiceImpl;
import com.github.wolfgang.operation.core.processor.DelegateTaskHandlerProcessor;
import com.github.wolfgang.operation.core.receiver.CallbackReceiverParserFactory;
import com.github.wolfgang.operation.core.service.OperationService;
import com.github.wolfgang.operation.core.service.OperationServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author wolfgang
 * @date 2020-02-14 22:49:43
 * @version $ Id: AsyncAutoConfiguration.java, v 0.1  wolfgang Exp $
 */
@Configuration
@MapperScan("com.github.wolfgang.operation.core.dal")
@ConditionalOnClass(OperationCallTemplate.class)
public class OperationAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public OperationCallTemplate asyncCallTemplate(OperationService operationService,
                                                   CallbackReceiverParserFactory callbackReceiverParserFactory) {
        return new OperationCallTemplate(operationService, callbackReceiverParserFactory);
    }

    @Bean
    @ConditionalOnMissingBean
    public CallbackReceiverParserFactory callbackReceiverFactory() {
        return new SpringCallbackReceiverParserFactoryImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    public OperationService operationService() {
        return new OperationServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    public OperationUserService operationUserService() {
        return new OperationUserServiceImpl();
    }

    @Bean
    public DelegateTaskHandlerProcessor delegateTaskHandlerProcessor(OperationCallTemplate operationCallTemplate) {
        return new DelegateTaskHandlerProcessor(operationCallTemplate);
    }
}
