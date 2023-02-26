package org.feather.rabbit.api;

/**
 * @projectName: rabbit-parent
 * @package: org.feather.rabbit.api
 * @className: SendCallback
 * @author: feather(杜雪松)
 * @description:回调函数处理
 * @since: 2023-02-26 10:37
 * @version: 1.0
 */

public interface SendCallback {

    void  onSuccess();

    void  onFail();


}
