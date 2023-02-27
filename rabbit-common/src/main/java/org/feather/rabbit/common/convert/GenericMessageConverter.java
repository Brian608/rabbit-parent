package org.feather.rabbit.common.convert;

import org.feather.rabbit.common.serializer.Serializer;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;
import com.google.common.base.Preconditions;

/**
 * @projectName: rabbit-parent
 * @package: org.feather.rabbit.common.converrt
 * @className: GenericMessageConverter
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2023-02-27 11:38
 * @version: 1.0
 */

public class GenericMessageConverter implements MessageConverter {

    private Serializer serializer;

    public GenericMessageConverter(Serializer serializer) {
        Preconditions.checkNotNull(serializer);
        this.serializer = serializer;
    }

    @Override
    public Object fromMessage(org.springframework.amqp.core.Message message) throws MessageConversionException {
        return this.serializer.deserialize(message.getBody());
    }

    @Override
    public org.springframework.amqp.core.Message toMessage(Object object, MessageProperties messageProperties)
            throws MessageConversionException {
        return new org.springframework.amqp.core.Message(this.serializer.serializeRaw(object), messageProperties);
    }

}
