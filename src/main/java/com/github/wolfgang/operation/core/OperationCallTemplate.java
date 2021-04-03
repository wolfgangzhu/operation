package com.github.wolfgang.operation.core;

import com.github.wolfgang.operation.core.dal.dataobject.OperationDO;
import com.github.wolfgang.operation.core.dal.dataobject.OperationTaskDO;
import com.github.wolfgang.operation.core.model.CallbackOperation;
import com.github.wolfgang.operation.core.model.CallbackResponseAdaptor;
import com.github.wolfgang.operation.core.model.OperationStatus;
import com.github.wolfgang.operation.core.model.ReceiverConfigBean;
import com.github.wolfgang.operation.core.model.TaskConfigBean;
import com.github.wolfgang.operation.core.model.TaskId;
import com.github.wolfgang.operation.core.model.TaskResult;
import com.github.wolfgang.operation.core.model.ZenInstance;
import com.github.wolfgang.operation.core.model.ZenInstances;
import com.github.wolfgang.operation.core.model.ZenOperation;
import com.github.wolfgang.operation.core.receiver.CallbackReceiver;
import com.github.wolfgang.operation.core.receiver.CallbackReceiverParserFactory;
import com.github.wolfgang.operation.core.service.OperationService;
import com.github.wolfgang.operation.core.task.OperationTaskChecker;
import com.github.wolfgang.operation.utils.DateUtils;
import com.google.common.base.Charsets;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import static com.github.wolfgang.operation.core.model.OperationStatus.NO_TASK;
import static com.google.common.base.Throwables.propagate;
import static com.google.common.base.Throwables.throwIfUnchecked;

/**
 * @author wolfgang
 * @date 2020-02-14 16:51:27
 * @version $ Id: AsyncCallTemplate.java, v 0.1  wolfgang Exp $
 */
@SuppressWarnings("unused")
public class OperationCallTemplate implements DisposableBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(OperationCallTemplate.class);

    private OperationService operationService;

    private CallbackReceiverParserFactory callbackReceiverParserFactory;

    private ExecutorService executor;

    private ScheduledExecutorService receiverExecutorService;
    private ScheduledExecutorService taskCheckerExecutorService;

    private static final long PERIOD_MS = 1000L * 5;

    private static final long TASK_PERIOD_MS = 1000L * 3;

    public OperationCallTemplate(OperationService operationService, CallbackReceiverParserFactory callbackReceiverParserFactory) {
        this(operationService, callbackReceiverParserFactory, createExecutor());
    }

    private static ExecutorService createExecutor() {
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("com.github.wolfgang.operation.execution-%d").setDaemon(true)
                .build();
        return new ScheduledThreadPoolExecutor(30, threadFactory);
    }

    public OperationCallTemplate(OperationService operationService, CallbackReceiverParserFactory callbackReceiverParserFactory,
                                 ExecutorService executor) {
        this.operationService = operationService;
        this.callbackReceiverParserFactory = callbackReceiverParserFactory;
        this.executor = executor;
        init();
    }

    private void init() {
        ThreadFactory callbackThreadFactory = new ThreadFactoryBuilder().setNameFormat("com.github.wolfgang.operation.callback-%d")
                .setDaemon(true).build();
        receiverExecutorService = new ScheduledThreadPoolExecutor(1, callbackThreadFactory);
        receiverExecutorService.scheduleAtFixedRate(this::handleExpiredOperations, 0, PERIOD_MS, TimeUnit.MILLISECONDS);

        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("com.github.wolfgang.operation.task-%d").setDaemon(true)
                .build();
        taskCheckerExecutorService = new ScheduledThreadPoolExecutor(1, threadFactory);
        taskCheckerExecutorService.scheduleAtFixedRate(this::handleTaskOperations, 0, TASK_PERIOD_MS, TimeUnit.MILLISECONDS);
    }

    public void receiveCallback(Integer operId, CallbackResponse response) {
        receiveCallback(operId, response, (byte[]) null);
    }

    public void receiveCallback(Integer operId, CallbackResponse response, String properties) {
        receiveCallback(operId, response, properties == null ? null : properties.getBytes(Charsets.UTF_8));
    }

    public void receiveCallback(Integer operId, CallbackResponse response, byte[] properties) {

        CallbackOperation operation = operationService.onCallbackReceived(operId, response, properties);

        if (operation == null) {
            return;
        }
        try {
            OperationContext.getContext().setOperation(operation);
            CallbackReceiver receiver = getReceiver(operation);
            if (receiver == null) {
                return;
            }

            receiver.callbackReceived(operation.getBizKey(), operation.getInstances(), response);
        } finally {
            OperationContext.removeContext();
        }
    }

    /**
     * callbackCall
     * @param bizKey 业务key
     * @param instances 实例
     * @param caller caller instance
     * @param user user
     * @param parameters 参数
     */
    public <T> void asyncCall(String bizKey, ZenInstances instances, OperationParameters parameters, AsyncCaller<T> caller, String user) {
        asyncCall(bizKey, instances, parameters, caller, null, user);
    }

    public Object asyncTaskCall(String bizKey, TaskCaller caller, Class<? extends OperationTaskChecker> taskChecker) {
        return asyncTaskCall(bizKey, null, null, caller, taskChecker, "");
    }

    public Object asyncTaskCall(String bizKey, ZenInstances instances, OperationParameters parameters, TaskCaller caller,
                                Class<? extends OperationTaskChecker> taskChecker, String user) {
        ZenOperation operation = operationService.saveTaskOperation(bizKey, instances, parameters, getCallerIdentifier(caller),
                getTaskConfig(taskChecker), OperationStatus.START, user);

        OperationContext.getContext().setOperation(operation);
        try {
            TaskId taskId = caller.execute(instances, parameters);
            if (taskId == null) {
                operationService.taskOperationDone(operation.getId(), NO_TASK, "no task");
            } else {
                operationService.onTaskOperationInvoked(operation.getId(), taskId);
            }
            return taskId;
        } catch (Exception e) {
            operationService.onTaskOperationException(operation.getId(), e.getMessage());
            throw propagate(e);
        } finally {
            OperationContext.removeContext();
        }
    }

    public void asyncTaskCall(String bizKey, ZenInstances instances, OperationParameters parameters, TaskHandler handler, String user) {
        asyncTaskCall(bizKey, instances, parameters, handler, (Class<? extends OperationTaskChecker>) ClassUtils.getUserClass(handler),
                user);
    }

    @SuppressWarnings("unchecked")
    public <T> void asyncCall(String bizKey, ZenInstances instances, OperationParameters parameters, String user) {
        AsyncHandler<T> handler = (AsyncHandler<T>) HandlerCenter.getByType(bizKey);
        if (handler == null) {
            throw new IllegalStateException("handle not found by bizKey :" + bizKey);
        }
        asyncCall(bizKey, instances, parameters, handler, user);
    }

    @SuppressWarnings("unchecked")
    public <T> void asyncCall(String bizKey, String group, ZenInstances instances, OperationParameters parameters, String user) {
        AsyncHandler<T> handler = (AsyncHandler<T>) HandlerCenter.getByType(group, bizKey);
        if (handler == null) {
            throw new IllegalStateException("handle not found by bizKey :" + bizKey);
        }
        asyncCall(bizKey, instances, parameters, handler, user);
    }

    /**
     * 一次远程操作会影响多个实例
     * @param bizKey 业务key
     * @param instances 实例
     * @param caller 调用方
     * @param receiver 接收方
     * @param user user
     */
    @SuppressWarnings("unchecked")
    public <T> void asyncCall(String bizKey, ZenInstances instances, OperationParameters parameters, AsyncCaller<T> caller,
                              Class<? extends CallbackReceiver> receiver, String user) {
        caller.preExecute(instances);
        ZenOperation operation = operationService.saveCallbackOperation(bizKey, instances, parameters, getCallerIdentifier(caller),
                getReceiverConfig(receiver), OperationStatus.START, user);
        OperationContext.getContext().setOperation(operation);
        try {
            T result = caller.execute(instances, parameters);
            operationService.onOperationInvoked(operation.getId());

            if (receiver != null && result instanceof ListenableFuture) {
                Futures.addCallback(((ListenableFuture) result), new FutureCallback<CallbackResponse>() {
                    @Override
                    public void onSuccess(CallbackResponse result) {
                        receiveCallback(operation.getId(), result);
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        receiveCallback(operation.getId(), new CallbackResponseAdaptor(false, throwable.getMessage()));
                    }
                }, executor);
            } else {
                //operationDone
            }
            caller.afterExecute(result);
        } catch (Exception e) {
            operationService.onOperationException(operation.getId(), e.getMessage());
            throwIfUnchecked(e);
        } finally {
            OperationContext.removeContext();
        }
    }

    @Override
    public void destroy() {
        if (receiverExecutorService != null) {
            receiverExecutorService.shutdown();
        }
        if (taskCheckerExecutorService != null) {
            taskCheckerExecutorService.shutdown();
        }
        if (executor != null) {
            executor.shutdown();
        }
    }

    private TaskConfigBean getTaskConfig(Class<? extends OperationTaskChecker> taskChecker) {
        return callbackReceiverParserFactory.parseTask(taskChecker);
    }

    private void handleExpiredOperations() {
        try {
            List<Integer> operationIds = operationService.getExpiredOperationIds();
            operationIds.forEach(operationId -> executor.execute(() -> doHandleExpireOperation(operationId)));
        } catch (Exception e) {
            LOGGER.error("callback operation handle", e);
        }
    }

    private void handleTaskOperations() {
        try {
            List<OperationDO> operations = operationService.getUnDoneTaskOperations();
            operations.forEach(operation -> {
                executor.execute(() -> doHandleTaskOperation(operation));
            });
        } catch (Exception e) {
            LOGGER.error("task operation error", e);
        }

    }

    private void doHandleTaskOperation(OperationDO operation) {
        OperationTaskDO operationTaskDO = operationService.getOperationTask(operation.getId());
        if (operationTaskDO == null || !timeToHandle(operationTaskDO)) {
            return;
        }
        try {
            OperationTaskChecker taskChecker = getTaskChecker(operationTaskDO);
            ZenInstances instances = operationService.getOperationInstances(operation.getId());
            TaskId taskId = new TaskId(operationTaskDO.getTaskId());
            TaskResult result = taskChecker.check(taskId, instances);
            if (result.isDone()) {
                operationService.taskOperationDone(operation.getId(), result.getStatus(), result.getMsg());
                taskChecker.onDone(taskId, instances, result, operation.getUserUuid());
            } else {
                operationService.taskOperationNotDone(operation.getId(), result.getStatus(), result.getMsg());
            }
        } catch (Exception e) {
            // todo retry
            LOGGER.error("handle exception occurs", e);
            operationService.onTaskOperationException(operation.getId(), e.getMessage());
        }
    }

    private OperationTaskChecker getTaskChecker(OperationTaskDO operationTaskDO) {
        return callbackReceiverParserFactory.newTaskChecker(
                new TaskConfigBean(operationTaskDO.getPeriod(), operationTaskDO.getTaskCaller(), operationTaskDO.getTaskName()));
    }

    private boolean timeToHandle(OperationTaskDO operationTaskDO) {
        long period = operationTaskDO.getPeriod();
        Date lastExecTime = operationTaskDO.getLastCallTime();
        if (lastExecTime == null && !StringUtils.isEmpty(operationTaskDO.getTaskId())) {
            return true;
        } else {
            return DateUtils.add(lastExecTime, period, TimeUnit.MILLISECONDS).before(new Date());
        }
    }

    private void doHandleExpireOperation(Integer operationId) {
        try {
            receiveCallback(operationId, CallbackResponse.EXPIRE);
        } catch (Exception e) {
            LOGGER.error("handle expired operation exception, operationId: " + operationId, e);
        }
    }

    public <T> void asyncCall(String bizKey, ZenInstance instance, OperationParameters parameters, AsyncCaller<T> caller,
                              Class<? extends CallbackReceiver> receiver, String user) {
        validateParam(instance);
        asyncCall(bizKey, wrapOneInstances(instance), parameters, caller, receiver, user);
    }

    private ZenInstances wrapOneInstances(ZenInstance instance) {
        return ZenInstances.builder().type(instance.getType()).instance(instance.getInstanceId()).build();
    }

    public <T> void asyncCall(String bizKey, ZenInstances instances, OperationParameters parameters, AsyncHandler<T> asyncHandler,
                              String user) {
        asyncCall(bizKey, instances, parameters, asyncHandler, asyncHandler.getClass(), user);
    }

    private String getCallerIdentifier(Object caller) {
        return caller.getClass().getName();
    }

    private ReceiverConfigBean getReceiverConfig(Class<? extends CallbackReceiver> receiver) {
        if (receiver == null) {
            return null;
        }
        return callbackReceiverParserFactory.parseReceiver(receiver);
    }

    private CallbackReceiver getReceiver(CallbackOperation operation) {
        ReceiverConfigBean receiver = operation.getReceiverConfigBean();
        if (receiver == null) {
            return null;
        }
        return callbackReceiverParserFactory.newCallbackReceiver(receiver);
    }

    private void validateParam(ZenInstance zenInstance) {
        if (zenInstance == null || StringUtils.isEmpty(zenInstance.getInstanceId()) || StringUtils.isEmpty(zenInstance.getType())) {
            throw new IllegalArgumentException("operInstance, type or instanceId can't be null");
        }
    }

}
