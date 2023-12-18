package org.vminds.requests.processor.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * Configuration for cache. Need to be adjusted according to the volume of cacheable tables.
 */
@Configuration
@EnableCaching
public class CacheConfig {

    public static final String CUSTOMER_CACHE_NAME = "CustomerCache";
    public static final String IP_BLACKLIST_CACHE_NAME = "IpBlacklist";
    public static final String UA_BLACKLIST_CACHE_NAME = "UaBlacklist";

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager(
                CUSTOMER_CACHE_NAME, IP_BLACKLIST_CACHE_NAME, UA_BLACKLIST_CACHE_NAME
        );
        cacheManager.setCaffeine(caffeineCacheBuilder());
        return cacheManager;
    }

    Caffeine<Object, Object> caffeineCacheBuilder() {
        return Caffeine.newBuilder()
                .initialCapacity(50)
                .maximumSize(500)
                .expireAfterWrite(Duration.ofMinutes(10));
    }

}
