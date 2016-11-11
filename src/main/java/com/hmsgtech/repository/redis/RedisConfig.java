package com.hmsgtech.repository.redis;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@Component
public class RedisConfig {
	@Autowired
	StringRedisTemplate stringTemplate;
	@Autowired
	private JedisConnectionFactory jedisConnection;

	public static final String XCODE_KEY = "studio.xcode.key";

	public static final String QRCODE_KEY = "studio.ticket.key";

	// @PostConstruct
	public void test() {
		System.out.println("stringTemplate测试" + stringTemplate.opsForValue().get("WDCACHE_WECHATID_1719"));
		// StringRedisTemplate template = redisTemplate;
		// 操作HASH表中的值:
		HashOperations<String, Object, Object> hash = stringTemplate.opsForHash();
		System.out.println(hash.get("xiaomianao.doctor.pushchannel", "D000009202"));
		hash.put("mytest_HashOperations", "test1", "不知道什么dongxi");

		// 取VALUE表中的值:
		ValueOperations<String, String> operations = stringTemplate.opsForValue();
		operations.set("mytest_ValueOperations", "test2", 60, TimeUnit.SECONDS);
		System.out.println(operations.get("WDCACHE_WECHATID_1719"));

		// 设置有效期
		stringTemplate.expire("mytest_HashOperations", 60, TimeUnit.SECONDS);
		// 操作LIST队列中的值:
		ListOperations<String, String> listOperations = stringTemplate.opsForList();
		// 操作SET集合中的值:
		SetOperations<String, String> setOperations = stringTemplate.opsForSet();
	}

	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
		RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
		template.setConnectionFactory(jedisConnection);
		setSerializer(template);
		template.setKeySerializer(new StringRedisSerializer());
		return template;
	}

	private void setSerializer(RedisTemplate template) {
		Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
		ObjectMapper om = new ObjectMapper();
		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		jackson2JsonRedisSerializer.setObjectMapper(om);
		template.setValueSerializer(jackson2JsonRedisSerializer);
	}
}