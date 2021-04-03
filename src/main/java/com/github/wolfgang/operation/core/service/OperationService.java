package com.github.wolfgang.operation.core.service;

import com.github.wolfgang.operation.core.CallbackResponse;
import com.github.wolfgang.operation.core.LastOperation;
import com.github.wolfgang.operation.core.OperationParameters;
import com.github.wolfgang.operation.core.dal.dataobject.OperationDO;
import com.github.wolfgang.operation.core.dal.dataobject.OperationTaskDO;
import com.github.wolfgang.operation.core.model.CallbackOperation;
import com.github.wolfgang.operation.core.model.OperationStatus;
import com.github.wolfgang.operation.core.model.ReceiverConfigBean;
import com.github.wolfgang.operation.core.model.TaskConfigBean;
import com.github.wolfgang.operation.core.model.TaskId;
import com.github.wolfgang.operation.core.model.ZenInstances;
import com.github.wolfgang.operation.core.model.ZenOperation;

import java.util.List;

/**
 * @author wolfgang
 * @date 2020-02-14 18:41:01
 * @version $ Id: OperationService.java, v 0.1  wolfgang Exp $
 */
public interface OperationService {

    LastOperation getLastOperation(String instanceId, String type);

    ZenOperation saveCallbackOperation(String bizKey, ZenInstances instances, OperationParameters parameters, String callerIdentifier,
                                       ReceiverConfigBean receiverConfig, OperationStatus operationStatus, String user);

    ZenOperation saveTaskOperation(String bizKey, ZenInstances instances, OperationParameters parameters, String callerIdentifier,
                                   TaskConfigBean taskConfig, OperationStatus status, String user);

    void onOperationException(Integer operId, String errorMsg);

    void onOperationInvoked(Integer operId);

    void onTaskOperationInvoked(Integer operId, TaskId taskId);

    CallbackOperation onCallbackReceived(Integer operId, CallbackResponse response, byte[] properties);

    List<Integer> getExpiredOperationIds();

    List<OperationDO> getUnDoneTaskOperations();

    OperationTaskDO getOperationTask(Integer operationId);

    ZenInstances getOperationInstances(Integer operationId);

    void taskOperationDone(Integer operationId, OperationStatus status, String msg);

    void taskOperationNotDone(Integer operationId, OperationStatus status, String msg);

    void onTaskOperationException(Integer operationId, String message);
}
