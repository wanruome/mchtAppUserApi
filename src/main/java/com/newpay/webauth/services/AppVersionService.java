/**
 *	@copyright wanruome-2018
 * 	@author wanruome
 * 	@create 2018年6月24日 下午9:13:00
 */
package com.newpay.webauth.services;

import com.newpay.webauth.dal.request.appversion.AppVersionReqDto;

public interface AppVersionService {
	public Object doGetAppVersion(AppVersionReqDto appVersionReqDto);
}
