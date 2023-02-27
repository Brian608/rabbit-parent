package org.feather.rabbit.common.convert;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;

import com.google.common.base.Preconditions;
/**
 * @projectName: rabbit-parent
 * @package: org.feather.rabbit.common.convert
 * @className: RabbitMessageConverter
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2023-02-27 11:47
 * @version: 1.0
 */

public class RabbitMessageConverter implements MessageConverter {

    private GenericMessageConverter delegate;

//	private final String delaultExprie = String.valueOf(24 * 60 * 60 * 1000);

    public RabbitMessageConverter(GenericMessageConverter genericMessageConverter) {
        Preconditions.checkNotNull(genericMessageConverter);
        this.delegate = genericMessageConverter;
    }

    @Override
    public Message toMessage(Object object, MessageProperties messageProperties) throws MessageConversionException {
//		messageProperties.setExpiration(delaultExprie);
        org.feather.rabbit.api.Message message = (org.feather.rabbit.api.Message)object;
        messageProperties.setDelay(message.getDelayMills());
        return this.delegate.toMessage(object, messageProperties);
    }

    @Override
    public Object fromMessage(Message message) throws MessageConversionException {
        org.feather.rabbit.api.Message msg = (org.feather.rabbit.api.Message) this.delegate.fromMessage(message);
        return msg;
    }

}
