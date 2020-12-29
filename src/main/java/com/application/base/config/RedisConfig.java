package com.application.base.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author : 孤狼
 * @NAME: RedisConfig
 * @DESC: RedisConfig类设计
 **/
@Configuration

public class RedisConfig {
	
	@Autowired(required=false)
	private LettuceConnectionFactory lettuceConnectionFactory;
	
	@Primary
	@Bean("redisTemplate")
	@Conditional(RedisCondition.class)
	@ConditionalOnProperty(name = "spring.redis.host", matchIfMissing = true)
	public RedisTemplate<String, Object> getSingleRedisTemplate( ) {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
		RedisSerializer redisObjectSerializer = new RedisObjectSerializer();
		redisTemplate.setConnectionFactory(lettuceConnectionFactory);
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(redisObjectSerializer);
		redisTemplate.setHashValueSerializer(redisObjectSerializer);
		redisTemplate.afterPropertiesSet();
		return redisTemplate;
	}
	
}
