package org.feather.rabbit.api;

import org.feather.rabbit.api.exception.MessageRunTimeException;

import java.util.List;

/**
 * @projectName: rabbit-parent
 * @package: org.feather.rabbit.api
 * @className: MessageProducer
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2023-02-26 10:35
 * @version: 1.0
 */

public interface MessageProducer {
    /**
     * 消息的异步发送，附带Send CallBack回调执行相应的业务逻辑处理
     * @param message
     * @param sendCallback
     * @throws MessageRunTimeException
     */

    void  send(Message message,SendCallback sendCallback)  throws MessageRunTimeException;

    void  send(Message message)  throws MessageRunTimeException;

    void  send(List<Message> messageList)   throws MessageRunTimeException;


}
