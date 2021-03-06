/**
 *	@copyright wanruome-2018
 * 	@author wanruome
 * 	@create 2018年6月19日 下午10:54:26
 */
package com.newpay.webauth.services.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.newpay.webauth.config.AppConfig;
import com.newpay.webauth.dal.core.LocationParse;
import com.newpay.webauth.dal.core.LoginUuidParse;
import com.newpay.webauth.dal.core.TokenResponseParse;
import com.newpay.webauth.dal.mapper.LoginUserTokenMapper;
import com.newpay.webauth.dal.model.LoginAppInfo;
import com.newpay.webauth.dal.model.LoginTermInfo;
import com.newpay.webauth.dal.model.LoginUserAccount;
import com.newpay.webauth.dal.model.LoginUserToken;
import com.newpay.webauth.dal.request.useraccount.UserInfoLoginReqDto;
import com.newpay.webauth.dal.request.useraccount.UserInfoLogout;
import com.newpay.webauth.dal.response.ResultFactory;
import com.newpay.webauth.services.DbSeqService;
import com.newpay.webauth.services.SecureTokenService;
import com.ruomm.base.http.ResponseData;
import com.ruomm.base.http.okhttp.DataOKHttp;
import com.ruomm.base.http.okhttp.OkHttpConfig;
import com.ruomm.base.tools.ListUtils;
import com.ruomm.base.tools.RegexUtil;
import com.ruomm.base.tools.StringUtils;
import com.ruomm.base.tools.TimeUtils;
import com.ruomm.base.tools.TokenUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SecureTokenServiceImpl implements SecureTokenService {
	@Autowired
	DbSeqService dbSeqService;
	@Autowired
	LoginUserTokenMapper loginUserTokenMapper;

	// public TokenResponseParse createTokenForLogin(UserInfoLoginReqDto userInfoLoginReqDto,
	// LoginUserAccount resultLoginUserAccount, LoginAppInfo resultLoginAppInfo) {
	// // TODO Auto-generated method stub
	// String appId = resultLoginAppInfo.getAppId();
	// String userId = resultLoginUserAccount.getLoginId();
	// String termType = userInfoLoginReqDto.getTermType();
	// String uuidTemp = userInfoLoginReqDto.getUuid();
	// String realUUID = appId + "_" + userId + "_" + uuidTemp;
	// TokenResponseParse tokenResponseParse = new TokenResponseParse();
	// tokenResponseParse.setValid(false);
	// tokenResponseParse.setNeedVerifyCode(false);
	// int termTypeLimit = 0;
	// if (termType.equals(AppConfig.TERM_TYPE_ANDROID)) {
	// if (resultLoginAppInfo.getTermAndroidLimit() <= 0) {
	// tokenResponseParse.setReturnResp(ResultFactory.toNackCORE("该设备无权登录"));
	// return tokenResponseParse;
	// }
	// termTypeLimit = resultLoginAppInfo.getTermAndroidLimit();
	// }
	// else if (termType.equals(AppConfig.TERM_TYPE_IPHONE)) {
	// if (resultLoginAppInfo.getTermIphoneLimit() <= 0) {
	// tokenResponseParse.setReturnResp(ResultFactory.toNackCORE("该设备无权登录"));
	// return tokenResponseParse;
	// }
	// termTypeLimit = resultLoginAppInfo.getTermIphoneLimit();
	// }
	// else if (termType.equals(AppConfig.TERM_TYPE_WEB)) {
	// if (resultLoginAppInfo.getTermWebLimit() <= 0) {
	// tokenResponseParse.setReturnResp(ResultFactory.toNackCORE("该设备无权登录"));
	// return tokenResponseParse;
	// }
	// termTypeLimit = resultLoginAppInfo.getTermWebLimit();
	// }
	// else {
	// tokenResponseParse.setReturnResp(ResultFactory.toNackCORE("该设备无权登录"));
	// return tokenResponseParse;
	// }
	// LoginUserToken queryUUIDToken = new LoginUserToken();
	// queryUUIDToken.setAppId(appId);
	// queryUUIDToken.setUserId(userId);
	// queryUUIDToken.setUuid(realUUID);
	//
	// LoginUserToken resultUUIDToken = loginUserTokenMapper.selectOne(queryUUIDToken);
	// // 查找该设备是否可以直接登录
	// boolean isUuidCanLogin = isUuidCanLogin(userInfoLoginReqDto, resultLoginUserAccount);
	// // 查找有没有该UUID下面的设备，有的话不需要验证码登录
	// if (StringUtils.isEmpty(userInfoLoginReqDto.getMsgVerifyCode()) && !isUuidCanLogin) {
	// // 查看设备授权状态
	// // JSONObject jsonResult = ResultFactory.toNack(ResultFactory.ERR_NEED_VERIFYCODE,
	// // "需要验证码登录");
	// if (null == resultUUIDToken) {
	// tokenResponseParse.setReturnResp(ResultFactory.toNack(ResultFactory.ERR_NEED_VERIFYCODE,
	// "需要验证码登录"));
	// return tokenResponseParse;
	// }
	// else {
	// try {
	// long timeLastValidTime =
	// AppConfig.SDF_DB_TIME.parse(resultUUIDToken.getValidTime()).getTime();
	// long timeSkip = new Date().getTime() - timeLastValidTime;
	// if (timeSkip > AppConfig.UserToken_DeleteTime()) {
	// tokenResponseParse
	// .setReturnResp(ResultFactory.toNack(ResultFactory.ERR_NEED_VERIFYCODE, "需要验证码登录"));
	// return tokenResponseParse;
	// }
	// }
	// catch (ParseException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// tokenResponseParse
	// .setReturnResp(ResultFactory.toNack(ResultFactory.ERR_NEED_VERIFYCODE, "需要验证码登录"));
	// return tokenResponseParse;
	// }
	//
	// }
	// // 查看业务时间段
	// }
	//
	// Date nowTime = new Date();
	// String nowTimeStr = AppConfig.SDF_DB_TIME.format(nowTime);
	// String validTimeString = TimeUtils.formatTime(nowTime.getTime() +
	// AppConfig.UserToken_ValidTime(),
	// AppConfig.SDF_DB_TIME);
	// LoginUserToken outUserToken = new LoginUserToken();
	// outUserToken.setAppId(appId);
	// outUserToken.setUserId(userId);
	// outUserToken.setValidTime(nowTimeStr);
	// loginUserTokenMapper.logoutAllInValid(outUserToken);
	// LoginUserToken queryUserToken = new LoginUserToken();
	// queryUserToken.setAppId(appId);
	// queryUserToken.setUserId(userId);
	// queryUserToken.setTermType(Integer.valueOf(termType));
	// List<LoginUserToken> resultTermTokenLst =
	// loginUserTokenMapper.selectLoginTokens(queryUserToken);
	// log.debug("列表返回130", resultTermTokenLst);
	// int loginTermSize = ListUtils.getSize(resultTermTokenLst);
	// int deleteTermSize = loginTermSize >= termTypeLimit ? loginTermSize - termTypeLimit + 1 : 0;
	// if (deleteTermSize > loginTermSize) {
	// deleteTermSize = loginTermSize;
	// }
	// for (int i = 0; i < deleteTermSize; i++) {
	// resultTermTokenLst.get(i).setLoginStatus(0);
	// if (loginUserTokenMapper.updateByPrimaryKeySelective(resultTermTokenLst.get(i)) <= 0) {
	// log.debug("列表返回139", resultTermTokenLst.get(i));
	// tokenResponseParse.setReturnResp(ResultFactory.toNackDB("无法登录"));
	// return tokenResponseParse;
	// }
	// if (resultTermTokenLst.get(i).getTokenId().equals(resultUUIDToken.getTokenId())) {
	// resultUUIDToken.setVersion(resultTermTokenLst.get(i).getVersion() + 1);
	// resultUUIDToken.setLoginStatus(0);
	// }
	// }
	// queryUserToken.setTermType(null);
	// List<LoginUserToken> resultTotalTokenLst =
	// loginUserTokenMapper.selectLoginTokens(queryUserToken);
	// System.out.println(resultTotalTokenLst);
	// int loginTotalSize = ListUtils.getSize(resultTotalTokenLst);
	// int deleteTotalSize = loginTotalSize >= resultLoginAppInfo.getTermLimit()
	// ? loginTotalSize - resultLoginAppInfo.getTermLimit() + 1 : 0;
	// if (deleteTotalSize > loginTotalSize) {
	// deleteTotalSize = loginTotalSize;
	// }
	// for (int i = 0; i < deleteTotalSize; i++) {
	// resultTotalTokenLst.get(i).setLoginStatus(0);
	// if (loginUserTokenMapper.updateByPrimaryKeySelective(resultTotalTokenLst.get(i)) <= 0) {
	// log.debug("列表返回156", resultTotalTokenLst.get(i));
	// tokenResponseParse.setReturnResp(ResultFactory.toNackDB("无法登录"));
	// return tokenResponseParse;
	// }
	// if (resultTotalTokenLst.get(i).getTokenId().equals(resultUUIDToken.getTokenId())) {
	// resultUUIDToken.setVersion(resultTotalTokenLst.get(i).getVersion() + 1);
	// resultUUIDToken.setLoginStatus(0);
	// }
	// }
	//
	// if (null == resultUUIDToken) {
	// LoginUserToken loginUserToken = new LoginUserToken();
	// loginUserToken.setTokenId(dbSeqService.getLoginTokenNewPk());
	// loginUserToken.setAppId(appId);
	// loginUserToken.setUserId(userId);
	// loginUserToken.setUuid(realUUID);
	// loginUserToken.setTermType(Integer.valueOf(termType));
	// loginUserToken.setToken(TokenUtil.generateToken());
	// loginUserToken.setLoginStatus(1);
	// loginUserToken.setValidTime(validTimeString);
	// loginUserToken.setCreateTime(nowTimeStr);
	// loginUserToken.setVersion(1);
	// int dbResult = loginUserTokenMapper.insert(loginUserToken);
	// if (dbResult > 0) {
	// List<LoginUserToken> userTokenListAll = new ArrayList<>();
	// for (int i = 0; i < loginTotalSize; i++) {
	// if (resultTotalTokenLst.get(i).getLoginStatus() == 1) {
	// userTokenListAll.add(resultTotalTokenLst.get(i));
	// }
	// }
	// userTokenListAll.add(loginUserToken);
	// tokenResponseParse.setLoginUserToken(loginUserToken);
	// tokenResponseParse.setTokenList(userTokenListAll);
	// tokenResponseParse.setValid(true);
	// return tokenResponseParse;
	// }
	// else {
	// log.debug("列表返回189", loginUserToken);
	// tokenResponseParse.setReturnResp(ResultFactory.toNackDB("无法登录"));
	// return tokenResponseParse;
	// }
	// }
	// else {
	// LoginUserToken loginUserToken = new LoginUserToken();
	// loginUserToken.setTokenId(resultUUIDToken.getTokenId());
	// loginUserToken.setTermType(Integer.valueOf(termType));
	// loginUserToken.setToken(TokenUtil.generateToken());
	// loginUserToken.setLoginStatus(1);
	// loginUserToken.setValidTime(validTimeString);
	// loginUserToken.setVersion(resultUUIDToken.getVersion());
	// int dbResult = loginUserTokenMapper.updateByPrimaryKeySelective(loginUserToken);
	// loginUserToken.setAppId(appId);
	// loginUserToken.setUserId(userId);
	// loginUserToken.setUuid(realUUID);
	// loginUserToken.setCreateTime(resultUUIDToken.getCreateTime());
	// loginUserToken.setVersion(resultUUIDToken.getVersion() + 1);
	//
	// if (dbResult > 0) {
	// List<LoginUserToken> userTokenListAll = new ArrayList<>();
	// for (int i = 0; i < loginTotalSize; i++) {
	// if (resultTotalTokenLst.get(i).getLoginStatus() == 1) {
	// userTokenListAll.add(resultTotalTokenLst.get(i));
	// }
	// }
	// userTokenListAll.add(loginUserToken);
	// tokenResponseParse.setLoginUserToken(loginUserToken);
	// tokenResponseParse.setTokenList(userTokenListAll);
	// tokenResponseParse.setValid(true);
	// return tokenResponseParse;
	// }
	// else {
	// log.debug("列表返回223", loginUserToken);
	// tokenResponseParse.setReturnResp(ResultFactory.toNackDB("无法登录"));
	// return tokenResponseParse;
	// }
	// }
	// }

	@Override
	public TokenResponseParse createTokenForLogin(UserInfoLoginReqDto userInfoLoginReqDto,
			LoginUserAccount resultLoginUserAccount, LoginAppInfo resultLoginAppInfo, LoginUserToken resultUUIDToken) {
		// TODO Auto-generated method stub
		TokenResponseParse tokenResponseParse = new TokenResponseParse();
		tokenResponseParse.setValid(false);
		tokenResponseParse.setNeedVerifyCode(false);
		String appId = resultLoginAppInfo.getAppId();
		String userId = resultLoginUserAccount.getLoginId();
		String termType = userInfoLoginReqDto.getTermType();
		String uuidTemp = userInfoLoginReqDto.getUuid();
		// String realUUID = appId + "_" + userId + "_" + uuidTemp;
		String realUUID = uuidTemp;
		Integer termTypeLimit = null;
		if (termType.equals(AppConfig.TERM_TYPE_ANDROID)) {
			termTypeLimit = resultLoginAppInfo.getTermAndroidLimit();
		}
		else if (termType.equals(AppConfig.TERM_TYPE_IPHONE)) {
			termTypeLimit = resultLoginAppInfo.getTermIphoneLimit();
		}
		else if (termType.equals(AppConfig.TERM_TYPE_WEB)) {
			termTypeLimit = resultLoginAppInfo.getTermWebLimit();
		}
		if (null == termTypeLimit || termTypeLimit <= 0) {
			tokenResponseParse.setReturnResp(ResultFactory.toNackCORE("该设备无权登录"));
			return tokenResponseParse;
		}
		Date nowTime = new Date();
		String nowTimeStr = AppConfig.SDF_DB_TIME.format(nowTime);
		String validTimeString = TimeUtils.formatTime(nowTime.getTime() + AppConfig.UserToken_ValidTime(),
				AppConfig.SDF_DB_TIME);
		LoginUserToken outUserToken = new LoginUserToken();
		outUserToken.setAppId(appId);
		outUserToken.setUserId(userId);
		outUserToken.setValidTime(nowTimeStr);
		loginUserTokenMapper.logoutAllInValid(outUserToken);
		LoginUserToken queryUserToken = new LoginUserToken();
		queryUserToken.setAppId(appId);
		queryUserToken.setUserId(userId);
		queryUserToken.setTermType(Integer.valueOf(termType));
		List<LoginUserToken> resultTermTokenLst = loginUserTokenMapper.selectLoginTokens(queryUserToken);
		log.debug("列表返回130", resultTermTokenLst);
		int loginTermSize = ListUtils.getSize(resultTermTokenLst);
		int deleteTermSize = loginTermSize >= termTypeLimit ? loginTermSize - termTypeLimit + 1 : 0;
		if (deleteTermSize > loginTermSize) {
			deleteTermSize = loginTermSize;
		}
		for (int i = 0; i < deleteTermSize; i++) {
			resultTermTokenLst.get(i).setLoginStatus(0);
			if (loginUserTokenMapper.updateByPrimaryKeySelective(resultTermTokenLst.get(i)) <= 0) {
				log.debug("列表返回139", resultTermTokenLst.get(i));
				tokenResponseParse.setReturnResp(ResultFactory.toNackDB("无法登录"));
				return tokenResponseParse;
			}
			if (null != resultUUIDToken
					&& resultTermTokenLst.get(i).getTokenId().equals(resultUUIDToken.getTokenId())) {
				resultUUIDToken.setVersion(resultTermTokenLst.get(i).getVersion() + 1);
				resultUUIDToken.setLoginStatus(0);
			}
		}
		queryUserToken.setTermType(null);
		List<LoginUserToken> resultTotalTokenLst = loginUserTokenMapper.selectLoginTokens(queryUserToken);
		System.out.println(resultTotalTokenLst);
		int loginTotalSize = ListUtils.getSize(resultTotalTokenLst);
		int deleteTotalSize = loginTotalSize >= resultLoginAppInfo.getTermLimit()
				? loginTotalSize - resultLoginAppInfo.getTermLimit() + 1 : 0;
		if (deleteTotalSize > loginTotalSize) {
			deleteTotalSize = loginTotalSize;
		}
		for (int i = 0; i < deleteTotalSize; i++) {
			resultTotalTokenLst.get(i).setLoginStatus(0);
			if (loginUserTokenMapper.updateByPrimaryKeySelective(resultTotalTokenLst.get(i)) <= 0) {
				log.debug("列表返回156", resultTotalTokenLst.get(i));
				tokenResponseParse.setReturnResp(ResultFactory.toNackDB("无法登录"));
				return tokenResponseParse;
			}
			if (null != resultUUIDToken
					&& resultTotalTokenLst.get(i).getTokenId().equals(resultUUIDToken.getTokenId())) {
				resultUUIDToken.setVersion(resultTotalTokenLst.get(i).getVersion() + 1);
				resultUUIDToken.setLoginStatus(0);
			}
		}

		if (null == resultUUIDToken) {
			LoginUserToken loginUserToken = new LoginUserToken();
			loginUserToken.setTokenId(dbSeqService.getLoginTokenNewPk());
			loginUserToken.setAppId(appId);
			loginUserToken.setUserId(userId);
			loginUserToken.setUuid(realUUID);
			loginUserToken.setTermType(Integer.valueOf(termType));
			loginUserToken.setToken(TokenUtil.generateLoginToken());
			loginUserToken.setLoginStatus(1);
			loginUserToken.setValidTime(validTimeString);
			loginUserToken.setCreateTime(nowTimeStr);
			loginUserToken.setLoginTime(nowTimeStr);
			loginUserToken.setVersion(1);
			int dbResult = loginUserTokenMapper.insert(loginUserToken);
			if (dbResult > 0) {
				List<LoginUserToken> userTokenListAll = new ArrayList<>();
				for (int i = 0; i < loginTotalSize; i++) {
					if (resultTotalTokenLst.get(i).getLoginStatus() == 1) {
						userTokenListAll.add(resultTotalTokenLst.get(i));
					}
				}
				userTokenListAll.add(loginUserToken);
				tokenResponseParse.setLoginUserToken(loginUserToken);
				tokenResponseParse.setTokenList(userTokenListAll);
				tokenResponseParse.setValid(true);
				return tokenResponseParse;
			}
			else {
				log.debug("列表返回189", loginUserToken);
				tokenResponseParse.setReturnResp(ResultFactory.toNackDB("无法登录"));
				return tokenResponseParse;
			}
		}
		else {
			LoginUserToken loginUserToken = new LoginUserToken();
			loginUserToken.setTokenId(resultUUIDToken.getTokenId());
			loginUserToken.setTermType(Integer.valueOf(termType));
			loginUserToken.setToken(TokenUtil.generateLoginToken());
			loginUserToken.setLoginStatus(1);
			loginUserToken.setValidTime(validTimeString);
			loginUserToken.setVersion(resultUUIDToken.getVersion());
			loginUserToken.setLoginTime(nowTimeStr);
			int dbResult = loginUserTokenMapper.updateByPrimaryKeySelective(loginUserToken);
			loginUserToken.setAppId(appId);
			loginUserToken.setUserId(userId);
			loginUserToken.setUuid(realUUID);
			loginUserToken.setCreateTime(resultUUIDToken.getCreateTime());

			loginUserToken.setVersion(resultUUIDToken.getVersion() + 1);

			if (dbResult > 0) {
				List<LoginUserToken> userTokenListAll = new ArrayList<>();
				for (int i = 0; i < loginTotalSize; i++) {
					if (resultTotalTokenLst.get(i).getLoginStatus() == 1) {
						userTokenListAll.add(resultTotalTokenLst.get(i));
					}
				}
				userTokenListAll.add(loginUserToken);
				tokenResponseParse.setLoginUserToken(loginUserToken);
				tokenResponseParse.setTokenList(userTokenListAll);
				tokenResponseParse.setValid(true);
				return tokenResponseParse;
			}
			else {
				log.debug("列表返回223", loginUserToken);
				tokenResponseParse.setReturnResp(ResultFactory.toNackDB("无法登录"));
				return tokenResponseParse;
			}
		}
	}

	@Override
	public JSONObject disableTokenForLogout(UserInfoLogout userInfoLogout) {
		// TODO Auto-generated method stub
		LoginUserToken queryToken = new LoginUserToken();
		// queryToken.setUserId(userInfoLogout.getUserId());
		// queryToken.setAppId(userInfoLogout.getAppId());
		queryToken.setTokenId(userInfoLogout.getTokenId().split("_")[0]);
		LoginUserToken resultToken = loginUserTokenMapper.selectOne(queryToken);
		if (null == resultToken || resultToken.getLoginStatus() != 1) {
			// return ResultFactory.toNackCORE("用户已经退出了");
			return ResultFactory.toNack(ResultFactory.ERR_TOKEN_INVALID, "用户已经退出了");
		}
		else {
			queryToken.setLoginStatus(0);
			queryToken.setVersion(resultToken.getVersion());
			int dbResult = loginUserTokenMapper.updateByPrimaryKeySelective(queryToken);
			if (dbResult > 0) {
				return ResultFactory.toAck(null);
			}
			else {
				return ResultFactory.toNackDB();
			}
		}

	}

	@Override
	public LoginUserToken getTokenByUuidAndUserId(String appId, String userId, String uuidReq) {
		// TODO Auto-generated method stub
		// String realUUID = appId + "_" + userId + "_" + uuidReq;
		String realUUID = uuidReq;
		LoginUserToken queryUUIDToken = new LoginUserToken();
		queryUUIDToken.setAppId(appId);
		queryUUIDToken.setUserId(userId);
		queryUUIDToken.setUuid(realUUID);
		LoginUserToken resultUUIDToken = loginUserTokenMapper.selectOne(queryUUIDToken);
		return resultUUIDToken;
	}

	@Override
	public LoginUuidParse parseUuidLimit(UserInfoLoginReqDto userInfoLoginReqDto,
			LoginUserAccount resultLoginUserAccount, LoginAppInfo resultLoginAppInfo, LoginUserToken resultUUIDToken,
			LoginTermInfo resultLoginTermInfo, LocationParse locationParse) {
		// TODO Auto-generated method stub
		String termType = userInfoLoginReqDto.getTermType();
		LoginUuidParse loginUuidParse = new LoginUuidParse();
		loginUuidParse.setValid(false);
		loginUuidParse.setNeedVerifyCode(false);
		if (termType.equals(AppConfig.TERM_TYPE_ANDROID)) {
			if (resultLoginAppInfo.getTermAndroidLimit() <= 0) {
				loginUuidParse.setReturnResp(ResultFactory.toNackCORE("该设备无权登录"));
				return loginUuidParse;
			}
		}
		else if (termType.equals(AppConfig.TERM_TYPE_IPHONE)) {
			if (resultLoginAppInfo.getTermIphoneLimit() <= 0) {
				loginUuidParse.setReturnResp(ResultFactory.toNackCORE("该设备无权登录"));
				return loginUuidParse;
			}
		}
		else if (termType.equals(AppConfig.TERM_TYPE_WEB)) {
			if (resultLoginAppInfo.getTermWebLimit() <= 0) {
				loginUuidParse.setReturnResp(ResultFactory.toNackCORE("该设备无权登录"));
				return loginUuidParse;
			}
		}
		else {
			loginUuidParse.setReturnResp(ResultFactory.toNackCORE("该设备无权登录"));
			return loginUuidParse;
		}
		// 查看是否频繁登录。
		if (StringUtils.isEmpty(userInfoLoginReqDto.getMsgVerifyCode())
				&& AppConfig.UserToken_UuidChangeLimitCount() > 0 && AppConfig.UserToken_UuidChangeLimitTime() > 0) {
			String timeLimit = TimeUtils.formatTime(
					System.currentTimeMillis() - AppConfig.UserToken_UuidChangeLimitTime(), AppConfig.SDF_DB_TIME);
			LoginUserToken loginUserToken = new LoginUserToken();
			loginUserToken.setUserId(resultLoginUserAccount.getLoginId());
			loginUserToken.setAppId(resultLoginAppInfo.getAppId());
			loginUserToken.setUuid(userInfoLoginReqDto.getUuid());
			loginUserToken.setLoginTime(timeLimit);
			int count = loginUserTokenMapper.selectUuidChangeCount(loginUserToken);
			if (count >= AppConfig.UserToken_UuidChangeLimitCount()) {
				loginUuidParse.setReturnResp(ResultFactory.toNack(ResultFactory.ERR_NEED_VERIFYCODE, "频繁切换登录，需要验证码"));
				return loginUuidParse;
			}
		}
		if (StringUtils.isEmpty(userInfoLoginReqDto.getMsgVerifyCode())
				&& AppConfig.UserToken_UserChangeLimitCount() > 0 && AppConfig.UserToken_UserChangeLimitTime() > 0) {
			String timeLimit = TimeUtils.formatTime(
					System.currentTimeMillis() - AppConfig.UserToken_UserChangeLimitTime(), AppConfig.SDF_DB_TIME);
			LoginUserToken loginUserToken = new LoginUserToken();
			loginUserToken.setUserId(resultLoginUserAccount.getLoginId());
			loginUserToken.setAppId(resultLoginAppInfo.getAppId());
			loginUserToken.setUuid(userInfoLoginReqDto.getUuid());
			loginUserToken.setLoginTime(timeLimit);
			int count = loginUserTokenMapper.selectUserIdChangeCount(loginUserToken);
			if (count >= AppConfig.UserToken_UserChangeLimitCount()) {
				loginUuidParse.setReturnResp(ResultFactory.toNack(ResultFactory.ERR_NEED_VERIFYCODE, "频繁更换设备登录，需要验证码"));
				return loginUuidParse;
			}
		}
		// 查找风险地区，风险地区强制验证码登录
		if (StringUtils.isEmpty(userInfoLoginReqDto.getMsgVerifyCode()) && null != locationParse
				&& locationParse.isVerifyLocation()) {
			List<String> listArea = AppConfig.UserToken_RiskArea();
			boolean isRisk = false;
			if (null != listArea && listArea.size() > 0) {
				for (String tmp : listArea) {
					String area = locationParse.getProvince() + "-" + locationParse.getCity();
					if (area.contains(tmp)) {
						isRisk = true;
						break;
					}
				}
			}
			if (isRisk) {
				loginUuidParse
						.setReturnResp(ResultFactory.toNack(ResultFactory.ERR_NEED_VERIFYCODE, "在交易风险地区登录，需要验证码"));
				return loginUuidParse;
			}
		}

		// 查找该设备是否可以直接登录
		boolean isUuidCanLogin = isUuidCanLogin(userInfoLoginReqDto, resultLoginUserAccount);
		// 查找有没有该UUID下面的设备，有的话不需要验证码登录
		if (StringUtils.isEmpty(userInfoLoginReqDto.getMsgVerifyCode()) && !isUuidCanLogin) {

			// 若是不同地区登录则需要验证码
			if (null != locationParse && locationParse.isVerifyLocation()) {
				if (null == resultLoginTermInfo || StringUtils.isEmpty(resultLoginTermInfo.getProvince())
						|| StringUtils.isEmpty(resultLoginTermInfo.getCity())) {
					loginUuidParse
							.setReturnResp(ResultFactory.toNack(ResultFactory.ERR_NEED_VERIFYCODE, "换地区登录，需要验证码"));
					return loginUuidParse;
				}
				if (!resultLoginTermInfo.getProvince().equals(locationParse.getProvince())
						|| !resultLoginTermInfo.getCity().equals(locationParse.getCity())) {
					loginUuidParse
							.setReturnResp(ResultFactory.toNack(ResultFactory.ERR_NEED_VERIFYCODE, "换地区登录，需要验证码"));
					return loginUuidParse;
				}

			}
			// 若是在登录限制时段则需要验证码登录。
			String loginVerifyTime = AppConfig.UserToken_LoginVerifyTime();
			if (RegexUtil.doRegex(loginVerifyTime, RegexUtil.LOGIN_VERIFYTIME)) {
				String[] verifyTimeArray = loginVerifyTime.split("-");
				String timeNow = AppConfig.SDF_LOGIN_VERIFY.format(new Date());

				if (verifyTimeArray[0].compareTo(verifyTimeArray[1]) > 0) {
					if (timeNow.compareTo(verifyTimeArray[0]) >= 0 && timeNow.compareTo("24:00") <= 0) {
						loginUuidParse
								.setReturnResp(ResultFactory.toNack(ResultFactory.ERR_NEED_VERIFYCODE, "夜间时段登录，需要验证码"));
						return loginUuidParse;
					}
					if (timeNow.compareTo(verifyTimeArray[1]) <= 0 && timeNow.compareTo("00:00") >= 0) {
						loginUuidParse
								.setReturnResp(ResultFactory.toNack(ResultFactory.ERR_NEED_VERIFYCODE, "限制时段登录，需要验证码"));
						return loginUuidParse;
					}
				}
				else if (verifyTimeArray[0].compareTo(verifyTimeArray[1]) < 0) {
					if (timeNow.compareTo(verifyTimeArray[0]) >= 0 && timeNow.compareTo(verifyTimeArray[1]) <= 0) {
						loginUuidParse.setReturnResp(
								ResultFactory.toNack(ResultFactory.ERR_NEED_VERIFYCODE, "限制时段登录，需要验证码登录"));
						return loginUuidParse;
					}
				}
			}

			// 查看设备授权状态
			// JSONObject jsonResult = ResultFactory.toNack(ResultFactory.ERR_NEED_VERIFYCODE,
			// "需要验证码登录");
			if (null == resultUUIDToken) {
				loginUuidParse.setReturnResp(ResultFactory.toNack(ResultFactory.ERR_NEED_VERIFYCODE, "新设备登录，需要验证码"));
				return loginUuidParse;
			}
			else {
				try {
					long timeLastValidTime = AppConfig.SDF_DB_TIME.parse(resultUUIDToken.getValidTime()).getTime();
					long timeSkip = new Date().getTime() - timeLastValidTime;
					if (timeSkip > AppConfig.UserToken_DeleteTime()) {
						loginUuidParse.setReturnResp(
								ResultFactory.toNack(ResultFactory.ERR_NEED_VERIFYCODE, "该设备长期未登录，需要验证码"));
						return loginUuidParse;
					}
				}
				catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					loginUuidParse
							.setReturnResp(ResultFactory.toNack(ResultFactory.ERR_NEED_VERIFYCODE, "该设备长期未登录，需要验证码登录"));
					return loginUuidParse;
				}
			}
			// 查看业务时间段
		}
		loginUuidParse.setValid(true);
		return loginUuidParse;
	}

	private boolean isUuidCanLogin(UserInfoLoginReqDto userInfoLoginReqDto, LoginUserAccount resultLoginUserAccount) {
		if (!StringUtils.isEmpty(userInfoLoginReqDto.getMsgVerifyCode())) {
			return true;
		}
		if (!userInfoLoginReqDto.getUuid().equals(resultLoginUserAccount.getLastAuthUuid())) {
			return false;
		}
		else {
			try {
				long timeLastValidTime = AppConfig.SDF_DB_TIME.parse(resultLoginUserAccount.getLastAuthTime())
						.getTime();
				long timeSkip = new Date().getTime() - timeLastValidTime;
				if (timeSkip < 0 || timeSkip > AppConfig.UserUuidAuthTime()) {
					return false;
				}
				else {
					return true;
				}
			}
			catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}

		}
	}

	public LocationParse parseLocationByLatLng(String lat, String lng) {
		LocationParse locationParse = new LocationParse();
		if (null == AppConfig.UserToken_LoginVerifyLocation() || !AppConfig.UserToken_LoginVerifyLocation()) {
			locationParse.setValid(true);
			return locationParse;
		}

		locationParse.setValid(false);
		double latValue = 0;
		double lngValue = 0;
		try {
			latValue = Double.valueOf(lat);
			lngValue = Double.valueOf(lng);
		}
		catch (Exception e) {
			latValue = 0;
			lngValue = 0;
		}
		if ((latValue < 0.5 && latValue > -0.5) || (lngValue < 0.5 && lngValue > -0.5)) {
			locationParse.setReturnResp(ResultFactory.toNackPARAM("定位信息不正确，请开启定位后重新提交"));
			return locationParse;
		}
		Map<String, String> map = new HashMap<String, String>();
		// map.put("callback", "renderReverse");
		map.put("location", lat + "," + lng);
		map.put("output", "json");
		map.put("ak", AppConfig.BaiduLocationAK());
		String url = OkHttpConfig.createRequestUrlForGet("http://api.map.baidu.com/geocoder/v2/", map);
		ResponseData responseData = new DataOKHttp().setUrl(url).setPost(false).doHttp(JSONObject.class);
		System.out.println(responseData);
		if (null == responseData || null == responseData.getResultObject()) {
			locationParse.setReturnResp(ResultFactory.toNackCORE("获取定位信息失败"));
			return locationParse;
		}
		JSONObject jsonObject = (JSONObject) responseData.getResultObject();
		JSONObject resultJsonObject = jsonObject.getJSONObject("result");
		if (!"0".equals(jsonObject.getString("status")) || null == resultJsonObject) {
			locationParse.setReturnResp(ResultFactory.toNackCORE("获取定位信息失败"));
			return locationParse;
		}

		JSONObject addressComponent = resultJsonObject.getJSONObject("addressComponent");
		if (null == addressComponent || !"中国".equals(addressComponent.getString("country"))) {
			locationParse.setReturnResp(ResultFactory.toNackCORE("定位获取不在国内，无法进行登录"));
			return locationParse;
		}
		locationParse.setValid(true);
		locationParse.setCountry(addressComponent.getString("country"));
		locationParse.setProvince(addressComponent.getString("province"));
		locationParse.setCity(addressComponent.getString("city"));
		locationParse.setDetailAdress(resultJsonObject.getString("formatted_address"));
		return locationParse;
	}

}
