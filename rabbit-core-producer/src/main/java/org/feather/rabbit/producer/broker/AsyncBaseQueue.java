package org.feather.rabbit.producer.broker;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @projectName: rabbit-parent
 * @package: org.feather.rabbit.producer.broker
 * @className: AsyncBaseQueue
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2023-02-26 13:54
 * @version: 1.0
 */
@Slf4j
public class AsyncBaseQueue {

    private static final int THREAD_SIZE = Runtime.getRuntime().availableProcessors();

    private static final int QUEUE_SIZE = 10000;

    private static ExecutorService senderAsync =
            new ThreadPoolExecutor(THREAD_SIZE,
                    THREAD_SIZE,
                    60L,
                    TimeUnit.SECONDS,
                    new ArrayBlockingQueue<Runnable>(QUEUE_SIZE),
                    r -> {
                        Thread t = new Thread(r);
                        t.setName("rabbitmq_client_async_sender");
                        return t;
                    },
                    (r, executor) -> log.error("async sender is error rejected, runnable: {}, executor: {}", r, executor));

    public static void submit(Runnable runnable) {
        senderAsync.submit(runnable);
    }


}
