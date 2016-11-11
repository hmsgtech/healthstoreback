package com.hmsgtech.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hmsgtech.domain.order.BuyOrder;
import com.hmsgtech.domain.user.Contact;
import com.hmsgtech.domain.user.Recipient;
import com.hmsgtech.domain.user.Reserve;
import com.hmsgtech.repository.BuyOrderRepository;
import com.hmsgtech.repository.ContactRepository;
import com.hmsgtech.repository.RecipientRepository;
import com.hmsgtech.repository.ReserveRepository;

/**
 * 联系人
 * 
 * @author lujq
 *
 */
@Controller
public class UserContactController {
	@Autowired
	BuyOrderRepository buyOrderRepository;

	@Autowired
	ContactRepository contactRepository;

	@Autowired
	ReserveRepository reserveRepository;

	@Autowired
	RecipientRepository recipientRepository;

	/*
	 * 选择联系人列表
	 */
	@RequestMapping("listContacts")
	public @ResponseBody List<Contact> listContacts(@RequestParam("userid") String userid) {
		return (List<Contact>) contactRepository.findContactListByUserid(userid);
	}

	// 体检人列表
	@RequestMapping("reserves")
	public @ResponseBody List<Reserve> listReserve(@RequestParam("userid") String userid) {
		return reserveRepository.findByUserid(userid);
	}

	@RequestMapping(value = "addReserve", method = RequestMethod.POST)
	public @ResponseBody Reserve addReserve(@RequestParam("userid") String userid, @RequestParam("name") String name, @RequestParam("phone") String phone,
			@RequestParam(value = "cardNumber") String cardNumber, @RequestParam(value = "sex") int sex, @RequestParam(value = "marry") int marry) {
		Reserve contact = new Reserve();
		contact.setUserid(userid);
		contact.setName(name);
		contact.setPhone(phone);
		contact.setCardNumber(cardNumber);
		contact.setMarry(marry);
		contact.setSex(sex);
		return reserveRepository.save(contact);
	}

	@RequestMapping("getReserve")
	public @ResponseBody Reserve getReserve(@RequestParam(value = "orderId", required = false) long orderId,
			@RequestParam(value = "reserveid", required = false, defaultValue = "0") int reserveid) {
		if (orderId > 0) {
			BuyOrder order = buyOrderRepository.findOne(orderId);
			return reserveRepository.findOne((int) order.getReserveId());
		}
		return reserveRepository.findOne(reserveid);
	}

	/**
	 * 新增联系人
	 * 
	 * @param userid
	 * @param name
	 * @param phone
	 * @param cardNumber
	 * @param description
	 * @return
	 */
	@RequestMapping(value = "createContact", method = RequestMethod.POST)
	public @ResponseBody Contact createContact(@RequestParam("userid") String userid, @RequestParam("name") String name, @RequestParam("phone") String phone,
			@RequestParam(value = "cardNumber", required = false) String cardNumber, @RequestParam(value = "description", required = false) String description) {
		Contact contact = new Contact();
		contact.setUserid(userid);
		contact.setName(name);
		contact.setPhone(phone);
		contact.setCardNumber(cardNumber);
		contact.setDescription(description);
		return contactRepository.save(contact);
	}

	@RequestMapping("getContact")
	public @ResponseBody Contact getContact(@RequestParam(value = "orderId", required = false) long orderId) {
		BuyOrder order = buyOrderRepository.findOne(orderId);
		return contactRepository.findOne(order.getContactId());
	}

	/*
	 * 收件人列表
	 */
	@RequestMapping("listRecipients")
	public @ResponseBody List<Recipient> listRecipients(@RequestParam("userid") String userid) {
		return (List<Recipient>) recipientRepository.findByUserid(userid);
	}

	/**
	 * 修改/新增收件人
	 * 
	 * @param id
	 * @param userid
	 * @param name
	 * @param phone
	 * @param postcode
	 * @param address
	 * @param description
	 * @return
	 */
	@RequestMapping(value = "editRecipient", method = RequestMethod.POST)
	public @ResponseBody Recipient updateRecipient(@RequestParam(value = "id", required = false, defaultValue = "0") Long id, @RequestParam("userid") String userid,
			@RequestParam("name") String name, @RequestParam("phone") String phone, @RequestParam(value = "postcode", required = false, defaultValue = "") String postcode,
			@RequestParam("address") String address, @RequestParam(value = "description", required = false, defaultValue = "") String description) {
		Recipient recipient;
		if (id != null && id > 0) {
			recipient = recipientRepository.findOne(id);
		} else {
			recipient = new Recipient();
		}
		recipient.setUserid(userid);
		recipient.setName(name);
		recipient.setPhone(phone.trim());
		recipient.setAddress(address);
		recipient.setPostcode(postcode);
		recipient.setDescription(description);
		return recipientRepository.save(recipient);
	}

	@RequestMapping("getRecipient")
	public @ResponseBody Recipient getRecipient(@RequestParam(value = "orderId", required = false) long orderId) {
		BuyOrder order = buyOrderRepository.findOne(orderId);
		return recipientRepository.findOne(order.getRecipientId());
	}

	/**
	 * 删除收件人
	 * 
	 * @param id
	 */
	@RequestMapping(value = "deleteRecipient", method = RequestMethod.POST)
	public @ResponseBody String deleteRecipient(@RequestParam("id") Long id) {
		recipientRepository.delete(id);
		return "{\"status\":\"success\"}";
	}

}
