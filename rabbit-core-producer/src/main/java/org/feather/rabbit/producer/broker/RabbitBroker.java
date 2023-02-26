package org.feather.rabbit.producer.broker;

import org.feather.rabbit.api.Message;

/**
 * @projectName: rabbit-parent
 * @package: org.feather.rabbit.producer.broker
 * @className: RabbitBroker
 * @author: feather(杜雪松)
 * @description:具体发送不同类型消息的接口
 * @since: 2023-02-26 13:41
 * @version: 1.0
 */

public interface RabbitBroker {


    void  repaidSend(Message message);

    void confirmSend(Message message);

    void  reliantSend(Message message);

    void  sendMessage();
}
