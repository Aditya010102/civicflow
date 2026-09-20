package com.civicflow.ratelimit;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class RateLimitService {

    private final StringRedisTemplate redisTemplate;
    private final RateLimitProperties properties;

    public RateLimitService(
            StringRedisTemplate redisTemplate,
            RateLimitProperties properties
    ) {
        this.redisTemplate = redisTemplate;
        this.properties = properties;
    }

    public boolean isAllowed(String clientKey) {

        if (!properties.isEnabled()) {
            return true;
        }

        String key =
                "civicflow:rate-limit:"
                        + clientKey;

        Long count =
                redisTemplate.opsForValue().increment(key);

        if (count == null) {
            return false;
        }

        if (count == 1) {
            redisTemplate.expire(
                    key,
                    Duration.ofMinutes(1)
            );
        }

        return count <= properties.getRequestsPerMinute();
    }
}