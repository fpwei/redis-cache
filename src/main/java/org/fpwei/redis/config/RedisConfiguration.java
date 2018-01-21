package org.fpwei.redis.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

import java.time.Duration;
import java.util.List;

import static org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;

@EnableCaching
public class RedisConfiguration {

    @Bean
    public RedisConnectionFactory redisConnectionFactory(@Value("${redis.master.name}") String masterName,
                                                         @Value("${redis.sentinel.host}") List<String> hosts,
                                                         @Value("${redis.sentinel.port}") List<Integer> ports) {

        RedisSentinelConfiguration configuration = new RedisSentinelConfiguration().master(masterName);

        for (int index = 0; index < hosts.size(); ++index) {
            configuration.sentinel(hosts.get(index), ports.get(index));
        }

        return new JedisConnectionFactory(configuration);
    }


    @Bean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory factory) {
        return new StringRedisTemplate(factory);
    }

    @Bean
    public RedisCacheManager redisCacheManager(RedisConnectionFactory factory, @Value("${redis.cache.expire}") long expireTime) {
        SerializationPair serializationPair = SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer());

        RedisCacheConfiguration cacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(expireTime))
                .serializeValuesWith(serializationPair);

        RedisCacheManager redisCacheManager = RedisCacheManager
                .builder(factory)
                .cacheDefaults(cacheConfiguration)
                .build();

        return redisCacheManager;
    }

}
