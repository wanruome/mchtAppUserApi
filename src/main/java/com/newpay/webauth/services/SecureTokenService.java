/**
 *	@copyright wanruome-2018
 * 	@author wanruome
 * 	@create 2018年6月19日 下午10:52:24
 */
package com.newpay.webauth.services;

import com.alibaba.fastjson.JSONObject;
import com.newpay.webauth.dal.core.LoginUuidParse;
import com.newpay.webauth.dal.core.TokenResponseParse;
import com.newpay.webauth.dal.model.LoginAppInfo;
import com.newpay.webauth.dal.model.LoginUserAccount;
import com.newpay.webauth.dal.model.LoginUserToken;
import com.newpay.webauth.dal.request.useraccount.UserInfoLoginReqDto;
import com.newpay.webauth.dal.request.useraccount.UserInfoLogout;

public interface SecureTokenService {
	public TokenResponseParse createTokenForLogin(UserInfoLoginReqDto userInfoLoginReqDto,
			LoginUserAccount resultLoginUserAccount, LoginAppInfo resultLoginAppInfo, LoginUserToken resultUUIDToken);

	public JSONObject disableTokenForLogout(UserInfoLogout userInfoLogout);

	public LoginUserToken getTokenByUuidAndUserId(String appId, String userId, String uuid);

	public LoginUuidParse parseUuidLimit(UserInfoLoginReqDto userInfoLoginReqDto,
			LoginUserAccount resultLoginUserAccount, LoginAppInfo resultLoginAppInfo, LoginUserToken resultUUIDToken);
}
