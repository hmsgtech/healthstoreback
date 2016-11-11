package com.hmsgtech.controller.studio;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hmsgtech.constants.CompanyEnum;
import com.hmsgtech.constants.StudioAppUserStatus;
import com.hmsgtech.constants.YesNoStatus;
import com.hmsgtech.coreapi.ApiResponse;
import com.hmsgtech.coreapi.HealthStoreException;
import com.hmsgtech.coreapi.ReturnCode;
import com.hmsgtech.domain.store.Store;
import com.hmsgtech.domain.store.StoreImage;
import com.hmsgtech.domain.studio.StudioUser;

/**
 * 工作室
 * 
 * @author lujq
 *
 */
@Controller
@RequestMapping("/studio")
public class StudioController extends StudioBaseController {
	private Log logger = LogFactory.getLog(StudioController.class);

	@RequestMapping("/getUserInfo")
	public @ResponseBody StudioUser getUserInfo(@RequestParam("unionid") String unionid) {
		StudioUser studioUser = studioUserRepository.findOne(unionid);
		if (studioUser == null) {
			studioUser = storeService.initStoreUser(unionid);
		} else {
			// 管理员
			if (studioUser.getAgencyId() != null && studioUser.getAgencyId() > 0) {
				if (unionid.equals(storeRepository.findOne((long) studioUser.getAgencyId()).getManage())) {
					studioUser.setIsAdmin(YesNoStatus.YES.getValue());
				}
			}
			// 登陆记录：仅记录审核通过的用户
			if (studioUser.getStatus() == StudioAppUserStatus.AUDIT_PASS.getValue()) {
				studioUserRepository.updateLastLandTime(unionid, new Date());
			}
		}
		studioService.setAuth(studioUser);
		return studioUser;
	}

	/**
	 * 注册：完善资料/选择机构
	 * 
	 * @return
	 * @throws Exception
	 * @throws IOException
	 */
	@RequestMapping(value = "/registerUserInfo", method = RequestMethod.POST)
	public @ResponseBody ApiResponse setUserInfo(@RequestParam("unionid") String unionid, @RequestParam(value = "status", required = false, defaultValue = "3") Integer status,
			@RequestParam(value = "name", required = false) String name, @RequestParam(value = "phone", required = false) String phone,
			@RequestParam(value = "identity", required = false, defaultValue = "0") int identity, @RequestParam(value = "agencyId", required = false) Integer agencyId,
			@RequestParam(value = "snapshot", required = false) String snapshot) throws Exception {
		ReturnCode code = ReturnCode.SUCCESS;
		try {
			StudioUser user = studioService.registerUserInfo(unionid, status, name, phone, identity, agencyId, snapshot);
			return ApiResponse.success(user);
		} catch (HealthStoreException e) {
			code = e.getErrorCode();
			e.printStackTrace();
		} catch (Exception e) {
			code = ReturnCode.FAIL;
			e.printStackTrace();
		}
		return ApiResponse.fail(code);
	}

	@RequestMapping("/updateUserInfo")
	public @ResponseBody StudioUser updateUserInfo(@RequestParam("unionid") String unionid, @RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "description", required = false) String description, @RequestParam(value = "snapshot", required = false) String snapshot,
			@RequestParam(value = "workingYears", required = false) Integer workingYears, @RequestParam(value = "specialty", required = false) String specialty) {
		StudioUser studioUser = studioUserRepository.findOne(unionid);
		if (!StringUtils.isEmpty(name)) {
			studioUser.setName(name);
		}
		if (!StringUtils.isEmpty(description)) {
			studioUser.setDescription(description);
		}
		if (!StringUtils.isEmpty(snapshot)) {
			studioUser.setSnapshot(snapshot);
		}
		if (workingYears != null && workingYears > 0) {
			studioUser.setWorkingYears(workingYears);
		}
		if (!StringUtils.isEmpty(specialty)) {
			studioUser.setSpecialty(specialty);
		}
		return studioUserRepository.save(studioUser);
	}

	@RequestMapping("/updateUserPhone")
	public @ResponseBody ApiResponse updateUserPhone(@RequestParam("unionid") String unionid, @RequestParam(value = "xcode") String xcode,
			@RequestParam(value = "phone") String phone) {
		ReturnCode code = ReturnCode.SUCCESS;
		try {
			StudioUser studioUser = studioUserRepository.findOne(unionid);
			commonService.validateXcode(phone, xcode, 5);
			if (studioUserRepository.findByPhone(phone) != null) {
				return ApiResponse.fail(ReturnCode.PHONE_ALREADY_EXIST);
			}
			studioUser.setPhone(phone);
			return ApiResponse.success(studioUserRepository.save(studioUser));
		} catch (HealthStoreException e) {
			code = e.getErrorCode();
			e.printStackTrace();
		} catch (Exception e) {
			code = ReturnCode.FAIL;
			e.printStackTrace();
		}
		return ApiResponse.fail(code);
	}

	@RequestMapping("/exitAgency")
	public @ResponseBody ApiResponse exitAgency(@RequestParam("unionid") String unionid) {
		StudioUser studioUser = studioUserRepository.findOne(unionid);
		if (studioUser.getAgencyId() != null && studioUser.getAgencyId() > 0) {
			if (unionid.equals(storeRepository.findOne((long) studioUser.getAgencyId()).getManage())) {
				return ApiResponse.fail(ReturnCode.ADMIN_EXIT);
			}
		} else {
			return ApiResponse.fail(ReturnCode.USER_NOT_EXIST);
		}
		// 重置用户
		storeService.initStoreUser(unionid);
		return ApiResponse.success();
	}

	@RequestMapping("/agencyMember")
	public @ResponseBody List<StudioUser> getAgencyMember(@RequestParam("unionid") String unionid,
			@RequestParam(value = "status", required = false, defaultValue = "3") Integer status) {
		StudioUser studioUser = studioUserRepository.findOne(unionid);
		Store store = storeRepository.findOne((long) studioUser.getAgencyId());
		List<StudioUser> studioUsers = studioUserRepository.findByAgencyIdAndStatusAndUnionidNot(studioUser.getAgencyId(), status, store.getManage());
		if (status == 3) {
			StudioUser admin = studioUserRepository.findOne(store.getManage());
			studioUsers.add(0, admin);
		}
		return studioUsers;
	}

	// 查询机构列表：by name,city,坐标,分页
	@RequestMapping("/agencies")
	public @ResponseBody List<Store> findAgencies(@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "city", required = false, defaultValue = "0") int city, @RequestParam(value = "longitude", required = false, defaultValue = "0") Float longitude,
			@RequestParam(value = "latitude", required = false, defaultValue = "0") Float latitude, @RequestParam(value = "page") int page,
			@RequestParam(value = "size", required = false) Integer size) {
		List<Store> stores = storeService.findAgencies(name, city, longitude, latitude, page, size);
		for (Store store : stores) {
			store.setAdmin(studioUserRepository.findOne(store.getManage()).getName());
		}
		return stores;
	}

	@RequestMapping("/agency")
	public @ResponseBody Store findAgency(@RequestParam("unionid") String unionid) {
		StudioUser studioUser = studioUserRepository.findOne(unionid);
		return storeRepository.findOne((long) studioUser.getAgencyId());
	}

	@RequestMapping("/agencyImages")
	public @ResponseBody List<StoreImage> findAgencyImages(@RequestParam("storeId") int storeid) {
		List<StoreImage> images = studioService.initImageList(storeid);
		List<StoreImage> images_save = storeImageRepository.findByStoreIdOrderByType(storeid);
		for (StoreImage storeImage : images_save) {
			if (storeImage.getType() == 1) {
				images.set(0, storeImage);
			} else if (storeImage.getType() == 2) {
				images.set(1, storeImage);
			} else if (storeImage.getType() == 3) {
				images.set(2, storeImage);
			} else if (storeImage.getType() == 4) {
				images.set(3, storeImage);
			} else if (storeImage.getType() == 5) {
				images.set(4, storeImage);
			}
		}
		return images;
	}

	// 添加一个机构：美容院
	@RequestMapping("/addAgency")
	@Transactional
	public @ResponseBody ApiResponse addAgency(@RequestParam("unionid") String unionid, @RequestParam("name") String name,
			@RequestParam(value = "address", required = false) String address, @RequestParam("cityId") int cityId, @RequestParam("telephone") String telephone,
			@RequestParam(value = "startTime", required = false) String startTime, @RequestParam(value = "endTime", required = false) String endTime,
			@RequestParam(value = "description", required = false, defaultValue = "还没有描述") String description, @RequestParam(value = "snapshot", required = false) String snapshot,
			@RequestParam("industry") String industry) {
		StudioUser studioUser = studioUserRepository.findOne(unionid);
		// 审核通过的名字不能重复
		if (storeRepository.findByNameAndCompanyIdAndStatus(name, CompanyEnum.MEIRONG.getValue(), YesNoStatus.YES.getValue()) != null) {
			return ApiResponse.fail(ReturnCode.AGENCY_NAME_REPEAT);
		}
		Store agency = new Store();
		if (studioUser.getAgencyId() != null && studioUser.getAgencyId() > 0) {
			agency = storeRepository.findOne((long) studioUser.getAgencyId());
		}
		agency.setName(name);
		agency.setAddress(address);
		agency.setCityId(cityId);
		agency.setAddress(address);
		agency.setDescription(description);
		agency.setPhonenumber(telephone);
		agency.setAvailableTime(startTime + "——" + endTime);
		agency.setStatus(0);
		agency.setManage(unionid);
		agency.setSnapshot(snapshot);
		agency.setCompanyId(CompanyEnum.MEIRONG.getValue());
		agency.setIndustry(industry);
		storeRepository.save(agency);

		studioUser.setAgencyId((int) agency.getId());
		studioUser.setStatus(StudioAppUserStatus.ADD_AGENCY.getValue());
		studioUserRepository.save(studioUser);
		return ApiResponse.success();
	}

	@RequestMapping("/systemMsg")
	public @ResponseBody Map<String, Object> findMyMsg(@RequestParam("unionid") String unionid) {
		Map<String, Object> map = new HashMap<>();
		Store store = storeRepository.findByManage(unionid);
		if (store != null) {
			map.put("applyCount", studioUserRepository.countByAgencyIdAndStatus((int) store.getId(), 2));
		}
		return map;
	}
}
