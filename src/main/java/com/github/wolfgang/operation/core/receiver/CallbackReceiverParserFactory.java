package com.github.wolfgang.operation.core.receiver;

import com.github.wolfgang.operation.core.model.ReceiverConfigBean;
import com.github.wolfgang.operation.core.model.TaskConfigBean;
import com.github.wolfgang.operation.core.task.OperationTaskChecker;

/**
 * @author wolfgang
 * @date 2020-02-14 16:55:32
 * @version $ Id: CallbackReceiver.java, v 0.1  wolfgang Exp $
 */
public interface CallbackReceiverParserFactory {

    ReceiverConfigBean parseReceiver(Class<? extends CallbackReceiver> receiverClass);

    TaskConfigBean parseTask(Class<? extends OperationTaskChecker> receiverClass);

    CallbackReceiver newCallbackReceiver(ReceiverConfigBean configBean);

    OperationTaskChecker newTaskChecker(TaskConfigBean configBean);
}
