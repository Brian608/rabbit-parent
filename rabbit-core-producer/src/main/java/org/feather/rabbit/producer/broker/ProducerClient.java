package org.feather.rabbit.producer.broker;

import com.google.common.base.Preconditions;
import org.feather.rabbit.api.Message;
import org.feather.rabbit.api.MessageProducer;
import org.feather.rabbit.api.MessageType;
import org.feather.rabbit.api.SendCallback;
import org.feather.rabbit.api.exception.MessageRunTimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @projectName: rabbit-parent
 * @package: org.feather.rabbit.producer.broker
 * @className: ProducerClient
 * @author: feather(杜雪松)
 * @description: 发送消息的实际实现类
 * @since: 2023-02-26 12:04
 * @version: 1.0
 */
@Component
public class ProducerClient  implements MessageProducer {
    @Autowired
    private RabbitBroker rabbitBroker;

    @Override
    public void send(Message message, SendCallback sendCallback) throws MessageRunTimeException {
        Preconditions.checkNotNull(message.getTopic());
        String messageType = message.getMessageType();
        switch (messageType){
            case MessageType.RAPID:

                break;
            case MessageType.CONFIRM:

                break;
            case MessageType.RELIANT:

                break;
            default: break;
        }
    }

    @Override
    public void send(Message message) throws MessageRunTimeException {

    }

    @Override
    public void send(List<Message> messageList) throws MessageRunTimeException {

    }
}
