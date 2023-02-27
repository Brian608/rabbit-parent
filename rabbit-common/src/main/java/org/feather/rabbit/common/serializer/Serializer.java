package org.feather.rabbit.common.serializer;

/**
 * @projectName: rabbit-parent
 * @package: org.feather.rabbit.common.serializer
 * @className: serializer
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2023-02-27 11:21
 * @version: 1.0
 */

public interface Serializer {

    byte[] serializeRaw(Object data);

    String serialize(Object data);

    <T> T deserialize(String content);

    <T> T deserialize(byte[] content);
}
