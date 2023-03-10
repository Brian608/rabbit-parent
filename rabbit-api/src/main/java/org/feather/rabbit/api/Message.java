package org.feather.rabbit.api;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @projectName: rabbit-parent
 * @package: org.feather.rabbit.api
 * @className: Message
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2023-02-24 17:48
 * @version: 1.0
 */
@Data
public class Message implements Serializable {

    /**
     * 消息唯一id
     */
    private String messageId;

    /**
     * 消息主题
     */
    private String topic;

    /**
     * 消息的路由规则
     */
    private String routingKey="";

    private Map<String, Object> attributes=new HashMap<String, Object>();

    /**
     * 延迟消息的参数设置
     */
    private int delayMills;

    /**
     * 消息类型  默认为confirm类型
     */
    private String messageType=MessageType.CONFIRM;

    public Message(String messageId, String topic, String routingKey, Map<String, Object> attributes, int delayMills, String messageType) {
        this.messageId = messageId;
        this.topic = topic;
        this.routingKey = routingKey;
        this.attributes = attributes;
        this.delayMills = delayMills;
        this.messageType = messageType;
    }

    public Message() {
        super();
    }
}
