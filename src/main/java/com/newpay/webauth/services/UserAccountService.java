/**
 *	@copyright wanruome-2018
 * 	@author wanruome
 * 	@create 2018年6月10日 下午3:10:53
 */
package com.newpay.webauth.services;

import com.newpay.webauth.dal.request.useraccount.UserInfoFindPwd;
import com.newpay.webauth.dal.request.useraccount.UserInfoLoginReqDto;
import com.newpay.webauth.dal.request.useraccount.UserInfoLogout;
import com.newpay.webauth.dal.request.useraccount.UserInfoModifyEmail;
import com.newpay.webauth.dal.request.useraccount.UserInfoModifyMobie;
import com.newpay.webauth.dal.request.useraccount.UserInfoModifyName;
import com.newpay.webauth.dal.request.useraccount.UserInfoModifyPwd;
import com.newpay.webauth.dal.request.useraccount.UserInfoRegisterReqDto;

public interface UserAccountService {
	public Object doLogin(UserInfoLoginReqDto userInfoLoginReqDto);

	public Object doRegister(UserInfoRegisterReqDto loginUserReqDto);

	public Object doModifyPwd(UserInfoModifyPwd userInfoModifyPwd);

	public Object doFindPwd(UserInfoFindPwd userInfoFindPwd);

	public Object doModifyMobile(UserInfoModifyMobie userInfoModifyMobie);

	public Object doModifyEmail(UserInfoModifyEmail userInfoModifyEmail);

	public Object doModifyName(UserInfoModifyName userInfoModifyName);

	public Object doLogout(UserInfoLogout userInfoLogout);
}
