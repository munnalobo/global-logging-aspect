package com.neuron.logging.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("com.neuron.logging")
@PropertySource(value = "classpath:application.properties")
public class TestConfig {
//    @Bean
//    public LoggingAspect loggingAspect(){
//        return Aspects.aspectOf(LoggingAspect.class);
//    }


//    @Bean("spanIdInterceptor")
//    @Qualifier("spanIdInterceptor")
//    public SpanIdInterceptor loggingAspect(){
//        return new SpanIdInterceptor();
//    }


}
