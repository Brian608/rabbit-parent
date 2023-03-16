package org.feather.rabbit.producer.task;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import lombok.extern.slf4j.Slf4j;
import org.feather.rabbit.producer.broker.RabbitBroker;
import org.feather.rabbit.producer.constant.BrokerMessageStatus;
import org.feather.rabbit.producer.entity.BrokerMessage;
import org.feather.rabbit.producer.service.MessageStoreService;
import org.feather.rabbit.task.annotation.ElasticJobConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @projectName: rabbit-parent
 * @package: org.feather.rabbit.producer
 * @className: RetryMessage
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2023-03-13 17:22
 * @version: 1.0
 */
@Slf4j
@Component
@ElasticJobConfig(
        name = "org.feather.rabbit.producer.task.RetryMessageDataFlowJob",
        cron = "0/10 * * * * ?",
        description = "可靠性投递消息补偿任务",
        overwrite = true,
        shardingTotalCount =1

)
public class RetryMessageDataFlowJob  implements DataflowJob<BrokerMessage> {

    @Autowired
    private MessageStoreService messageStoreService;

    @Autowired
    private RabbitBroker rabbitBroker;

    private static  final int MAX_RETRY_COUNT=3;

    @Override
    public List<BrokerMessage> fetchData(ShardingContext shardingContext) {
        List<BrokerMessage> list = this.messageStoreService.fetchTimeOutMessage4Retry(BrokerMessageStatus.SENDING);
        log.info("--------------抓取数据集合,数量:{}----------------",list.size());
        return  list;
    }

    @Override
    public void processData(ShardingContext shardingContext, List<BrokerMessage> dataList) {
        dataList.forEach(brokerMessage -> {
            String messageId = brokerMessage.getMessageId();
            if (brokerMessage.getTryCount()>=MAX_RETRY_COUNT){
                this.messageStoreService.failure(messageId);
                log.warn("----消息重试最终失败,消息id：{}----", messageId);
            }else {
                //每次重新发的时候需要更新一下tryCount字段
                this.messageStoreService.updateTryCount(messageId);
                //发送消息
                this.rabbitBroker.confirmSend(brokerMessage.getMessage());
            }
        });

    }
}
