package org.feather.rabbit.producer.broker;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.feather.rabbit.api.Message;
import org.feather.rabbit.api.MessageType;
import org.feather.rabbit.producer.constant.BrokerMessageConst;
import org.feather.rabbit.producer.constant.BrokerMessageStatus;
import org.feather.rabbit.producer.entity.BrokerMessage;
import org.feather.rabbit.producer.service.MessageStoreService;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

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

    @Autowired
    private MessageStoreService messageStoreService;

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
        message.setMessageType(MessageType.RELIANT);
        BrokerMessage bm = messageStoreService.selectByMessageId(message.getMessageId());
        if (bm==null){
            //1 把数据库的消息日志记录好
            Date now=new Date();
            BrokerMessage brokerMessage=new BrokerMessage();
            brokerMessage.setMessageId(message.getMessageId());
            brokerMessage.setStatus(BrokerMessageStatus.SENDING.getCode());
            //try Count 在最开始的时候不需要进行设置
            brokerMessage.setNextRetry(DateUtils.addMinutes(now, BrokerMessageConst.TIMEOUT));
            brokerMessage.setCreateTime(now);
            brokerMessage.setUpdateTime(now);
            messageStoreService.insert(brokerMessage);
        }
        //2.执行真正的发送消息逻辑
        sendKernel(message);
    }

    @Override
    public void sendMessage() {
        List<Message> messages = MessageHolder.clear();
        messages.forEach(message -> {
            MessageHolderAsyncQueue.submit((Runnable) () -> {
                CorrelationData correlationData =
                        new CorrelationData(String.format("%s#%s#%s",
                                message.getMessageId(),
                                System.currentTimeMillis(),
                                message.getMessageType()));
                String topic = message.getTopic();
                String routingKey = message.getRoutingKey();
                RabbitTemplate rabbitTemplate = rabbitTemplateContainer.getTemplate(message);
                rabbitTemplate.convertAndSend(topic, routingKey, message, correlationData);
                log.info("#RabbitBrokerImpl.sendMessages# send to rabbitmq, messageId: {}", message.getMessageId());
            });
        });
    }
}
