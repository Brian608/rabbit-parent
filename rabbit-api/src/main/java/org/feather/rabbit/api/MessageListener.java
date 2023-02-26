package org.feather.rabbit.api;

/**
 * @projectName: rabbit-parent
 * @package: org.feather.rabbit.api
 * @className: MessageListener
 * @author: feather(杜雪松)
 * @description: 消费者监听消息
 * @since: 2023-02-26 10:35
 * @version: 1.0
 */

public interface MessageListener {

    void  onMessage(Message message);



}
