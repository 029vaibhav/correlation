package org.commonsources.correlation.config;

import org.commonsources.correlation.filter.CorrelationIdInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import jakarta.annotation.PostConstruct;

@Configuration
public class CorrelationRestConfig {


    @PostConstruct
    void addCorrelationId() {
        restTemplate().getInterceptors().add(new CorrelationIdInterceptor());
    }

    @Bean
    @ConditionalOnMissingBean(name = "restTemplate")
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
