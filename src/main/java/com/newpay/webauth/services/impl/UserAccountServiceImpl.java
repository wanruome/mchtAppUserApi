/**
 *	@copyright wanruome-2018
 * 	@author wanruome
 * 	@create 2018年6月10日 下午3:11:31
 */
package com.newpay.webauth.services.impl;

import java.security.PublicKey;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.newpay.webauth.aop.SystemLogThreadLocal;
import com.newpay.webauth.config.AppConfig;
import com.newpay.webauth.config.EncryptConfig;
import com.newpay.webauth.dal.core.PwdErrParse;
import com.newpay.webauth.dal.core.TokenResponseParse;
import com.newpay.webauth.dal.mapper.LoginAppInfoMapper;
import com.newpay.webauth.dal.mapper.LoginUserAccountMapper;
import com.newpay.webauth.dal.model.LoginAppInfo;
import com.newpay.webauth.dal.model.LoginUserAccount;
import com.newpay.webauth.dal.model.LoginUserToken;
import com.newpay.webauth.dal.request.userinfo.UserInfoFindPwd;
import com.newpay.webauth.dal.request.userinfo.UserInfoLoginReqDto;
import com.newpay.webauth.dal.request.userinfo.UserInfoLogout;
import com.newpay.webauth.dal.request.userinfo.UserInfoModifyEmail;
import com.newpay.webauth.dal.request.userinfo.UserInfoModifyMobie;
import com.newpay.webauth.dal.request.userinfo.UserInfoModifyName;
import com.newpay.webauth.dal.request.userinfo.UserInfoModifyPwd;
import com.newpay.webauth.dal.request.userinfo.UserInfoRegisterReqDto;
import com.newpay.webauth.dal.response.ResultFactory;
import com.newpay.webauth.services.DbSeqService;
import com.newpay.webauth.services.SecureTokenService;
import com.newpay.webauth.services.UserAccountService;
import com.ruomm.base.http.HttpConfig;
import com.ruomm.base.http.ResponseData;
import com.ruomm.base.http.okhttp.DataOKHttp;
import com.ruomm.base.tools.Base64;
import com.ruomm.base.tools.EncryptUtils;
import com.ruomm.base.tools.RSAUtils;
import com.ruomm.base.tools.RegexUtil;
import com.ruomm.base.tools.StringUtils;
import com.ruomm.base.tools.TimeUtils;

@Component
@Service
public class UserAccountServiceImpl implements UserAccountService {
	@Autowired
	LoginAppInfoMapper loginAppInfoMapper;
	@Autowired
	SecureTokenService secureTokenService;
	@Autowired
	DbSeqService dbSeqService;
	@Autowired
	LoginUserAccountMapper loginUserAccountMapper;
	boolean VERIFY_IN_DB = true;
	private static final String PWD_ERR_NONE = "none";

	@Override
	public Object doRegister(UserInfoRegisterReqDto loginUserReqDto) {
		// TODO Auto-generated method stub

		LoginUserAccount insertUserAccount = new LoginUserAccount();
		// 验证手机号是否有效
		if (AppConfig.ACCOUNT_TYPE_MOBILE.equals(loginUserReqDto.getAccountType())) {
			loginUserReqDto.setMobie(loginUserReqDto.getAccount());
		}
		else if (AppConfig.ACCOUNT_TYPE_EMAIL.equals(loginUserReqDto.getAccountType())) {
			loginUserReqDto.setEmail(loginUserReqDto.getAccount());
		}
		else if (AppConfig.ACCOUNT_TYPE_NAME.equals(loginUserReqDto.getAccountType())) {
			loginUserReqDto.setName(loginUserReqDto.getAccount());
		}
		else {
			return ResultFactory.toNackPARAM();
		}
		if (!StringUtils.isEmpty(loginUserReqDto.getMobie())) {
			if (!RegexUtil.doRegex(loginUserReqDto.getMobie(), RegexUtil.MOBILE_NUM)) {
				return ResultFactory.toNackPARAM();
			}
			if (VERIFY_IN_DB) {
				LoginUserAccount queryUserInfo = new LoginUserAccount();
				queryUserInfo.setLoginMobile(loginUserReqDto.getMobie());
				List<LoginUserAccount> lstResult = loginUserAccountMapper.select(queryUserInfo);
				if (null != lstResult && lstResult.size() > 0) {
					return ResultFactory.toNackCORE("手机号已经被注册了");
				}
			}
			insertUserAccount.setLoginMobile(loginUserReqDto.getMobie());
		}
		// 验证邮箱是否有效
		if (!StringUtils.isEmpty(loginUserReqDto.getEmail())) {
			if (!RegexUtil.doRegex(loginUserReqDto.getEmail(), RegexUtil.EMAILS)
					|| StringUtils.getLengthByChar(loginUserReqDto.getEmail()) > 64) {
				return ResultFactory.toNackPARAM();
			}
			if (VERIFY_IN_DB) {
				LoginUserAccount queryUserAccount = new LoginUserAccount();
				queryUserAccount.setLoginEmail(loginUserReqDto.getEmail());
				List<LoginUserAccount> lstResult = loginUserAccountMapper.select(queryUserAccount);
				if (null != lstResult && lstResult.size() > 0) {
					return ResultFactory.toNackCORE("邮箱已经被注册了");
				}
			}
			insertUserAccount.setLoginEmail(loginUserReqDto.getEmail());
		}
		// 验证用户名是否有效
		if (!StringUtils.isEmpty(loginUserReqDto.getName())) {
			if (!RegexUtil.doRegex(loginUserReqDto.getName(), RegexUtil.APP_LOGIN_NAME)) {
				return ResultFactory.toNackPARAM();
			}
			if (VERIFY_IN_DB) {
				LoginUserAccount queryUserAccount = new LoginUserAccount();
				queryUserAccount.setLoginName(loginUserReqDto.getName());
				List<LoginUserAccount> lstResult = loginUserAccountMapper.select(queryUserAccount);
				if (null != lstResult && lstResult.size() > 0) {
					return ResultFactory.toNackCORE("用户名已经被注册了");
				}
			}
			insertUserAccount.setLoginName(loginUserReqDto.getName());
		}

		insertUserAccount.setLoginPwd(loginUserReqDto.getPwd());
		// 插入记录
		insertUserAccount.setLoginId(dbSeqService.getLoginUserNewPK());
		insertUserAccount.setVersion(1);
		insertUserAccount.setStatus(1);
		String nowTimeStr = AppConfig.SDF_DB_TIME.format(new Date());
		insertUserAccount.setRegisterTime(nowTimeStr);
		insertUserAccount.setUpdateTime(nowTimeStr);
		insertUserAccount.setPwdErrCount(0);
		insertUserAccount.setLastAuthUuid(loginUserReqDto.getUuid());
		insertUserAccount.setLastAuthTime(nowTimeStr);
		SystemLogThreadLocal.setUserId(insertUserAccount.getLoginId());
		int dbResult = loginUserAccountMapper.insertSelective(insertUserAccount);
		if (dbResult > 0) {
			return ResultFactory.toAck(null);
		}
		else {
			return ResultFactory.toNackCORE("注册失败：手机号、用户名、邮箱等重复");
		}
	}

	@Override
	public Object doLogin(UserInfoLoginReqDto userInfoLoginReqDto) {
		LoginUserAccount queryUserAccount = new LoginUserAccount();
		if (AppConfig.ACCOUNT_TYPE_MOBILE.equals(userInfoLoginReqDto.getAccountType())) {
			queryUserAccount.setLoginMobile(userInfoLoginReqDto.getAccount());
		}
		else if (AppConfig.ACCOUNT_TYPE_EMAIL.equals(userInfoLoginReqDto.getAccountType())) {
			queryUserAccount.setLoginEmail(userInfoLoginReqDto.getAccount());
		}
		else if (AppConfig.ACCOUNT_TYPE_NAME.equals(userInfoLoginReqDto.getAccountType())) {
			queryUserAccount.setLoginName(userInfoLoginReqDto.getAccount());
		}
		else if (AppConfig.ACCOUNT_TYPE_USERID.equals(userInfoLoginReqDto.getAccountType())) {
			queryUserAccount.setLoginId(userInfoLoginReqDto.getAccount());
		}
		else {
			return ResultFactory.toNackPARAM();
		}
		LoginUserAccount resultLoginUserAccount = loginUserAccountMapper.selectOne(queryUserAccount);
		if (null == resultLoginUserAccount) {
			return ResultFactory.toNackCORE("用户不存在");
		}
		SystemLogThreadLocal.setUserId(resultLoginUserAccount.getLoginId());
		// if (null != resultLoginUserAccount.getPwdErrCount() && AppConfig.UserPwdErrLimit() > 0
		// && resultLoginUserAccount.getPwdErrCount() > 5) {
		// return ResultFactory.toNack(ResultFactory.ERR_NEED_VERIFYCODE, "密码错误次数过多，请验证登录或找回密码");
		// }
		// // 验证密码错误次数
		// if
		// (!EncryptConfig.decryptPWD(resultLoginUserAccount.getLoginPwd()).equals(userInfoLoginReqDto.getPwd()))
		// {
		// updateLoginUserPwdCount(userInfoLoginReqDto, resultLoginUserAccount, false);
		// return ResultFactory.toNack(ResultFactory.ERR_PWD_WRONG, null);
		// }
		// else {
		// boolean flagPwdCount = updateLoginUserPwdCount(userInfoLoginReqDto,
		// resultLoginUserAccount, true);
		// if (!flagPwdCount) {
		// return ResultFactory.toNackDB();
		// }
		// }
		PwdErrParse pwdErrParse = parseErrCount(resultLoginUserAccount, userInfoLoginReqDto.getPwd(),
				userInfoLoginReqDto.getUuid(), "密码");
		if (!pwdErrParse.isValid()) {
			return pwdErrParse.getReturnResp();
		}

		LoginAppInfo queryLoginAppinfo = new LoginAppInfo();
		queryLoginAppinfo.setAppId(userInfoLoginReqDto.getAppId());
		LoginAppInfo resultLoginAppinfo = loginAppInfoMapper.selectByPrimaryKey(queryLoginAppinfo);
		if (null == resultLoginAppinfo || resultLoginAppinfo.getStatus() != 1) {
			return ResultFactory.toNackCORE("无法登陆，应用授权失败");
		}
		// String token = EncryptUtils.encodingMD5(TokenUtil.generateToken());
		// UsernamePasswordToken shiroToken = new
		// UsernamePasswordToken(resultLoginUserAccount.getLoginId(),
		// TokenUtil.generateToken(), false);
		// SecurityUtils.getSubject().login(shiroToken);
		// TokenResponseParse tokenResponseParse = userTokenInfoService.createTokenForLogin(
		// resultLoginUserAccount.getLoginId(), userInfoLoginReqDto.getAppId(),
		// userInfoLoginReqDto.getTermType(),
		// userInfoLoginReqDto.getUuid());
		TokenResponseParse tokenResponseParse = secureTokenService.createTokenForLogin(userInfoLoginReqDto,
				resultLoginUserAccount, resultLoginAppinfo);
		if (!tokenResponseParse.isValid()) {
			return tokenResponseParse.getReturnResp();
		}
		// 发送数据到第三方服务器上
		if (!StringUtils.isEmpty(resultLoginAppinfo.getNotifyUrl())
				&& resultLoginAppinfo.getNotifyUrl().toLowerCase().startsWith("http")) {
			List<JSONObject> lstTokenJsons = new ArrayList<JSONObject>();
			List<LoginUserToken> lstToken = tokenResponseParse.getTokenList();
			StringBuilder sb = new StringBuilder();
			for (LoginUserToken tmp : lstToken) {
				sb.append(tmp.getTokenId()).append(tmp.getToken());
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("userId", tmp.getUserId());
				jsonObject.put("tokenId", tmp.getTokenId());
				jsonObject.put("token", tmp.getToken());
				jsonObject.put("termType", tmp.getTermType());
				jsonObject.put("validTime", tmp.getValidTime());
				lstTokenJsons.add(jsonObject);
			}
			String tokenSignMd5 = EncryptUtils.encodingMD5(sb.toString());
			PublicKey tokenKey = RSAUtils.getPublicKey(Base64.decode(resultLoginAppinfo.getPublicKey()));
			byte[] tokeSignData = RSAUtils.encryptData(tokenSignMd5.getBytes(), tokenKey);
			if (null == tokeSignData) {
				return ResultFactory.toNackCORE("应用授权登录失败，应用秘钥不正确");
			}
			String tokenSignRSAMD5 = Base64.encode(tokeSignData);

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("signInfo", tokenSignRSAMD5);
			jsonObject.put("tokenList", lstTokenJsons);
			jsonObject.put("token", tokenResponseParse.getTokenList());
			JSONObject json = ResultFactory.toAck(jsonObject);
			ResponseData responseData = new DataOKHttp().setUrl(resultLoginAppinfo.getNotifyUrl())
					.setRequestBody(json.toJSONString()).setRequestBody("fads").doHttp(String.class);
			if (null == responseData || responseData.getStatus() != HttpConfig.Code_Success) {
				return ResultFactory.toNackCORE("应用授权登录失败，第三方服务器无响应");
			}
		}
		Map<String, String> resultData = new HashMap<>();
		resultData.put("tokenId", tokenResponseParse.getLoginUserToken().getTokenId());
		resultData.put("token", tokenResponseParse.getLoginUserToken().getToken());
		resultData.put("validTime", tokenResponseParse.getLoginUserToken().getValidTime());
		resultData.put("termType", tokenResponseParse.getLoginUserToken().getTermType() + "");
		resultData.put("userId", resultLoginUserAccount.getLoginId());
		resultData.put("appId", tokenResponseParse.getLoginUserToken().getAppId());
		resultData.put("email", resultLoginUserAccount.getLoginEmail());
		resultData.put("name", resultLoginUserAccount.getLoginName());
		resultData.put("mobile", resultLoginUserAccount.getLoginMobile());
		return ResultFactory.toAck(resultData);
	}

	@Override
	public Object doModifyPwd(UserInfoModifyPwd userInfoModifyPwd) {
		LoginAppInfo queryLoginAppinfo = new LoginAppInfo();
		queryLoginAppinfo.setAppId(userInfoModifyPwd.getAppId());
		LoginAppInfo resultLoginAppinfo = loginAppInfoMapper.selectByPrimaryKey(queryLoginAppinfo);
		if (null == resultLoginAppinfo || resultLoginAppinfo.getStatus() != 1) {
			return ResultFactory.toNackCORE("无法修改密码，应用授权失败");
		}
		LoginUserAccount dbLoginUserAccount = queryLoginUserAccount(userInfoModifyPwd.getUserId());
		if (null == dbLoginUserAccount) {
			return ResultFactory.toNackCORE("用户不存在");
		}
		PwdErrParse pwdErrParse = parseErrCount(dbLoginUserAccount, userInfoModifyPwd.getOldPwd(),
				userInfoModifyPwd.getUuid(), "旧密码");
		if (!pwdErrParse.isValid()) {
			return pwdErrParse.getReturnResp();
		}
		// if
		// (!userInfoModifyPwd.getOldPwd().equals(EncryptConfig.decryptPWD(dbLoginUserAccount.getLoginPwd())))
		// {
		// return ResultFactory.toNack(ResultFactory.ERR_PWD_WRONG, "旧的密码不正确");
		// }
		String pwdEncrypt = EncryptConfig.encryptPWD(userInfoModifyPwd.getNewPwd());
		if (StringUtils.isEmpty(pwdEncrypt)) {
			ResultFactory.toNack(ResultFactory.ERR_PWD_PARSE, null);
		}
		LoginUserAccount updateUserAccount = new LoginUserAccount();
		updateUserAccount.setLoginId(userInfoModifyPwd.getUserId());
		updateUserAccount.setLoginPwd(pwdEncrypt);
		updateUserAccount.setUpdateTime(AppConfig.SDF_DB_TIME.format(new Date()));
		boolean dbFlag = updateLoginUserAccount(dbLoginUserAccount, updateUserAccount);
		if (dbFlag) {
			Map<String, String> mapResult = new HashMap<String, String>();
			mapResult.put("version", updateUserAccount.getVersion() + "");
			return ResultFactory.toAck(mapResult);
		}
		else {
			return ResultFactory.toNackDB();
		}
	}

	@Override
	public Object doFindPwd(UserInfoFindPwd userInfoFindPwd) {
		// TODO Auto-generated method stub
		LoginUserAccount queryUserAccount = new LoginUserAccount();
		if (AppConfig.ACCOUNT_TYPE_MOBILE.equals(userInfoFindPwd.getAccountType())) {
			queryUserAccount.setLoginMobile(userInfoFindPwd.getAccount());
		}
		else if (AppConfig.ACCOUNT_TYPE_EMAIL.equals(userInfoFindPwd.getAccountType())) {
			queryUserAccount.setLoginEmail(userInfoFindPwd.getAccount());
		}
		else {
			return ResultFactory.toNackPARAM();
		}
		LoginUserAccount dbLoginUserAccount = loginUserAccountMapper.selectOne(queryUserAccount);
		if (null == dbLoginUserAccount || dbLoginUserAccount.getStatus() != 1) {
			return ResultFactory.toNackCORE("账户不存在");
		}
		if (dbLoginUserAccount.getStatus() != 1) {
			return ResultFactory.toNackCORE("账户已停用");
		}
		String pwdEncrypt = EncryptConfig.encryptPWD(userInfoFindPwd.getNewPwd());
		if (StringUtils.isEmpty(pwdEncrypt)) {
			ResultFactory.toNack(ResultFactory.ERR_PWD_PARSE, null);
		}
		String nowTimeStr = AppConfig.SDF_DB_TIME.format(new Date());
		LoginUserAccount updateUserAccount = new LoginUserAccount();
		updateUserAccount.setLoginId(dbLoginUserAccount.getLoginId());
		updateUserAccount.setLoginPwd(pwdEncrypt);
		updateUserAccount.setLastAuthTime(nowTimeStr);
		updateUserAccount.setLastAuthUuid(userInfoFindPwd.getUuid());
		updateUserAccount.setPwdErrCount(0);
		updateUserAccount.setPwdErrTime(PWD_ERR_NONE);
		updateUserAccount.setUpdateTime(nowTimeStr);
		boolean dbFlag = updateLoginUserAccount(dbLoginUserAccount, updateUserAccount);
		if (dbFlag) {
			return ResultFactory.toAck(null);
		}
		else {
			return ResultFactory.toNackDB();
		}
	}

	@Override
	public Object doModifyMobile(UserInfoModifyMobie userInfoModifyMobie) {
		// TODO Auto-generated method stub
		LoginAppInfo queryLoginAppinfo = new LoginAppInfo();
		queryLoginAppinfo.setAppId(userInfoModifyMobie.getAppId());
		LoginAppInfo resultLoginAppinfo = loginAppInfoMapper.selectByPrimaryKey(queryLoginAppinfo);
		if (null == resultLoginAppinfo || resultLoginAppinfo.getStatus() != 1) {
			return ResultFactory.toNackCORE("无法修改手机号，应用授权失败");
		}
		if (VERIFY_IN_DB) {
			LoginUserAccount queryUserAccount = new LoginUserAccount();
			queryUserAccount.setLoginMobile(userInfoModifyMobie.getNewMobile());
			List<LoginUserAccount> lstResult = loginUserAccountMapper.select(queryUserAccount);
			if (null != lstResult && lstResult.size() > 0) {
				if (userInfoModifyMobie.getUserId().equals(lstResult.get(0).getLoginId())) {
					return ResultFactory.toNackCORE("新手机号不能和旧手机号相同");
				}
				else {
					return ResultFactory.toNackCORE("手机号已经被注册了");
				}
			}
		}
		LoginUserAccount dbLoginUserAccount = queryLoginUserAccount(userInfoModifyMobie.getUserId());
		if (null == dbLoginUserAccount) {
			return ResultFactory.toNackCORE("用户不存在");
		}
		LoginUserAccount updateUserAccount = new LoginUserAccount();
		updateUserAccount.setLoginId(userInfoModifyMobie.getUserId());
		updateUserAccount.setLoginMobile(userInfoModifyMobie.getNewMobile());
		updateUserAccount.setUpdateTime(AppConfig.SDF_DB_TIME.format(new Date()));
		boolean dbFlag = updateLoginUserAccount(dbLoginUserAccount, updateUserAccount);
		if (dbFlag) {
			Map<String, String> mapResult = new HashMap<String, String>();
			mapResult.put("version", updateUserAccount.getVersion() + "");
			return ResultFactory.toAck(mapResult);
		}
		else {
			return ResultFactory.toNackDB();
		}
	}

	@Override
	public Object doModifyEmail(UserInfoModifyEmail userInfoModifyEmail) {
		// TODO Auto-generated method stub
		LoginAppInfo queryLoginAppinfo = new LoginAppInfo();
		queryLoginAppinfo.setAppId(userInfoModifyEmail.getAppId());
		LoginAppInfo resultLoginAppinfo = loginAppInfoMapper.selectByPrimaryKey(queryLoginAppinfo);
		if (null == resultLoginAppinfo || resultLoginAppinfo.getStatus() != 1) {
			return ResultFactory.toNackCORE("无法修改邮箱，应用授权失败");
		}
		if (VERIFY_IN_DB) {
			LoginUserAccount queryUserAccount = new LoginUserAccount();
			queryUserAccount.setLoginEmail(userInfoModifyEmail.getNewEmail());
			List<LoginUserAccount> lstResult = loginUserAccountMapper.select(queryUserAccount);
			if (null != lstResult && lstResult.size() > 0) {
				if (userInfoModifyEmail.getUserId().equals(lstResult.get(0).getLoginId())) {
					return ResultFactory.toNackCORE("新邮箱不能和旧邮箱相同");
				}
				else {
					return ResultFactory.toNackCORE("邮箱已经被注册了");
				}
			}
		}
		LoginUserAccount dbLoginUserAccount = queryLoginUserAccount(userInfoModifyEmail.getUserId());
		if (null == dbLoginUserAccount) {
			return ResultFactory.toNackCORE("用户不存在");
		}
		// 验证authToken是否有效
		LoginUserAccount updateUserAccount = new LoginUserAccount();
		updateUserAccount.setLoginId(userInfoModifyEmail.getUserId());
		updateUserAccount.setLoginEmail(userInfoModifyEmail.getNewEmail());
		boolean dbFlag = updateLoginUserAccount(dbLoginUserAccount, updateUserAccount);
		if (dbFlag) {
			Map<String, String> mapResult = new HashMap<String, String>();
			mapResult.put("version", updateUserAccount.getVersion() + "");
			return ResultFactory.toAck(mapResult);
		}
		else {
			return ResultFactory.toNackDB();
		}
	}

	@Override
	public Object doModifyName(UserInfoModifyName userInfoModifyName) {
		// TODO Auto-generated method stub
		LoginAppInfo queryLoginAppinfo = new LoginAppInfo();
		queryLoginAppinfo.setAppId(userInfoModifyName.getAppId());
		LoginAppInfo resultLoginAppinfo = loginAppInfoMapper.selectByPrimaryKey(queryLoginAppinfo);
		if (null == resultLoginAppinfo || resultLoginAppinfo.getStatus() != 1) {
			return ResultFactory.toNackCORE("无法修改用户名，应用授权失败");
		}
		if (VERIFY_IN_DB) {
			LoginUserAccount queryUserAccount = new LoginUserAccount();
			queryUserAccount.setLoginName(userInfoModifyName.getNewName());
			List<LoginUserAccount> lstResult = loginUserAccountMapper.select(queryUserAccount);
			if (null != lstResult && lstResult.size() > 0) {
				if (userInfoModifyName.getUserId().equals(lstResult.get(0).getLoginId())) {
					return ResultFactory.toNackCORE("新用户名不能和旧用户名相同");
				}
				else {
					return ResultFactory.toNackCORE("用户名已经被注册了");
				}
			}
		}
		LoginUserAccount dbLoginUserAccount = queryLoginUserAccount(userInfoModifyName.getUserId());
		if (null == dbLoginUserAccount) {
			return ResultFactory.toNackCORE("用户不存在");
		}
		// 验证authToken是否有效
		LoginUserAccount updateUserAccount = new LoginUserAccount();
		updateUserAccount.setLoginId(userInfoModifyName.getUserId());
		updateUserAccount.setLoginName(userInfoModifyName.getNewName());
		updateUserAccount.setUpdateTime(AppConfig.SDF_DB_TIME.format(new Date()));
		boolean dbFlag = updateLoginUserAccount(dbLoginUserAccount, updateUserAccount);
		if (dbFlag) {
			Map<String, String> mapResult = new HashMap<String, String>();
			mapResult.put("version", updateUserAccount.getVersion() + "");
			return ResultFactory.toAck(mapResult);
		}
		else {
			return ResultFactory.toNackDB();
		}
	}

	@Override
	public Object doLogout(UserInfoLogout userInfoLogout) {
		return secureTokenService.disableTokenForLogout(userInfoLogout);

	}

	public LoginUserAccount queryLoginUserAccount(String userId) {
		LoginUserAccount queryUserAccount = new LoginUserAccount();
		queryUserAccount.setLoginId(userId);

		return loginUserAccountMapper.selectByPrimaryKey(queryUserAccount);
	}

	// public boolean updateLoginUserPwdCount(UserInfoLoginReqDto userInfoLoginReqDto,
	// LoginUserAccount dbLoginUserAccount,
	// boolean isSuccess) {
	// if (isSuccess) {
	// LoginUserAccount updateBean = new LoginUserAccount();
	// updateBean.setLoginId(dbLoginUserAccount.getLoginId());
	// updateBean.setPwdErrCount(0);
	// updateBean.setLastAuthUuid(userInfoLoginReqDto.getUuid());
	// updateBean.setLastAuthTime(AppConfig.SDF_DB_TIME.format(new Date()));
	// updateBean.setVersion(dbLoginUserAccount.getVersion());
	// int dbResult = loginUserAccountMapper.updateByPrimaryKeySelective(updateBean);
	// if (dbResult > 0) {
	// dbLoginUserAccount.setVersion(dbLoginUserAccount.getVersion() + 1);
	// }
	// return dbResult > 0 ? true : false;
	// }
	// else {
	// LoginUserAccount updateBean = new LoginUserAccount();
	// updateBean.setLoginId(dbLoginUserAccount.getLoginId());
	// updateBean.setPwdErrCount(
	// null == dbLoginUserAccount.getPwdErrCount() ? 1 : dbLoginUserAccount.getPwdErrCount() + 1);
	//
	// updateBean.setVersion(dbLoginUserAccount.getVersion());
	// int dbResult = loginUserAccountMapper.updateByPrimaryKeySelective(updateBean);
	// if (dbResult > 0) {
	// dbLoginUserAccount.setVersion(dbLoginUserAccount.getVersion() + 1);
	// }
	// return dbResult > 0 ? true : false;
	// }
	//
	// }

	private PwdErrParse parseErrCount(LoginUserAccount resultLoginUserAccount, String pwd, String uuid, String pwdTag) {
		String pwdRemark = StringUtils.isEmpty(pwdTag) ? "密码" : pwdTag;
		Date date = new Date();
		PwdErrParse pwdErrParse = new PwdErrParse();
		boolean isCacheOK = false;
		if (StringUtils.isEmpty(resultLoginUserAccount.getPwdErrTime())
				|| PWD_ERR_NONE.equals(resultLoginUserAccount.getPwdErrTime())) {
			isCacheOK = false;
		}
		else {
			isCacheOK = TimeUtils.isCacheOk(resultLoginUserAccount.getPwdErrTime(), date.getTime(),
					AppConfig.SDF_DB_TIME, AppConfig.UserPwdErrTimeLimit());
		}
		if (isCacheOK && null != resultLoginUserAccount.getPwdErrCount()
				&& resultLoginUserAccount.getPwdErrCount() >= AppConfig.UserPwdErrCountLimit()) {
			// pwdErrParse.setReturnResp(
			// ResultFactory.toNack(ResultFactory.ERR_NEED_VERIFYCODE, pwdRemark +
			// "错误次数过多，请稍后重试或找回密码"));
			long time = AppConfig.UserPwdErrTimeLimit();
			try {
				time = AppConfig.UserPwdErrTimeLimit() - date.getTime()
						+ AppConfig.SDF_DB_TIME.parse(resultLoginUserAccount.getPwdErrTime()).getTime();
			}
			catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				time = AppConfig.UserPwdErrTimeLimit();
			}
			pwdErrParse.setReturnResp(ResultFactory.toNack(ResultFactory.ERR_NEED_VERIFYCODE,
					pwdRemark + "错误次数过多，" + parseErrTimeRespnseToString(time)));

			return pwdErrParse;
		}
		// 验证密码错误次数
		if (!EncryptConfig.decryptPWD(resultLoginUserAccount.getLoginPwd()).equals(pwd)) {
			if (isCacheOK) {
				int pwdErrCount = null == resultLoginUserAccount.getPwdErrCount() ? 1
						: resultLoginUserAccount.getPwdErrCount() + 1;
				pwdErrParse.setValid(false);
				pwdErrParse.setPwdErrCount(pwdErrCount);
				pwdErrParse.setPwdErrTime(null);
			}
			else {
				pwdErrParse.setValid(false);
				pwdErrParse.setPwdErrCount(1);
				pwdErrParse.setPwdErrTime(AppConfig.SDF_DB_TIME.format(date));
			}
			LoginUserAccount updateBean = new LoginUserAccount();
			updateBean.setLoginId(resultLoginUserAccount.getLoginId());
			updateBean.setPwdErrCount(pwdErrParse.getPwdErrCount());
			updateBean.setPwdErrTime(pwdErrParse.getPwdErrTime());
			updateBean.setVersion(resultLoginUserAccount.getVersion());
			int dbResult = loginUserAccountMapper.updateByPrimaryKeySelective(updateBean);
			if (dbResult > 0) {
				resultLoginUserAccount.setVersion(resultLoginUserAccount.getVersion() + 1);
				pwdErrParse.setReturnResp(ResultFactory.toNack(ResultFactory.ERR_PWD_WRONG, pwdRemark + "错误"));
			}
			else {
				pwdErrParse.setReturnResp(ResultFactory.toNackDB());
			}
		}
		else {
			pwdErrParse.setPwdErrCount(0);
			pwdErrParse.setPwdErrTime(AppConfig.SDF_DB_TIME.format(date));
			LoginUserAccount updateBean = new LoginUserAccount();
			updateBean.setLoginId(resultLoginUserAccount.getLoginId());
			updateBean.setPwdErrCount(0);
			updateBean.setPwdErrTime(PWD_ERR_NONE);
			updateBean.setLastAuthUuid(uuid);
			updateBean.setLastAuthTime(pwdErrParse.getPwdErrTime());
			updateBean.setVersion(resultLoginUserAccount.getVersion());
			int dbResult = loginUserAccountMapper.updateByPrimaryKeySelective(updateBean);
			if (dbResult > 0) {
				resultLoginUserAccount.setVersion(resultLoginUserAccount.getVersion() + 1);
				pwdErrParse.setValid(true);
				return pwdErrParse;
			}
			else {
				pwdErrParse.setValid(false);
				pwdErrParse.setReturnResp(ResultFactory.toNackDB());
			}
		}

		return pwdErrParse;
	}

	public static String parseErrTimeRespnseToString(long errTime) {
		if (errTime <= 1000l * 60) {
			return "1分钟后重试或找回密码";
		}
		long timeMin = errTime / (1000l * 60);
		if (timeMin < 60l) {
			return timeMin + "分钟后重试或找回密码";
		}
		else if (timeMin % 60 < 30) {
			return timeMin / 60 + "后重试或找回密码";
		}
		else {
			return (timeMin / 60 + 1) + "后重试或找回密码";
		}
	}

	private boolean updateLoginUserAccount(LoginUserAccount dbLoginUserAccount, LoginUserAccount updateUserAccount) {

		updateUserAccount.setVersion(dbLoginUserAccount.getVersion());
		int dbResult = loginUserAccountMapper.updateByPrimaryKeySelective(updateUserAccount);
		return dbResult > 0 ? true : false;
	}
	// public boolean updateLoginUserInfo(LoginUserAccount dbLoginUserAccount, LoginUserAccount
	// updateUserAccount) {
	// updateUserAccount.setLoginId(dbLoginUserAccount.getLoginId());
	// updateUserAccount.setVersion(AppConfig.getUpdateVersion(dbLoginUserAccount.getVersion()));
	// // 创建Example
	// Example example = new Example(LoginUserAccount.class);
	// // 创建Criteria
	// Example.Criteria criteria = example.createCriteria();
	// // 添加条件
	// criteria.andEqualTo("loginId", dbLoginUserAccount.getLoginId());
	// criteria.andEqualTo("version", dbLoginUserAccount.getVersion());
	//
	// int dbResult = loginUserInfoMapper.updateByExampleSelective(updateUserAccount, example);
	// return dbResult > 0 ? true : false;
	// }

}
