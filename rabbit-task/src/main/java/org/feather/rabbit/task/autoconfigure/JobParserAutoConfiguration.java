package org.feather.rabbit.task.autoconfigure;

import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import lombok.extern.slf4j.Slf4j;
import org.feather.rabbit.task.parser.ElasticJobConfParser;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @projectName: rabbit-parent
 * @package: org.feather.rabbit.task.autoconfigure
 * @className: JobParserAutoConfiguration
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2023-03-06 13:50
 * @version: 1.0
 */
@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "elastic.job.zk",name = {"namespace","serverLists"})
@EnableConfigurationProperties(JobZookeeperProperties.class)
public class JobParserAutoConfiguration {
    @Bean(initMethod = "init")
    public ZookeeperRegistryCenter zookeeperRegistryCenter(JobZookeeperProperties jobZookeeperProperties){
        ZookeeperConfiguration zkConfig=new ZookeeperConfiguration(jobZookeeperProperties.getServerLists(), jobZookeeperProperties.getNamespace());
        zkConfig.setBaseSleepTimeMilliseconds(zkConfig.getBaseSleepTimeMilliseconds());
        zkConfig.setMaxSleepTimeMilliseconds(zkConfig.getMaxSleepTimeMilliseconds());
        zkConfig.setConnectionTimeoutMilliseconds(zkConfig.getConnectionTimeoutMilliseconds());
        zkConfig.setSessionTimeoutMilliseconds(zkConfig.getSessionTimeoutMilliseconds());
        zkConfig.setMaxRetries(zkConfig.getMaxRetries());
        log.info("初始化job注册中心配置成功，zkAddress:{},namespace:{}",jobZookeeperProperties.getServerLists(),jobZookeeperProperties.getNamespace());
        return new ZookeeperRegistryCenter(zkConfig);
    }

    @Bean
    public ElasticJobConfParser elasticJobConfParser(JobZookeeperProperties jobZookeeperProperties,ZookeeperRegistryCenter zookeeperRegistryCenter){
        return  new ElasticJobConfParser(jobZookeeperProperties,zookeeperRegistryCenter);
    }


}
