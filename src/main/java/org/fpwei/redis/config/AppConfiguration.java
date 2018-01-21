package org.fpwei.redis.config;

import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;

@Configuration
@Import(RedisConfiguration.class)
@ComponentScan(basePackages = "org.fpwei.redis.service")
@PropertySource("classpath:/config.properties")
public class AppConfiguration {
    @Bean
    public ConversionService conversionService() {
        return new DefaultConversionService();
    }

    @Bean
    public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
