package org.feather.rabbit.producer.autoconfigure;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @projectName: rabbit-parent
 * @package: org.feather.rabbit.producer.autoconfigure
 * @className: RabbitProducerAutoConfiguration
 * @author: feather(杜雪松)
 * @description: 自动装配
 * @since: 2023-02-26 11:01
 * @version: 1.0
 *
 */
@Configuration
@ComponentScan({"org.feather.rabbit.producer.*"})
public class RabbitProducerAutoConfiguration {


}
