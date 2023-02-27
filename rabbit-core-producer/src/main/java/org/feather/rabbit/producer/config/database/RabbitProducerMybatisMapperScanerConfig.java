package org.feather.rabbit.producer.config.database;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * @projectName: rabbit-parent
 * @package: org.feather.rabbit.producer.config.database
 * @className: RabbitProducerMybatisMapperScanerConfig
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2023-02-27 16:50
 * @version: 1.0
 */
@Configuration
@AutoConfigureAfter(RabbitProducerDataSourceConfiguration.class)
public class RabbitProducerMybatisMapperScanerConfig {

    @Bean(name="rabbitProducerMapperScannerConfigurer")
    public MapperScannerConfigurer rabbitProducerMapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("rabbitProducerSqlSessionFactory");
        mapperScannerConfigurer.setBasePackage("org.feather.rabbit.producer.mapper");
        return mapperScannerConfigurer;
    }

}

