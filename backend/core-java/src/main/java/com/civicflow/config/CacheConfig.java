package com.civicflow.config;

import com.civicflow.dto.DepartmentResponse;
import tools.jackson.databind.JavaType;
import tools.jackson.databind.ObjectMapper;

import java.time.Duration;
import java.util.List;
import java.util.Map;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public RedisCacheManager cacheManager(
            RedisConnectionFactory connectionFactory,
            ObjectMapper objectMapper
    ) {

        /*
         * The departments cache stores:
         *
         * List<DepartmentResponse>
         *
         * We therefore create a serializer for this exact type.
         */
        JavaType departmentListType =
                objectMapper.getTypeFactory()
                        .constructCollectionType(
                                List.class,
                                DepartmentResponse.class
                        );

        JacksonJsonRedisSerializer<List<DepartmentResponse>>
                departmentSerializer =
                new JacksonJsonRedisSerializer<>(
                        objectMapper,
                        departmentListType
                );

        RedisCacheConfiguration defaultConfig =
                RedisCacheConfiguration
                        .defaultCacheConfig()
                        .entryTtl(
                                Duration.ofMinutes(10)
                        )
                        .computePrefixWith(
                                cacheName ->
                                        "civicflow:"
                                                + cacheName
                                                + "::"
                        );

        RedisCacheConfiguration departmentsConfig =
                defaultConfig
                        .entryTtl(
                                Duration.ofMinutes(30)
                        )
                        .serializeValuesWith(
                                RedisSerializationContext
                                        .SerializationPair
                                        .fromSerializer(
                                                departmentSerializer
                                        )
                        );

        return RedisCacheManager
                .builder(connectionFactory)
                .cacheDefaults(defaultConfig)
                .withInitialCacheConfigurations(
                        Map.of(
                                "departments",
                                departmentsConfig
                        )
                )
                .build();
    }
}