package org.feather.rabbit.common.serializer;

/**
 * @projectName: rabbit-parent
 * @package: org.feather.rabbit.common.serializer
 * @className: SerializerFactory
 * @author: feather(杜雪松)
 * @description: 序列号和反序列化
 * @since: 2023-02-27 11:20
 * @version: 1.0
 */

public interface SerializerFactory {

    Serializer create();

}
