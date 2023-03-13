package org.feather.rabbit.task.annotation;

import org.feather.rabbit.task.autoconfigure.JobParserAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @projectName: rabbit-parent
 * @package: org.feather.rabbit.task.annotation
 * @className: EnableElasticJob
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2023-03-06 14:19
 * @version: 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(JobParserAutoConfiguration.class)
public @interface   EnableElasticJob {

}
