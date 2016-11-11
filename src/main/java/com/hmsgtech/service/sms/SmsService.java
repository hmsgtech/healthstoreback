package com.hmsgtech.service.sms;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hmsgtech.domain.studio.StudioUser;
import com.hmsgtech.domain.user.Contact;
import com.hmsgtech.repository.ContactRepository;
import com.hmsgtech.repository.RecipientRepository;
import com.hmsgtech.utils.StringsUtil;

@Service
public class SmsService {

	@Autowired
	private MenWangSMSService menService;

	@Autowired
	ContactRepository contactRepository;

	@Autowired
	RecipientRepository recipientRepository;

	@Value("${msg_activation_code_notify}")
	String msg_activation_notify;
	@Value("${msg_order_cancle}")
	String msg_order_cancle;
	@Value("${msg_agency_addfail}")
	String msg_agency_addfail;
	@Value("${msg_agency_addok}")
	String msg_agency_addok;

	@Value("${admin_update_member}")
	String admin_update_member;
	@Value("${admin_update_manage}")
	String admin_update_manage;

	public String sendXcode(String mobile, String xcode) {
		return menService.sendMessage(mobile, "您的验证码是:" + xcode);
	}

	public String sendUpdateMember(String mobile, String identity) {
		return menService.sendMessage(mobile, "管理员已将您的机构身份设置成" + identity + "，为了您更方便的使用，请您重新登录体验新的操作。");
	}

	public String sendUpdateManage(String mobile, String oldAdmin) {
		return menService.sendMessage(mobile, oldAdmin + "已成功将管理员权限转移给您，您的权限已发生变化，请您重新登录体验更多管理操作。");
	}

	/**
	 * 管理员审核同意
	 * 
	 * @param contactId
	 * @param code
	 * @return
	 */
	public String sendAddAgencySucess(StudioUser studioUser) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("{name}", studioUser.getName());
		return menService.sendMessage(studioUser.getPhone(), StringsUtil.replaceMap(map, msg_agency_addok));
	}

	public String sendAddAgencyFail(StudioUser studioUser, String reason) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("{name}", studioUser.getName());
		map.put("{reason}", reason);
		return menService.sendMessage(studioUser.getPhone(), StringsUtil.replaceMap(map, msg_agency_addfail));
	}

	/**
	 * 用户订单支付成功，发送激活码通知
	 * 
	 * @param mobileIds
	 * @param text
	 * @return
	 */
	public String sendActivationCodeMessage(long contactId, String code) {
		Contact contact = contactRepository.findOne(contactId);
		Map<String, String> map = new HashMap<String, String>();
		map.put("{nickname}", contact.getName());
		map.put("{code}", code);
		return menService.sendMessage(contact.getPhone(), StringsUtil.replaceMap(map, msg_activation_notify));
	}

	public String sendCancleOrderMessage(long contactId) {
		Contact contact = contactRepository.findOne(contactId);
		Map<String, String> map = new HashMap<String, String>();
		map.put("{nickname}", contact.getName());
		return menService.sendMessage(contact.getPhone(), StringsUtil.replaceMap(map, msg_order_cancle));
	}

}
