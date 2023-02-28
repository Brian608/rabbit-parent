package org.feather.rabbit.producer.constant;

/**
 * @projectName: rabbit-parent
 * @package: org.feather.rabbit.producer.constant
 * @className: BrokerMessageStatus
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2023-02-28 14:42
 * @version: 1.0
 */

/**
 * 	$BrokerMessageStatus
 * 	消息的发送状态
 * @author Alienware
 *
 */
public enum BrokerMessageStatus {

    SENDING("0"),
    SEND_OK("1"),
    SEND_FAIL("2"),
    SEND_FAIL_A_MOMENT("3");

    private String code;

    private BrokerMessageStatus(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

}