package org.feather.rabbit.producer.broker;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @projectName: rabbit-parent
 * @package: org.feather.rabbit.producer.broker
 * @className: MessageHolderAsyncQueue
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2023-03-16 17:24
 * @version: 1.0
 */
@Slf4j
public class MessageHolderAsyncQueue {
    private static final int THREAD_SIZE = Runtime.getRuntime().availableProcessors();

    private static final int QUEUE_SIZE = 10000;

    private static ExecutorService senderAsync =
            new ThreadPoolExecutor(THREAD_SIZE,
                    THREAD_SIZE,
                    60L,
                    TimeUnit.SECONDS,
                    new ArrayBlockingQueue<Runnable>(QUEUE_SIZE),
                    new ThreadFactory() {
                        @Override
                        public Thread newThread(Runnable r) {
                            Thread t = new Thread(r);
                            t.setName("rabbitmq_client_async_sender");
                            return t;
                        }
                    },
                    new java.util.concurrent.RejectedExecutionHandler() {
                        @Override
                        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                            log.error("async sender is error rejected, runnable: {}, executor: {}", r, executor);
                        }
                    });

    public static void submit(Runnable runnable) {
        senderAsync.submit(runnable);
    }
}
