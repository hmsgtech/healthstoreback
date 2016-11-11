package com.hmsgtech.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.hmsgtech.coreapi.HealthStoreException;
import com.hmsgtech.coreapi.ReturnCode;
import com.hmsgtech.repository.redis.RedisConfig;
import com.hmsgtech.repository.studio.StudioUserRepository;
import com.hmsgtech.service.sms.SmsService;
import com.hmsgtech.utils.RandomUtil;

@Component
public class CommonService {
	@Autowired
	StringRedisTemplate stringTemplate;
	@Autowired
	SmsService smsService;
	@Autowired
	StudioUserRepository studioUserRepository;

	private Log logger = LogFactory.getLog(CommonService.class);

	final Map<String, Object> confings = new HashMap<String, Object>();

	@PostConstruct
	public void serverStart() {
		logger.info("Server start Success");
	}

	public void serverShutdown() {
	}

	public void sendXcode(String mobile, int validateCodeType) {
		validateXcodeSendtime(mobile, validateCodeType);
		String xcode = RandomUtil.genRandomNum(6);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("xcode", xcode);
		map.put("createtime", String.valueOf(new Date().getTime()));
		stringTemplate.opsForHash().putAll(RedisConfig.XCODE_KEY + "_" + validateCodeType + "_" + mobile, map);
		stringTemplate.expire(RedisConfig.XCODE_KEY + "_" + validateCodeType + "_" + mobile, 1, TimeUnit.HOURS);
		smsService.sendXcode(mobile, xcode);
	}

	public void validateXcode(String mobile, String xcode, int validateCodeType) {
		Object code = stringTemplate.opsForHash().get(RedisConfig.XCODE_KEY + "_" + validateCodeType + "_" + mobile, "xcode");
		if (code == null || !code.toString().equals(xcode)) {
			throw new HealthStoreException(ReturnCode.XCODE_ERROR);
		}
		if (stringTemplate.opsForHash().hasKey(RedisConfig.XCODE_KEY + "_" + validateCodeType + "_" + mobile, "xcode")) {
		}
	}

	private void validateXcodeSendtime(String mobile, int validateCodeType) {
		Object createtime = stringTemplate.opsForHash().get(RedisConfig.XCODE_KEY + "_" + validateCodeType + "_" + mobile, "createtime");
		if (createtime != null) {
			long mills = Long.parseLong(createtime.toString());
			if (new Date().getTime() - mills < 60000) {
				throw new HealthStoreException(ReturnCode.SEND_XCODE_TIME_ERROR);
			}
		}
	}
}
