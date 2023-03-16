package org.feather.rabbit.producer.service;

import org.feather.rabbit.producer.constant.BrokerMessageStatus;
import org.feather.rabbit.producer.entity.BrokerMessage;
import org.feather.rabbit.producer.mapper.BrokerMessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @projectName: rabbit-parent
 * @package: org.feather.rabbit.producer.service
 * @className: MessageStoreService
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2023-02-28 14:34
 * @version: 1.0
 */
@Service
public class MessageStoreService {
    @Autowired
    private BrokerMessageMapper brokerMessageMapper;

    public  int insert(BrokerMessage brokerMessage){
        return  this.brokerMessageMapper.insert(brokerMessage);
    }

    public  BrokerMessage selectByMessageId(String messageId){
        return  this.brokerMessageMapper.selectByPrimaryKey(messageId);
    }


    public void success(String messageId) {
        this.brokerMessageMapper.changeBrokerMessageStatus(messageId, BrokerMessageStatus.SEND_OK.getCode(),new Date());
    }
    public void failure(String messageId) {
        this.brokerMessageMapper.changeBrokerMessageStatus(messageId, BrokerMessageStatus.SEND_FAIL.getCode(),new Date());
    }

    public List<BrokerMessage> fetchTimeOutMessage4Retry(BrokerMessageStatus brokerMessageStatus){
      return   this.brokerMessageMapper.queryBrokerMessageStatus4Timeout(brokerMessageStatus.getCode());

    }
    public  int  updateTryCount(String brokerMessageId){
       return this.brokerMessageMapper.update4TryCount(brokerMessageId,new Date());
    }
}
