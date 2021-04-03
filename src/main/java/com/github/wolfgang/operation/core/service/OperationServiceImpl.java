package com.github.wolfgang.operation.core.service;

import com.github.wolfgang.operation.core.CallbackResponse;
import com.github.wolfgang.operation.core.LastOperation;
import com.github.wolfgang.operation.core.OperationParameters;
import com.github.wolfgang.operation.core.dal.dataobject.OperationCallbackDO;
import com.github.wolfgang.operation.core.dal.dataobject.OperationDO;
import com.github.wolfgang.operation.core.dal.dataobject.OperationInstanceDO;
import com.github.wolfgang.operation.core.dal.dataobject.OperationInstanceLatestDO;
import com.github.wolfgang.operation.core.dal.dataobject.OperationParametersDO;
import com.github.wolfgang.operation.core.dal.dataobject.OperationReceiverDO;
import com.github.wolfgang.operation.core.dal.dataobject.OperationTaskDO;
import com.github.wolfgang.operation.core.dal.enumeration.OperationTypeEnum;
import com.github.wolfgang.operation.core.dal.enumeration.TaskStatusEnum;
import com.github.wolfgang.operation.core.dal.mapper.OperationCallbackDOMapper;
import com.github.wolfgang.operation.core.dal.mapper.OperationHistDOMapper;
import com.github.wolfgang.operation.core.dal.mapper.OperationInstanceLatestMapper;
import com.github.wolfgang.operation.core.dal.mapper.OperationInstanceMapper;
import com.github.wolfgang.operation.core.dal.mapper.OperationParametersMapper;
import com.github.wolfgang.operation.core.dal.mapper.OperationReceiverDOMapper;
import com.github.wolfgang.operation.core.dal.mapper.OperationTaskDOMapper;
import com.github.wolfgang.operation.core.model.CallbackOperation;
import com.github.wolfgang.operation.core.model.OperationStatus;
import com.github.wolfgang.operation.core.model.ReceiverConfigBean;
import com.github.wolfgang.operation.core.model.TaskConfigBean;
import com.github.wolfgang.operation.core.model.TaskId;
import com.github.wolfgang.operation.core.model.ZenInstances;
import com.github.wolfgang.operation.core.model.ZenInstances.InstancesBuilder;
import com.github.wolfgang.operation.core.model.ZenOperation;
import com.github.wolfgang.operation.core.service.helper.DefaultParameterSerializer;
import com.github.wolfgang.operation.core.service.helper.ParameterSerializer;
import com.github.wolfgang.operation.utils.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author wolfgang
 * @date 2020-02-14 18:41:11
 * @version $ Id: OperationServiceImpl.java, v 0.1  wolfgang Exp $
 */
@Service
public class OperationServiceImpl implements OperationService {

    @Autowired
    private OperationInstanceMapper operationInstanceMapper;

    @Autowired
    private OperationHistDOMapper operationMapper;

    @Autowired
    private OperationParametersMapper operationParametersMapper;

    @Autowired
    private OperationInstanceLatestMapper operationInstanceLatestMapper;

    @Autowired
    private OperationCallbackDOMapper operationCallbackDOMapper;

    @Autowired
    private OperationReceiverDOMapper operationReceiverDOMapper;

    @Autowired
    private OperationTaskDOMapper operationTaskDOMapper;

    ParameterSerializer parameterSerializer = new DefaultParameterSerializer();

    @Override
    public LastOperation getLastOperation(String instanceId, String type) {
        Example example = new Example(OperationInstanceDO.class);
        example.createCriteria().andEqualTo(OperationInstanceDO.INSTANCE_ID, instanceId).andEqualTo(OperationInstanceDO.INSTANCE_TYPE,
                type);

        OperationInstanceDO saved = operationInstanceMapper.selectOneByExample(example);
        LastOperation lastOperation = new LastOperation();
        BeanUtils.copyProperties(saved, lastOperation);
        return lastOperation;
    }

    @Override
    @Transactional
    public ZenOperation saveCallbackOperation(String bizKey, ZenInstances instances, OperationParameters parameters,
                                              String callerIdentifier, ReceiverConfigBean receiverConfig, OperationStatus operationStatus,
                                              String user) {
        OperationDO operationDO = saveOperationStart(bizKey, callerIdentifier, operationStatus, OperationTypeEnum.callback, user);
        OperationReceiverDO receiver = saveCallbackReceiver(operationDO.getId(), receiverConfig);
        saveOperationParameter(operationDO.getId(), parameters);
        saveOperationInstances(operationDO.getId(), instances, bizKey, OperationStatus.START);
        updateOperationLatestInstance(bizKey, operationDO.getId(), instances, OperationStatus.START);
        return convertToZenOperation(operationDO, receiver, instances, parameters);
    }

    @Override
    public ZenOperation saveTaskOperation(String bizKey, ZenInstances instances, OperationParameters parameters, String callerIdentifier,
                                          TaskConfigBean taskConfig, OperationStatus status, String user) {
        OperationDO operationDO = saveOperationStart(bizKey, callerIdentifier, status, OperationTypeEnum.task, user);

        saveTaskChecker(operationDO.getId(), taskConfig);
        saveOperationParameter(operationDO.getId(), parameters);
        saveOperationInstances(operationDO.getId(), instances, bizKey, OperationStatus.START);
        updateOperationLatestInstance(bizKey, operationDO.getId(), instances, OperationStatus.START);

        return convertToZenOperation(operationDO, instances, parameters);
    }

    private OperationTaskDO saveTaskChecker(Integer operationId, TaskConfigBean taskConfig) {
        OperationTaskDO operationTaskDO = new OperationTaskDO();
        operationTaskDO.setIsDone(false);
        operationTaskDO.setOperationId(operationId);
        operationTaskDO.setTaskName(taskConfig.getName());
        operationTaskDO.setTaskCaller(taskConfig.getTaskClass());
        operationTaskDO.setTaskStatus(TaskStatusEnum.START.name());
        operationTaskDO.setPeriod(taskConfig.getPeriodInMs());

        operationTaskDOMapper.insertSelective(operationTaskDO);
        return operationTaskDO;
    }

    private OperationReceiverDO saveCallbackReceiver(Integer operationId, ReceiverConfigBean receiverConfig) {

        OperationReceiverDO operationReceiverDO = new OperationReceiverDO();
        operationReceiverDO.setOperationId(operationId);
        operationReceiverDO.setReceiverName(receiverConfig.getName());
        operationReceiverDO.setReceiverClass(receiverConfig.getReceiverClass());
        operationReceiverDO.setExpireMills(receiverConfig.getExpireInMs());
        operationReceiverDO.setExpireTime(DateUtils.add(receiverConfig.getExpireInMs(), TimeUnit.MILLISECONDS));
        operationReceiverDOMapper.insertSelective(operationReceiverDO);
        return operationReceiverDO;
    }

    @Override
    public void onOperationException(Integer operId, String errorMsg) {
        doUpdateOperationOnException(operId, errorMsg);
        doUpdateOperationInstancesStatus(operId, OperationStatus.FAIL);
        doUpdateOperationLatestInstancesStatus(operId, OperationStatus.FAIL);
    }

    @Override
    public void onOperationInvoked(Integer operId) {
        OperationDO operationDO = new OperationDO();
        operationDO.setId(operId);
        operationDO.setInvokeTime(new Date());
        operationDO.setIsInvoke(true);
        operationDO.setUpdateTime(new Date());
        operationMapper.updateByPrimaryKeySelective(operationDO);
    }

    @Override
    @Transactional
    public CallbackOperation onCallbackReceived(Integer operationId, CallbackResponse response, byte[] properties) {

        OperationStatus status = operationStatusFromResponse(response);

        doSaveOperationCallback(operationId, response, properties);

        return doOnCallbackReceived(operationId, status, response.getResult());

    }

    private void doSaveOperationCallback(Integer operationId, CallbackResponse response, byte[] properties) {
        OperationCallbackDO callback = new OperationCallbackDO();
        callback.setOperationId(operationId);
        callback.setReceiveTime(new Date());
        callback.setProperties(properties);
        callback.setMsg(response.getResult());
        //TODO callback.setData(response.getData());
        callback.setSuccess(response.isSuccess());
        operationCallbackDOMapper.insertSelective(callback);
    }

    @Override
    public List<Integer> getExpiredOperationIds() {

        return operationMapper.selectExpiredOperations();

    }

    @Override
    public List<OperationDO> getUnDoneTaskOperations() {

        Example example = new Example(OperationDO.class);
        example.createCriteria().andEqualTo(OperationDO.IS_DONE, false).andEqualTo(OperationDO.TYPE, OperationTypeEnum.task);
        return operationMapper.selectByExample(example);
    }

    @Override
    public OperationTaskDO getOperationTask(Integer operationId) {
        Example example = new Example(OperationTaskDO.class);
        example.createCriteria().andEqualTo(OperationTaskDO.OPERATION_ID, operationId);
        return operationTaskDOMapper.selectOneByExample(example);
    }

    @Override
    public ZenInstances getOperationInstances(Integer operationId) {
        List<OperationInstanceDO> instances = doGetLatestInstances(operationId);
        return convertInstances(instances);
    }

    @Override
    public void taskOperationDone(Integer operationId, OperationStatus status, String msg) {
        OperationDO operation = doUpdateOperationStatus(operationId, status, msg);
        if (operation == null) {
            return;
        }
        doUpdateOperationInstancesStatus(operationId, status);
        doUpdateAndGetOperationLatestInstancesStatus(operationId, status);
        doUpdateOperationTaskDone(operationId);
    }

    @Override
    public void taskOperationNotDone(Integer operationId, OperationStatus status, String msg) {
        //更新最后执行时间
        updateTaskInternal(false, operationId, null);
    }

    @Override
    public void onTaskOperationException(Integer operationId, String message) {
        onOperationException(operationId, message);
        doUpdateOperationTaskException(operationId);
    }

    private void doUpdateOperationTaskDone(Integer operationId) {
        updateTaskInternal(true, operationId, TaskStatusEnum.SUCCESS);
    }

    private void doUpdateOperationTaskException(Integer operationId) {
        updateTaskInternal(true, operationId, TaskStatusEnum.EXCEPTION);
    }

    void updateTaskInternal(boolean isDone, Integer operationId, TaskStatusEnum status) {
        Example example = new Example(OperationTaskDO.class);
        example.createCriteria().andEqualTo(OperationTaskDO.OPERATION_ID, operationId);
        OperationTaskDO update = new OperationTaskDO();
        update.setLastCallTime(new Date());
        update.setIsDone(isDone);
        if (status != null) {
            update.setTaskStatus(status.name());
        }
        operationTaskDOMapper.updateByExampleSelective(update, example);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void onTaskOperationInvoked(Integer operId, TaskId taskId) {
        onOperationInvoked(operId);
        updateOperationTaskId(operId, taskId);
    }

    private void updateOperationTaskId(Integer operId, TaskId taskId) {
        OperationTaskDO operationTaskDO = new OperationTaskDO();
        operationTaskDO.setTaskId(taskId.getTaskId());

        Example example = new Example(OperationTaskDO.class);
        example.createCriteria().andEqualTo(OperationTaskDO.OPERATION_ID, operId);
        operationTaskDOMapper.updateByExampleSelective(operationTaskDO, example);
    }

    private CallbackOperation doOnCallbackReceived(Integer operationId, OperationStatus status, String msg) {
        OperationDO operation = doUpdateOperationStatus(operationId, status, msg);
        if (operation == null) {
            return null;
        }
        if (!OperationTypeEnum.callback.equals(operation.getType())) {
            throw new IllegalArgumentException("operation is not a callback type, operationId: " + operationId);
        }
        doUpdateOperationInstancesStatus(operationId, status);

        List<OperationInstanceDO> instances = doUpdateAndGetOperationLatestInstancesStatus(operationId, status);
        ZenInstances zenInstances = convertInstances(instances);

        OperationReceiverDO receiver = getOperationReceiver(operationId);

        OperationParameters parameters = getOperationParameters(operationId);

        return convertToZenOperation(operation, receiver, zenInstances, parameters);
    }

    private OperationReceiverDO getOperationReceiver(Integer operationId) {
        Example example = new Example(OperationReceiverDO.class);
        example.createCriteria().andEqualTo(OperationTaskDO.OPERATION_ID, operationId);
        return operationReceiverDOMapper.selectOneByExample(example);
    }

    private ZenInstances convertInstances(List<OperationInstanceDO> instances) {
        if (CollectionUtils.isEmpty(instances)) {
            return ZenInstances.EMPTY;
        }
        InstancesBuilder builder = ZenInstances.builder();
        instances.forEach(instance -> {
            builder.type(instance.getInstanceType());
            builder.instance(instance.getInstanceId());
        });
        return builder.build();
    }

    private OperationParameters getOperationParameters(Integer operationId) {
        Example example = new Example(OperationParametersDO.class);
        example.createCriteria().andEqualTo(OperationParametersDO.OPERATION_ID, operationId);

        OperationParametersDO operationParameterDO = operationParametersMapper.selectOneByExample(example);
        if (operationParameterDO == null) {
            return null;
        }
        byte[] paramByteArray = operationParameterDO.getParameters();
        return parameterSerializer.deserialize(paramByteArray);
    }

    private List<OperationInstanceDO> doUpdateAndGetOperationLatestInstancesStatus(Integer operationId, OperationStatus status) {
        doUpdateOperationLatestInstancesStatus(operationId, status);
        return doGetLatestInstances(operationId);

    }

    private List<OperationInstanceDO> doGetLatestInstances(Integer operationId) {
        OperationInstanceDO filter = new OperationInstanceDO();
        filter.setOperId(operationId);
        return operationInstanceMapper.select(filter);
    }

    private OperationDO doUpdateOperationStatus(Integer operationId, OperationStatus status, String msg) {
        OperationDO operation = operationMapper.selectByPrimaryKey(operationId);
        if (operation == null) {
            return null;
        }

        if (operation.getIsDone()) {
            return operation;
        }
        operation.setIsDone(true);
        operation.setOperStatus(status.name());
        operation.setUpdateTime(new Date());
        operation.setComment(msg);

        operationMapper.updateByPrimaryKeySelective(operation);
        return operation;
    }

    private OperationStatus operationStatusFromResponse(CallbackResponse response) {
        if (response.isSuccess()) {
            return OperationStatus.SUCCESS;
        } else {
            return OperationStatus.FAIL;
        }
    }

    private ZenOperation convertToZenOperation(OperationDO operationDO, ZenInstances instances, OperationParameters parameters) {
        ZenOperation zenOperation = new ZenOperation();
        zenOperation.setBizKey(operationDO.getBizKey());
        zenOperation.setInstances(instances);
        zenOperation.setId(operationDO.getId());
        zenOperation.setParameters(parameters);
        return zenOperation;
    }

    private CallbackOperation convertToZenOperation(OperationDO operationDO, OperationReceiverDO receiver, ZenInstances instances,
                                                    OperationParameters parameters) {
        CallbackOperation zenOperation = new CallbackOperation();
        zenOperation.setBizKey(operationDO.getBizKey());
        zenOperation.setInstances(instances);
        zenOperation.setId(operationDO.getId());
        zenOperation.setParameters(parameters);
        ReceiverConfigBean bean = new ReceiverConfigBean(receiver.getExpireMills(), receiver.getReceiverClass(),
                receiver.getReceiverName());
        zenOperation.setReceiverConfigBean(bean);

        return zenOperation;
    }

    private OperationDO saveOperationStart(String bizKey, String callerIdentifier, OperationStatus operationStatus, OperationTypeEnum type,
                                           String user) {
        OperationDO operation = new OperationDO();
        operation.setCreateTime(new Date());
        operation.setBizKey(bizKey);
        operation.setOperStatus(operationStatus.name());
        operation.setIsInvoke(false);
        operation.setIsDone(false);
        operation.setCaller(callerIdentifier);
        operation.setType(type);
        operation.setUserUuid(StringUtils.isEmpty(user) ? "-1" : user);
        operationMapper.insertSelective(operation);

        return operation;
    }

    private void doUpdateOperationInstancesStatus(Integer operationId, OperationStatus status) {
        OperationInstanceDO update = new OperationInstanceDO();
        update.setOperStatus(status.name());
        Example example = new Example(OperationInstanceDO.class);
        example.createCriteria().andEqualTo(OperationInstanceDO.OPER_ID, operationId);
        operationInstanceMapper.updateByExampleSelective(update, example);
    }

    private void doUpdateOperationOnException(Integer id, String errorMsg) {
        OperationDO update = new OperationDO();
        update.setId(id);
        update.setIsInvoke(true);
        update.setIsDone(true);
        update.setOperStatus(OperationStatus.FAIL.name());
        update.setComment(errorMsg);
        update.setUpdateTime(new Date());
        operationMapper.updateByPrimaryKeySelective(update);
    }

    private OperationInstanceLatestDO doGetLastOperation(String instanceId, String type) {
        Example example = new Example(OperationInstanceLatestDO.class);
        example.createCriteria().andEqualTo(OperationInstanceLatestDO.INSTANCE_ID, instanceId).andEqualTo(
                OperationInstanceLatestDO.INSTANCE_TYPE, type);

        OperationInstanceLatestDO saved = operationInstanceLatestMapper.selectOneByExample(example);
        return saved;
    }

    private void doUpdateOperationLatestInstancesStatus(Integer operId, OperationStatus status) {
        OperationInstanceLatestDO update = new OperationInstanceLatestDO();
        update.setOperStatus(status.name());
        Example example = new Example(OperationInstanceDO.class);
        example.createCriteria().andEqualTo(OperationInstanceDO.OPER_ID, operId);
        operationInstanceLatestMapper.updateByExampleSelective(update, example);
    }

    private void updateOperationLatestInstance(String bizKey, Integer operationId, ZenInstances instances, OperationStatus status) {
        if (instances == null || CollectionUtils.isEmpty(instances.getInstanceIds())) {
            return;
        }
        instances.getInstanceIds().forEach(instanceId -> {
            OperationInstanceLatestDO lastOperationInstance = doGetLastOperation(instanceId, instances.getType());
            if (lastOperationInstance == null) {
                doSaveLastOperation(bizKey, instanceId, instances.getType(), operationId, status);
            } else {
                lastOperationInstance.setOperStatus(status.name());
                lastOperationInstance.setBizKey(bizKey);
                lastOperationInstance.setOperId(operationId);
                doUpdateOperationLatestInstancesStatus(lastOperationInstance);
            }

        });
    }

    private void doUpdateOperationLatestInstancesStatus(OperationInstanceLatestDO lastOperationInstance) {
        operationInstanceLatestMapper.updateByPrimaryKeySelective(lastOperationInstance);
    }

    private void doSaveLastOperation(String bizKey, String instanceId, String type, Integer operationId, OperationStatus status) {
        OperationInstanceLatestDO insert = new OperationInstanceLatestDO();
        insert.setInstanceType(type);
        insert.setInstanceId(instanceId);
        insert.setOperId(operationId);
        insert.setOperStatus(status.name());
        insert.setBizKey(bizKey);
        operationInstanceLatestMapper.insertSelective(insert);
    }

    private void saveOperationParameter(Integer id, OperationParameters parameters) {
        if (parameters == null || parameters.isEmpty()) {
            return;
        }
        OperationParametersDO parametersDO = new OperationParametersDO();
        parametersDO.setOperId(id);
        parametersDO.setParameters(parameterSerializer.serialize(parameters));
        operationParametersMapper.insertSelective(parametersDO);
    }

    private void saveOperationInstances(Integer operationId, ZenInstances instances, String bizKey, OperationStatus status) {
        if (instances == null || CollectionUtils.isEmpty(instances.getInstanceIds())) {
            return;
        }
        List<OperationInstanceDO> savedList = instances.getInstanceIds().stream().map(id -> {
            OperationInstanceDO saved = new OperationInstanceDO();
            saved.setInstanceId(id);
            saved.setInstanceType(instances.getType());
            saved.setOperStatus(status.name());
            saved.setOperId(operationId);
            saved.setBizKey(bizKey);
            return saved;
        }).collect(Collectors.toList());
        operationInstanceMapper.insertList(savedList);
    }

}
