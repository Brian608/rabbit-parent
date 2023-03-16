package org.feather.rabbit.producer.broker;

import com.google.common.collect.Lists;
import org.feather.rabbit.api.Message;

import java.util.List;

/**
 * @projectName: rabbit-parent
 * @package: org.feather.rabbit.producer.broker
 * @className: MessageHolder
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2023-03-16 17:19
 * @version: 1.0
 */

public class MessageHolder {
    private List<Message> messages = Lists.newArrayList();

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static final ThreadLocal<MessageHolder> holder = new ThreadLocal() {
        @Override
        protected Object initialValue() {
            return new MessageHolder();
        }
    };

    public static void add(Message message) {
        holder.get().messages.add(message);
    }

    public static List<Message> clear() {
        List<Message> tmp = Lists.newArrayList(holder.get().messages);
        holder.remove();
        return tmp;
    }
}
