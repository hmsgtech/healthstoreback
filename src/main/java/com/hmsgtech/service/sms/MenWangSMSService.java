package com.hmsgtech.service.sms;

import java.nio.charset.Charset;
import java.util.List;

import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.hmsgtech.utils.StringsUtil;
import com.hmsgtech.utils.XmlUtils;

@Component
public class MenWangSMSService implements SMSServiceProvider {

	private Logger logger = LoggerFactory.getLogger(MenWangSMSService.class);

	/**
	 * mobies=xxx,xxx,xxx
	 * 
	 * @param mobileIds
	 * @param text
	 * @return
	 */
	public String sendMessage(String mobileIds, String text) {
		return sendMessage(mobileIds, text, sms_username, sms_password, sms_url);
	}

	// 营销短信发送
	public String sendMessageY(String mobileIds, String text) {
		return sendMessage(mobileIds, text, sms_username_y, sms_password_y, sms_url_y);
	}

	/**
	 * 发送短信 手机号用逗号隔开 test.sendMessage(mobies, "", "ja0281", "221578",
	 * "http://211.100.34.185:8016/MWGate/wmgw.asmx");
	 */
	public String sendMessage(String mobileIds, String text, String userName, String password, String url) {
		if (StringsUtil.isEmpty(mobileIds)) {
			return "手机号码为空";
		}
		int iMobiCount = StringsUtil.countMatches(mobileIds, ",") + 1;
		try {
			String result = Request.Post(url + "/MongateCsSpSendSmsNew").bodyForm(Form.form().add("userId", userName).add("password", password).add("pszMobis", mobileIds)
					.add("pszMsg", text).add("iMobiCount", iMobiCount + "").add("pszSubPort", "*").build(), Charset.forName("UTF-8")).execute().returnContent().asString();
			logger.debug("梦网返回, mobileid: " + mobileIds + ", result: " + result);
			return StringsUtil.substringBetween(result, "<string xmlns=\"http://tempuri.org/\">", "</string>");
		} catch (Exception e) {
			logger.error("梦网科技短信发送发生异常", e);
			return "发送报错异常";
		}
	}

	/**
	 * 发送短信 手机号数组
	 */
	public String sendMessage(String[] mobileIds, String text, String userName, String password, String url) {
		String pszMobis = StringsUtil.join(mobileIds, ",");
		if (StringsUtil.isEmpty(pszMobis)) {
			throw new IllegalArgumentException("手机号码为空");
		}
		return this.sendMessage(pszMobis, text, userName, password, url);
	}

	public String[] receiveMessage(String userName, String password, String url) {
		try {
			String result = Request.Post(url + "/MongateCsGetSmsExEx").bodyForm(Form.form().add("userId", userName).add("password", password).build(), Charset.forName("UTF-8"))
					.execute().returnContent().asString();
			logger.debug("梦网返回: " + result);
			List<String> list = XmlUtils.getTextForElements(result, "string");
			return list.toArray(new String[list.size()]);
		} catch (Exception e) {
			logger.error("梦网科技短信上行信息异常", e);
			throw new RuntimeException("短信上行信息异常", e);
		}
	}

	public String[] queryStatusReport(String userName, String password, String url) {
		try {
			String result = Request.Post(url + "/MongateCsGetStatusReportExEx")
					.bodyForm(Form.form().add("userId", userName).add("password", password).build(), Charset.forName("UTF-8")).execute().returnContent().asString();
			logger.debug("梦网返回: " + result);
			List<String> list = XmlUtils.getTextForElements(result, "string");
			return list.toArray(new String[list.size()]);
		} catch (Exception e) {
			logger.error("梦网科技短信状态报告异常", e);
			throw new RuntimeException("短信状态报告异常", e);
		}
	}

	public Integer queryBalance(String userName, String password, String url) {
		try {
			String result = Request.Post(url + "/MongateQueryBalance").bodyForm(Form.form().add("userId", userName).add("password", password).build(), Charset.forName("UTF-8"))
					.execute().returnContent().asString();
			logger.debug("梦网返回: " + result);
			return Integer.parseInt(StringsUtil.substringBetween(result, "<int xmlns=\"http://tempuri.org/\">", "</int>"));
		} catch (Exception e) {
			logger.error("梦网科技短信查询余额异常", e);
			throw new RuntimeException("短信查询余额异常", e);
		}
	}
}
