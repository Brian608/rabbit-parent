package org.feather.rabbit.producer.broker;

import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.feather.rabbit.api.Message;
import org.feather.rabbit.api.MessageType;
import org.feather.rabbit.api.exception.MessageRunTimeException;
import org.feather.rabbit.common.convert.GenericMessageConverter;
import org.feather.rabbit.common.convert.RabbitMessageConverter;
import org.feather.rabbit.common.serializer.Serializer;
import org.feather.rabbit.common.serializer.SerializerFactory;
import org.feather.rabbit.common.serializer.impl.JacksonSerializerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @projectName: rabbit-parent
 * @package: org.feather.rabbit.producer.broker
 * @className: RabbitTemplateContainer
 * @author: feather(杜雪松)
 * @description: RabbitTemplateContainer 池化封装
 * 提高发送的效率
 * 可以根据不通的需求制定不同的rabbitTemplate
 * @since: 2023-02-27 10:43
 * @version: 1.0
 */
@Slf4j
@Component
public class RabbitTemplateContainer  implements  RabbitTemplate.ConfirmCallback{
    private Map<String, RabbitTemplate> rabbitMap= Maps.newConcurrentMap();

    private Splitter splitter=Splitter.on("#");

    private SerializerFactory serializerFactory= JacksonSerializerFactory.INSTANCE;


    @Autowired
    private ConnectionFactory connectionFactory;


    public  RabbitTemplate getTemplate(Message message)  throws MessageRunTimeException {
        Preconditions.checkNotNull(message);
        String topic=message.getTopic();
        RabbitTemplate rabbitTemplate = rabbitMap.get("topic");
        if (rabbitTemplate!=null){
            return  rabbitTemplate;
        }
        log.info("#RabbitTemplateContainer.getTemplate# topic : {} is not exist,create one",topic);

         RabbitTemplate newRabbitTemplate =new RabbitTemplate(connectionFactory);
         newRabbitTemplate.setExchange(topic);
         newRabbitTemplate.setRoutingKey(message.getRoutingKey());
         newRabbitTemplate.setRetryTemplate(new RetryTemplate());
        //添加序列化和反序列化
        GenericMessageConverter gmc=new GenericMessageConverter(serializerFactory.create());
        RabbitMessageConverter rmc=new RabbitMessageConverter(gmc);
        newRabbitTemplate.setMessageConverter(rmc);
        String messageType = message.getMessageType();
        if (!MessageType.RAPID.equals(messageType)){
            newRabbitTemplate.setConfirmCallback(this);

        }
        rabbitMap.putIfAbsent(topic,newRabbitTemplate);
        return  rabbitMap.get(topic);

    }


    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        //具体的消息应答
        List<String> splitToList = splitter.splitToList(correlationData.getId());
        String messageId = splitToList.get(0);
        long sendTime = Long.parseLong(splitToList.get(1));
        if (ack){
            log.info(" send Message is ok,confirm messageId:{},sendTime:{}",messageId,sendTime);
        }else {
            log.error(" send Message is fail,confirm messageId:{},sendTime:{}",messageId,sendTime);
        }
    }
}
