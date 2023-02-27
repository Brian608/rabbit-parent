package org.feather.rabbit.producer.broker;

import lombok.extern.slf4j.Slf4j;
import org.feather.rabbit.api.Message;
import org.feather.rabbit.api.MessageType;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @projectName: rabbit-parent
 * @package: org.feather.rabbit.producer.broker
 * @className: RabbitBrokerImpl
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2023-02-26 13:44
 * @version: 1.0
 */
@Slf4j
@Component
public class RabbitBrokerImpl  implements  RabbitBroker{

    @Autowired
    private RabbitTemplateContainer rabbitTemplateContainer;

    /**
     * repaidSend 迅速发消息  使用异步线程池发送消息
     * @param message
     */
    @Override
    public void repaidSend(Message message) {
        message.setMessageType(MessageType.RAPID);
        sendKernel(message);

    }

    /**
     * 发送消息核心方法
     * @param message
     */
    private void sendKernel(Message message) {
        AsyncBaseQueue.submit((Runnable)()->{
            CorrelationData correlationData=new CorrelationData(String.format("%s#%s",message.getMessageId(),System.currentTimeMillis()));
            String topic = message.getTopic();
            String routingKey = message.getRoutingKey();
            RabbitTemplate rabbitTemplate = rabbitTemplateContainer.getTemplate(message);
            rabbitTemplate.convertAndSend("exchange",routingKey,message,correlationData);
            log.info("#RabbitBrokerImpl.sendKernel# send to rabbitmq, messageId:{}",message.getMessageId());
        });
    }

    @Override
    public void confirmSend(Message message) {
        message.setMessageType(MessageType.CONFIRM);
        sendKernel(message);
    }

    @Override
    public void reliantSend(Message message) {

    }

    @Override
    public void sendMessage() {

    }
}
