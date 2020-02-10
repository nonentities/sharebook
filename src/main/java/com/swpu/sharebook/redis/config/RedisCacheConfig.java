package com.swpu.sharebook.redis.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.*;

import java.time.Duration;

/**
 * @author KeennessNewBie
 * @Date 2020/1/24 20:38
 * @Message
 */
@Configuration
public class RedisCacheConfig {
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Bean("redisTemplate")
    public RedisTemplate<Object, Object> redisTemplate() {
        RedisTemplate<Object, Object> rt = new RedisTemplate<>();
        rt.setConnectionFactory(redisConnectionFactory);
        rt.setKeySerializer(jackson2JsonRedisSerializer());
        rt.setValueSerializer(jackson2JsonRedisSerializer());
        rt.setHashKeySerializer(jackson2JsonRedisSerializer());
        rt.setHashValueSerializer(jackson2JsonRedisSerializer());
        System.err.println("myRedisTemplate");
        return rt;
    }

    @Bean("redisCacheManager")
    public CacheManager redisCacheManager() {

        RedisCacheConfiguration conf = RedisCacheConfiguration.defaultCacheConfig();

        RedisCacheConfiguration okConf = conf
                .disableCachingNullValues()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(stringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(valueSerializer()))
                .entryTtl(Duration.ofMinutes(3));

        RedisCacheManager redisCacheManager = RedisCacheManager
                .builder(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory))
                .cacheDefaults(okConf)
                .build();
        return redisCacheManager;
    }

    private RedisSerializer<Object> valueSerializer() {
        return new GenericJackson2JsonRedisSerializer();
    }


    public Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer() {
        Jackson2JsonRedisSerializer<Object> jackson = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper objm = new ObjectMapper();

        /**
         * 这一句必须要，作用是序列化时将对象全类名一起保存下来
         * 设置之后的序列化结果如下：
         *  [
         *   "com.dxy.cache.pojo.Dept",
         *   {
         *     "pid": 1,
         *     "code": "11",
         *     "name": "财务部1"
         *   }
         * ]
         *
         * 不设置的话，序列化结果如下，将无法反序列化
         *
         *  {
         *     "pid": 1,
         *     "code": "11",
         *     "name": "财务部1"
         *   }
         *    //因为上面那句代码已经被标记成作废，因此用下面这个方法代替，仅仅测试了一下，不知道是否完全正确
         */
        objm.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objm.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);


        //解决jackson无法序列化LocalDateTime的问题
        objm.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objm.registerModule(new JavaTimeModule());


        jackson.setObjectMapper(objm);

        return jackson;

    }

    public StringRedisSerializer stringRedisSerializer() {
        StringRedisSerializer serializer = new StringRedisSerializer();
        return serializer;
    }

}
