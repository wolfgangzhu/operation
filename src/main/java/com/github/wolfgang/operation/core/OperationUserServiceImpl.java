package com.github.wolfgang.operation.core;

import com.github.wolfgang.operation.core.dal.dataobject.OperationInstanceLatestDO;
import com.github.wolfgang.operation.core.dal.mapper.OperationInstanceLatestMapper;
import com.github.wolfgang.operation.core.model.InstanceStatus;
import com.github.wolfgang.operation.core.model.OperationStatus;
import com.github.wolfgang.operation.core.model.ZenInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.github.wolfgang.operation.utils.Utils.groupBy;
import static com.github.wolfgang.operation.utils.Utils.toMap;

/**
 * @author wolfgang
 * @date 2020-02-17 18:17:38
 * @version $ Id: AsyncServiceImpl.java, v 0.1  wolfgang Exp $
 */
public class OperationUserServiceImpl implements OperationUserService {

    @Autowired
    private OperationInstanceLatestMapper operationInstanceLatestMapper;

    @Override
    public List<InstanceStatus> getInstancesStatus(List<String> instances, String type) {
        if (CollectionUtils.isEmpty(instances)) {
            return Collections.emptyList();
        }
        Example example = new Example(OperationInstanceLatestDO.class);
        example.createCriteria().andIn(OperationInstanceLatestDO.INSTANCE_ID, instances).andEqualTo(OperationInstanceLatestDO.INSTANCE_TYPE,
                type);
        List<OperationInstanceLatestDO> list = operationInstanceLatestMapper.selectByExample(example);

        return convertToList(list);
    }

    private List<InstanceStatus> convertToList(List<OperationInstanceLatestDO> list) {
        return list.stream().map(instance -> new InstanceStatus(instance.getInstanceId(), instance.getInstanceType(),
                OperationStatus.valueOf(instance.getOperStatus()), instance.getBizKey())).collect(Collectors.toList());
    }

    @Override
    public List<InstanceStatus> getInstancesStatus(List<ZenInstance> instances) {
        Map<String, List<ZenInstance>> group = groupBy(instances, ZenInstance::getType);
        List<InstanceStatus> res = new ArrayList<>();
        group.forEach((String type, List<ZenInstance> instanceIds) -> {
            res.addAll(getInstancesStatus(instanceIds.stream().map(ZenInstance::getInstanceId).collect(Collectors.toList()), type));
        });
        return res;
    }

    @Override
    public Map<String, InstanceStatus> getInstancesStatusMap(List<String> instances, String type) {
        List<InstanceStatus> res = getInstancesStatus(instances, type);
        Map<String, InstanceStatus> instancesMap = toMap(res, InstanceStatus::getInstanceId);
        return instancesMap;
    }

    @Override
    public Map<ZenInstance, InstanceStatus> getInstancesStatusMap(List<ZenInstance> instances) {
        List<InstanceStatus> res = getInstancesStatus(instances);

        return toMap(res, x -> new ZenInstance(x.getInstanceType(), x.getInstanceId()));
    }
}
