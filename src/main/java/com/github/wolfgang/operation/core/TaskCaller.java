package com.github.wolfgang.operation.core;

import com.github.wolfgang.operation.core.model.TaskId;
import com.github.wolfgang.operation.core.model.ZenInstances;

/**
 * @author wolfgang
 * @date 2020-02-29 19:24:52
 * @version $ Id: TaskCaller.java, v 0.1  wolfgang Exp $
 */
public interface TaskCaller {

    /**
     * return taskId
     * @param instances 实例
     * @param parameters
     * @return
     */
    TaskId execute(ZenInstances instances, OperationParameters parameters);
}
