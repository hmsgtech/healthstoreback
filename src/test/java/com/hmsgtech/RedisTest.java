package com.hmsgtech;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = HealthstorebackApplication.class)
@WebAppConfiguration
public class RedisTest {
	@Autowired
	StringRedisTemplate stringTemplate;

	@Before
	public void init() throws IOException {
	}

	@Test
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

}
