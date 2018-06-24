/**
 *	@copyright wanruome-2018
 * 	@author wanruome
 * 	@create 2018年6月24日 下午9:13:23
 */
package com.newpay.webauth.services.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newpay.webauth.config.util.VersionUtil;
import com.newpay.webauth.dal.mapper.AppVersionMapper;
import com.newpay.webauth.dal.model.AppVersion;
import com.newpay.webauth.dal.request.appversion.AppVersionReqDto;
import com.newpay.webauth.dal.response.ResultFactory;
import com.newpay.webauth.services.AppVersionService;

@Service
public class AppVersionServiceImpl implements AppVersionService {
	@Autowired
	AppVersionMapper appVersionMapper;

	@Override
	public Object doGetAppVersion(AppVersionReqDto appVersionReqDto) {
		// TODO Auto-generated method stub
		AppVersion queryVersion = new AppVersion();
		queryVersion.setAppName(appVersionReqDto.getAppName());
		queryVersion.setAppType(appVersionReqDto.getAppType());
		AppVersion resultVersion = appVersionMapper.selectByPrimaryKey(queryVersion);
		if (null == resultVersion) {
			return ResultFactory.toNackDB();
		}
		if (null != resultVersion && resultVersion.getUpdateStatus() == 0) {
			return ResultFactory.toNackDB();
		}
		long requestVersion = 0;
		long serverVersion = 0;
		try {
			requestVersion = VersionUtil.getVersion(appVersionReqDto.getAppVersion());

		}
		catch (Exception e) {
			e.printStackTrace();
			requestVersion = -1;
		}
		try {
			serverVersion = VersionUtil.getVersion(resultVersion.getAppVersion());
		}
		catch (Exception e) {
			e.printStackTrace();
			serverVersion = -1;
		}
		Integer isNeedUpdate = null;
		if (requestVersion < 0 || serverVersion < 0) {
			isNeedUpdate = null;
		}
		else if (serverVersion > requestVersion) {
			isNeedUpdate = 1;
		}
		else {
			isNeedUpdate = 0;
		}
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("appVersion", resultVersion.getAppVersion());
		resultMap.put("downUrl", resultVersion.getDownUrl());
		if (null != isNeedUpdate) {
			resultMap.put("needUpdate", isNeedUpdate + "");
		}
		return ResultFactory.toAck(resultMap);
	}

}
