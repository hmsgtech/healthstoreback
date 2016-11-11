package com.hmsgtech.controller.studio;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hmsgtech.constants.StudioAppUserStatus;
import com.hmsgtech.constants.YesNoStatus;
import com.hmsgtech.coreapi.ApiResponse;
import com.hmsgtech.coreapi.ReturnCode;
import com.hmsgtech.domain.store.Store;
import com.hmsgtech.domain.store.StoreImage;
import com.hmsgtech.domain.studio.StudioApplyRecord;
import com.hmsgtech.domain.studio.StudioUser;
import com.hmsgtech.utils.StringsUtil;

/**
 * 管理员工作
 * 
 * @author lujq
 *
 */
@Controller
@RequestMapping("/studioAdmin")
public class StudioAdminController extends StudioBaseController {

	// 设置机构简介,设置管理员
	@RequestMapping(value = "/updateAgency", method = RequestMethod.POST)
	public @ResponseBody ApiResponse updateAgency(@RequestParam("unionid") String unionid, @RequestParam(value = "description", required = false) String description,
			@RequestParam(value = "manage", required = false) String manage) {
		Store store = storeRepository.findByManage(unionid);
		if (store == null) {
			return ApiResponse.fail(ReturnCode.AGENCY_ADMIN_FAIL);
		}
		if (!StringsUtil.isEmpty(description)) {
			store.setDescription(description);
			storeRepository.save(store);
		}
		if (!StringsUtil.isEmpty(manage)) {
			if (studioUserRepository.findOne(manage).getAgencyId() != store.getId()) {
				return ApiResponse.fail(ReturnCode.AGENCY_NOT_COMMON);
			}
			store.setManage(manage);
			smsService.sendUpdateManage(studioUserRepository.findOne(manage).getPhone(), studioUserRepository.findOne(unionid).getName());
			storeRepository.save(store);
			return ApiResponse.build(ReturnCode.TOKEN_RESET,unionid);
		}
		return ApiResponse.success();
	}

	// 上传机构图片：id
	@RequestMapping(value = "/setStoreImage", method = RequestMethod.POST)
	public @ResponseBody ApiResponse setStoreImage(@RequestParam("unionid") String unionid, @RequestParam(value = "type", required = false, defaultValue = "9") Integer type,
			@RequestParam(value = "img", required = false) String img, @RequestParam(value = "storeId", required = false) int storeId) {
		Store store = storeRepository.findByManage(unionid);
		if (store == null) {
			return ApiResponse.fail(ReturnCode.AGENCY_ADMIN_FAIL);
		}
		if (store.getId() != storeId) {
			return ApiResponse.fail(ReturnCode.AGENCY_NOT_COMMON);
		}
		StoreImage storeImage = storeImageRepository.findByStoreIdAndType(storeId, type);
		if (storeImage == null) {
			storeImage = new StoreImage();
			storeImage.setType(type);
			storeImage.setStoreId(storeId);
		}
		storeImage.setImg(img);
		storeImageRepository.save(storeImage);
		return ApiResponse.success(storeImage);
	}

	// 删除机构图片：id
	@RequestMapping(value = "/delStoreImage", method = RequestMethod.POST)
	public @ResponseBody ApiResponse delStoreImage(@RequestParam("unionid") String unionid, @RequestParam("id") int id) {
		Store store = storeRepository.findByManage(unionid);
		if (store == null) {
			return ApiResponse.fail(ReturnCode.AGENCY_ADMIN_FAIL);
		}
		// 删除
		storeImageRepository.delete(id);
		return ApiResponse.success();
	}

	/**
	 * 修改成员身份，审核成员：拒绝/同意，删除成员
	 * 
	 * @param unionid
	 * @param status
	 * @param member
	 * @param reason
	 * @param identity
	 * @return
	 * @throws Exception
	 */
	@Transactional
	@RequestMapping(value = "/updateMember", method = RequestMethod.POST)
	public @ResponseBody ApiResponse setUserInfo(@RequestParam("unionid") String unionid, @RequestParam(value = "status", required = false, defaultValue = "0") Integer status,
			@RequestParam(value = "member", required = false) String member, @RequestParam(value = "reason", required = false) String reason,
			@RequestParam(value = "identity", required = false, defaultValue = "0") int identity) throws Exception {
		Store store = storeRepository.findByManage(unionid);
		if (store == null) {
			return ApiResponse.fail(ReturnCode.AGENCY_ADMIN_FAIL);
		}
		StudioUser studioUser = studioUserRepository.findOne(member);
		if (status > 0) {
			// 审核
			if (studioUser.getStatus() != StudioAppUserStatus.SET_AGENCY.getValue()) {
				return ApiResponse.fail(ReturnCode.AGENCY_AUDIT_FAIL);
			}
			StudioApplyRecord applyRecord = new StudioApplyRecord();
			applyRecord.setName(studioUser.getName());
			applyRecord.setSnapshot(studioUser.getSnapshot());
			applyRecord.setPhone(studioUser.getPhone());
			applyRecord.setUnionid(unionid);
			if (StudioAppUserStatus.AUDIT_PASS.getValue() == status) {
				studioUser.setStatus(status);
				applyRecord.setStatus(YesNoStatus.YES.getValue());
				smsService.sendAddAgencySucess(studioUser);
			} else if (StudioAppUserStatus.AUDIT_SET_AGENCY_FAIL.getValue() == status) {
				studioUser.setStatus(status);
				studioUser.setReason(reason);
				smsService.sendAddAgencyFail(studioUser, reason);
			}
			studioApplyRecordRepository.save(applyRecord);
		} else if (status == -1) {
			// 删除机构用户
			storeService.initStoreUser(member);
			return ApiResponse.success();
		} else if (identity > 0) {
			// 修改身份
			studioUser.setIdentity(identity);
			smsService.sendUpdateMember(studioUser.getPhone(), studioIdentityRepository.findOne(identity).getName());
			return ApiResponse.build(ReturnCode.TOKEN_RESET,studioUser.getUnionid());
		} else {
			return ApiResponse.fail(ReturnCode.REQUEST_ERROR);
		}
		studioUserRepository.save(studioUser);
		return ApiResponse.success();
	}

	@RequestMapping("/historyApply")
	public @ResponseBody List<StudioApplyRecord> getAgencyMember(@RequestParam("unionid") String unionid) {
		return studioApplyRecordRepository.findByUnionid(unionid);
	}
}
