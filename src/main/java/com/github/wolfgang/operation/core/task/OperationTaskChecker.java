package com.github.wolfgang.operation.core.task;

import com.github.wolfgang.operation.core.model.TaskId;
import com.github.wolfgang.operation.core.model.TaskResult;
import com.github.wolfgang.operation.core.model.ZenInstances;

/**
 * @author wolfgang
 * @date 2020-02-29 18:15:38
 * @version $ Id: OperationTasker.java, v 0.1  wolfgang Exp $
 */
public interface OperationTaskChecker {

    /**
     * 检查是否完成
     * @param taskId taskId
     * @param zenInstances zenInstances
     * @return
     */
    TaskResult check(TaskId taskId, ZenInstances zenInstances);

    void onDone(TaskId taskId, ZenInstances zenInstances, TaskResult result, String userUuid);

}
