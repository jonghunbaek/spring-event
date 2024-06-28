package com.example.springevent.common.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Scheduler;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@EnableCaching
@Configuration
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager("ticket");
        caffeineCacheManager.setCaffeine(Caffeine.newBuilder()
            .expireAfterAccess(3000, TimeUnit.MICROSECONDS)
            .maximumSize(1000)
            .scheduler(Scheduler.systemScheduler()));

        return caffeineCacheManager;
    }
}
