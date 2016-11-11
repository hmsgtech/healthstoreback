package com.hmsgtech.controller;

import java.io.IOException;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hmsgtech.coreapi.ApiResponse;
import com.hmsgtech.coreapi.HmsgService;
import com.hmsgtech.coreapi.ReturnCode;
import com.hmsgtech.domain.user.ShareGoodsRecord;
import com.hmsgtech.domain.user.UserStatus;
import com.hmsgtech.repository.ShareGoodsRecordRepository;
import com.hmsgtech.repository.UserStatusRepository;
import com.hmsgtech.utils.RandomUtil;
import com.hmsgtech.utils.StringsUtil;

/**
 * 抽奖行为
 * 
 * @author lujq
 *
 */
@Controller
public class LuckDrawController {
	private Log logger = LogFactory.getLog(LuckDrawController.class);
	@Autowired
	UserStatusRepository userStatusRepository;
	@Autowired
	ShareGoodsRecordRepository shareGoodsRecordRepository;

	@Autowired
	HmsgService hmsgService;

	/**
	 * 分享状态
	 * 
	 * @param userid
	 * @param goodsId
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("shareStatus/{goodsId}")
	public @ResponseBody ApiResponse getShareStatus(@RequestParam("token") String token, @RequestParam("userid") String userid, @PathVariable("goodsId") long goodsId)
			throws IOException {
		logger.debug("getShareStatus:token=" + token + ",userid=" + userid);
		try {
			if (StringsUtil.isEmpty(token, userid)) {
				return ApiResponse.fail(ReturnCode.NOT_LOGIN);
			}
			hmsgService.validateUserToken(userid, token);
		} catch (Exception e) {
			return ApiResponse.fail(ReturnCode.TOKEN_ERROR);
		}
		if (shareGoodsRecordRepository.findByUseridAndGoodsIdAndShareDate(userid, goodsId, new Date()).isEmpty()) {
			return ApiResponse.success();
		} else {
			return ApiResponse.fail(ReturnCode.ALREADY_SHARE);
		}
	}

	/**
	 * 抽奖
	 * 
	 * @param token
	 * @param userid
	 * @param orderid
	 * @param status
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("luckDraw/shareGoods")
	public @ResponseBody ApiResponse updateShareStatus(@RequestParam("token") String token, @RequestParam("userid") String userid, @RequestParam("goodsId") long goodsId)
			throws IOException {
		ApiResponse apiResponse = ApiResponse.success();
		if (!shareGoodsRecordRepository.findByUseridAndGoodsIdAndShareDate(userid, goodsId, new Date()).isEmpty()) {
			return ApiResponse.fail(ReturnCode.ALREADY_SHARE);
		}
		// 验证token
		hmsgService.validateUserToken(userid, token);
		UserStatus userStatus = userStatusRepository.findOne(userid);
		if (userStatus == null) {
			userStatus = new UserStatus();
			userStatus.setLuckDraw(0);
			userStatus.setUserid(userid);
		}
		if (userStatus.getLuckDraw() % 21 == 0) {
			// 送优惠券
			int couponAmount = RandomUtil.luckDraw(500, 1000);
			apiResponse.setMessage(couponAmount / 100 + "元" + "商城优惠券");
			// 调用小棉袄生成伊币或优惠券
			hmsgService.addCoupon(token, userid, couponAmount);
		} else {
			// 送伊币
			int eglod = RandomUtil.luckDraw(20, 100);
			apiResponse.setMessage(eglod + "个伊币");
			hmsgService.addEgold(token, userid, eglod, goodsId);
			// 调用小棉袄生成伊币或优惠券
		}
		// 保存抽奖记录
		ShareGoodsRecord goodsRecord = new ShareGoodsRecord();
		goodsRecord.setGoodsId(goodsId);
		goodsRecord.setUserid(userid);
		goodsRecord.setShareDate(new Date());
		shareGoodsRecordRepository.save(goodsRecord);

		userStatus.setLuckDraw(userStatus.getLuckDraw() + 1);
		userStatusRepository.save(userStatus);
		// 返回一个提示
		return apiResponse;
	}
}
