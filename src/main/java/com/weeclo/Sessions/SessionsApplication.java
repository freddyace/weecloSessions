package com.weeclo.Sessions;

import com.weeclo.Sessions.session.WeeCloSession;
import entities.UserEntity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootApplication
public class SessionsApplication {


	@Bean
	JedisConnectionFactory jedisConnectionFactory() {
		return new JedisConnectionFactory();
	}

	@Bean
	RedisTemplate<String, UserEntity> redisTemplate() {
		RedisTemplate<String, UserEntity> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(jedisConnectionFactory());
		return redisTemplate;
	}
	@Bean
	RedisTemplate<String, WeeCloSession> redisSessionTemplate() {
		RedisTemplate<String, WeeCloSession> redisSessionTemplate = new RedisTemplate<>();
		redisSessionTemplate.setConnectionFactory(jedisConnectionFactory());
		return redisSessionTemplate;
	}

	public static void main(String[] args) {
		SpringApplication.run(SessionsApplication.class, args);
	}
}
