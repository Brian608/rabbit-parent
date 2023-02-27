package org.feather.rabbit.common.serializer.impl;

import org.feather.rabbit.api.Message;
import org.feather.rabbit.common.serializer.Serializer;
import org.feather.rabbit.common.serializer.SerializerFactory;

/**
 * @projectName: rabbit-parent
 * @package: org.feather.rabbit.common.serializer.impl
 * @className: JacksonSerializerFactory
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2023-02-27 11:32
 * @version: 1.0
 */

public class JacksonSerializerFactory implements SerializerFactory{

    public static final SerializerFactory INSTANCE = new JacksonSerializerFactory();

    @Override
    public Serializer create() {
        return JacksonSerializer.createParametricType(Message.class);
    }

}
