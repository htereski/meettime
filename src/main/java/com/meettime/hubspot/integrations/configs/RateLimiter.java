package com.meettime.hubspot.integrations.configs;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;

@Component
public class RateLimiter {
    
    @Value("${hubspot.api.rate.limit}")
    private Integer rateLimit;

    private final Map<String, Bucket> cache = new ConcurrentHashMap<>();

    public boolean isAllowed(String key) {
        Bucket bucket = this.cache.computeIfAbsent(key, this::newBucket);
        return bucket.tryConsume(1);
    }

    private Bucket newBucket(String key) {
        Refill refill = Refill.greedy(this.rateLimit, Duration.ofMinutes(10));
        Bandwidth limit = Bandwidth.classic(this.rateLimit, refill);
        return Bucket.builder().addLimit(limit).build();
    }
}
