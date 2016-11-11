package com.hmsgtech.service.studio;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hmsgtech.constants.StudioAppUserStatus;
import com.hmsgtech.coreapi.HealthStoreException;
import com.hmsgtech.coreapi.ReturnCode;
import com.hmsgtech.domain.store.Store;
import com.hmsgtech.domain.store.StoreImage;
import com.hmsgtech.domain.studio.StudioIdentity;
import com.hmsgtech.domain.studio.StudioUser;

@Service
public class StudioService extends StudioBaseService{

	public StudioUser setAuth(StudioUser studioUser) {
		// 权限
		if (studioUser.getIdentity() == null || studioUser.getIdentity() <= 0) {
			studioUser.setAuthority("NO");
		} else {
			StudioIdentity authority = studioIdentityRepository.findOne(studioUser.getIdentity());
			studioUser.setAuthority(authority.getAuthority());
		}
		// 机构名称
		if (studioUser.getAgencyId() != null) {
			Store store = storeRepository.findOne((long) studioUser.getAgencyId());
			studioUser.setAgencyName(store.getName());
		}
		// 好像邀请那个客户端可以自己写：标题 ，简介写死好了，也没什么可改的。。技师的头像，名称，机构名称
		return studioUser;
	}

	public List<StoreImage> initImageList(int storeid) {
		List<StoreImage> images = new ArrayList<StoreImage>(5);
		for (int i = 1; i <= 5; i++) {
			images.add(new StoreImage(storeid, i));
		}
		return images;
	}

	public StudioUser registerUserInfo(String unionid, Integer status, String name, String phone, int identity, Integer agencyId, String snapshot) throws Exception {
		StudioUser studioUser = studioUserRepository.findOne(unionid);
		if (StudioAppUserStatus.SET_INFO.getValue() == status) {
			// 设置资料：审核通过不可以，
			if (studioUser.getStatus() == StudioAppUserStatus.AUDIT_PASS.getValue()) {
				throw new HealthStoreException(ReturnCode.ALEARY_REGISTER_SUCESS);
			}
			// 可以则重置机构，删除添加的机构
			if (studioUser.getAgencyId() != null && studioUser.getAgencyId() > 0) {
				if (unionid.equals(storeRepository.findOne((long) studioUser.getAgencyId()).getManage())) {
					storeRepository.delete((long) studioUser.getAgencyId());
				}
			}
			if ((!phone.equals(studioUser.getPhone())) && studioUserRepository.findByPhone(phone) != null) {
				throw new HealthStoreException(ReturnCode.PHONE_ALREADY_EXIST);
			}
			studioUser = new StudioUser();
			studioUser.setUnionid(unionid);
			studioUser.setName(name);
			studioUser.setPhone(phone);
			studioUser.setIdentity(identity);
			studioUser.setSnapshot(snapshot);
		} else if (StudioAppUserStatus.SET_AGENCY.getValue() == status) {
			// 设置资料：审核通过不可以，
			if (studioUser.getStatus() != StudioAppUserStatus.SET_INFO.getValue()) {
				throw new HealthStoreException(ReturnCode.REGISTER_USERINFO_FIRST);
			}
			studioUser.setAgencyId(agencyId);
		}
		studioUser.setStatus(status);
		return studioUserRepository.save(studioUser);
	}

}
