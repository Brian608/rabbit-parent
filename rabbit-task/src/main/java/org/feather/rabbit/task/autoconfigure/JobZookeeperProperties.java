package org.feather.rabbit.task.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @projectName: rabbit-parent
 * @package: org.feather.rabbit.task.autoconfigure
 * @className: JobZookeeperProperties
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2023-03-06 13:57
 * @version: 1.0
 */
@ConfigurationProperties(prefix = "elastic.job.zk")
@Data
public class JobZookeeperProperties {

    private String namespace;

    private String serverLists;

    private int maxRetries = 3;

    private int connectionTimeoutMilliseconds = 15000;

    private int sessionTimeoutMilliseconds = 60000;

    private int baseSleepTimeMilliseconds = 1000;

    private int maxSleepTimeMilliseconds = 3000;

    private String digest = "";

}
