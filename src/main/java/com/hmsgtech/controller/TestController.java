package com.hmsgtech.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hmsgtech.constants.YesNoStatus;
import com.hmsgtech.coreapi.ApiResponse;
import com.hmsgtech.coreapi.ReturnCode;
import com.hmsgtech.domain.store.Store;
import com.hmsgtech.domain.studio.StudioUser;
import com.hmsgtech.repository.StoreRepository;
import com.hmsgtech.repository.studio.StudioUserRepository;

/**
 * 工作室
 * 
 * @author lujq
 *
 */
@Controller
@RequestMapping("/test")
public class TestController {
	@Autowired
	StudioUserRepository studioUserRepository;
	// @Autowired
	// StoreAgencyRepository storeAgencyRepository;
	@Autowired
	StoreRepository storeRepository;

	@RequestMapping("/delStudioUser")
	public @ResponseBody ApiResponse delStudioUser(@RequestParam("unionid") String unionid) {
		studioUserRepository.delete(unionid);
		return ApiResponse.success();
	}

	// 添加一个机构：美容院
	@RequestMapping("/delUserAndAgency")
	public @ResponseBody ApiResponse delUserAndAgency(@RequestParam("unionid") String unionid) {
		StudioUser studioUser = studioUserRepository.findOne(unionid);
		Store agency = new Store();
		if (studioUser.getAgencyId() != null && studioUser.getAgencyId() > 0) {
			agency = storeRepository.findOne((long) studioUser.getAgencyId());
			storeRepository.delete(agency);
		}
		studioUserRepository.delete(unionid);
		return ApiResponse.success();
	}
}
