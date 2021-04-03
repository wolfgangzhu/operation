package com.github.wolfgang.operation.core;

import com.github.wolfgang.operation.core.model.InstanceStatus;
import com.github.wolfgang.operation.core.model.ZenInstance;

import java.util.List;
import java.util.Map;

/**
 * @author wolfgang
 * @date 2020-02-17 18:17:20
 * @version $ Id: AsyncService.java, v 0.1  wolfgang Exp $
 */
public interface OperationUserService {

    List<InstanceStatus> getInstancesStatus(List<String> instances, String type);

    List<InstanceStatus> getInstancesStatus(List<ZenInstance> instances);

    Map<String, InstanceStatus> getInstancesStatusMap(List<String> instances, String type);

    Map<ZenInstance, InstanceStatus> getInstancesStatusMap(List<ZenInstance> instances);

}
