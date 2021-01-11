package com.application.base.config;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @author ：孤狼
 * @date ：2020-12-28
 * @description: 是否启用redis
 * @modified By：
 * @version: 1.0.0
 */
public class RedisCondition implements Condition {
	
	/**
	 * 是否开启redis的使用
	 *
	 * @param context
	 * @param metadata
	 * @return
	 */
	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		String redisSwitch = context.getEnvironment().getProperty("spring.redis.open");
		return "true".equalsIgnoreCase(redisSwitch);
	}
}
