package com.github.wolfgang.operation.configuration.spring;

import com.github.wolfgang.operation.core.Constants;
import com.github.wolfgang.operation.core.annotation.ReceiverConfig;
import com.github.wolfgang.operation.core.annotation.TaskConfig;
import com.github.wolfgang.operation.core.model.ReceiverConfigBean;
import com.github.wolfgang.operation.core.model.TaskConfigBean;
import com.github.wolfgang.operation.core.receiver.CallbackReceiver;
import com.github.wolfgang.operation.core.receiver.CallbackReceiverParserFactory;
import com.github.wolfgang.operation.core.task.OperationTaskChecker;
import com.google.common.base.Throwables;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import static org.springframework.util.ClassUtils.getUserClass;

/**
 * @author wolfgang
 * @date 2020-02-17 14:59:31
 * @version $ Id: SpringCallbackReceiverParserImpl.java, v 0.1  wolfgang Exp $
 */
public class SpringCallbackReceiverParserFactoryImpl implements CallbackReceiverParserFactory, ApplicationContextAware {

    private ApplicationContext context;

    @Override
    public ReceiverConfigBean parseReceiver(Class<? extends CallbackReceiver> receiverClass) {
        ReceiverConfig config = getUserClass(receiverClass).getAnnotation(ReceiverConfig.class);
        long expireTimeInMs;
        String name;

        if (config == null) {
            expireTimeInMs = Constants.RECEIVER_EXPIRED_TIME_IN_MS;
            name = "";
        } else {
            expireTimeInMs = config.expireInMs();
            name = config.name();
        }
        String className = getUserClass(receiverClass).getName();

        // 那么
        return new ReceiverConfigBean(expireTimeInMs, className, name);
    }

    @Override
    public TaskConfigBean parseTask(Class<? extends OperationTaskChecker> receiverClass) {
        TaskConfig config = getUserClass(receiverClass).getAnnotation(TaskConfig.class);
        long periodInMs;
        String name;
        if (config == null) {
            periodInMs = Constants.DEFAULT_TASK_INTERVAL_IN_MS;
            name = "";
        } else {
            periodInMs = config.periodInMs();
            name = config.name();
        }

        return new TaskConfigBean(periodInMs, getUserClass(receiverClass).getName(), name);
    }

    @Override
    public CallbackReceiver newCallbackReceiver(ReceiverConfigBean configBean) {
        try {
            Object receiver = context.getBean(configBean.getName());
            if (receiver instanceof CallbackReceiver) {
                return ((CallbackReceiver) receiver);
            }
        } catch (BeansException e) {
            // ignored
        }
        try {
            return (CallbackReceiver) context.getBean(Class.forName(configBean.getReceiverClass()));
        } catch (ClassNotFoundException e) {
            throw Throwables.propagate(e);
        }
    }

    @Override
    public OperationTaskChecker newTaskChecker(TaskConfigBean configBean) {
        try {
            Object taskChecker = context.getBean(configBean.getName());
            if (taskChecker instanceof OperationTaskChecker) {
                return ((OperationTaskChecker) taskChecker);
            }
        } catch (BeansException e) {
            // ignored
        }
        try {
            return (OperationTaskChecker) context.getBean(Class.forName(configBean.getTaskClass()));
        } catch (ClassNotFoundException e) {
            throw Throwables.propagate(e);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}
