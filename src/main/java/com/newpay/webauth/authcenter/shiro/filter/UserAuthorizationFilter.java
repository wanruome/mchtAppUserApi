/**
 *	@copyright wanruome-2018
 * 	@author wanruome
 * 	@create 2018年6月14日 下午9:23:58
 */
package com.newpay.webauth.authcenter.shiro.filter;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.newpay.webauth.aop.SystemLogThreadLocal;
import com.newpay.webauth.config.AppConfig;
import com.newpay.webauth.config.MsgFunctionConfig;
import com.newpay.webauth.config.SystemLogFunctionConfig;
import com.newpay.webauth.config.sign.SignTools;
import com.newpay.webauth.dal.core.SysLogBean;
import com.newpay.webauth.dal.mapper.LoginUserAccountMapper;
import com.newpay.webauth.dal.mapper.LoginUserTokenMapper;
import com.newpay.webauth.dal.mapper.MsgAuthInfoMapper;
import com.newpay.webauth.dal.mapper.SignInfoManagerMapper;
import com.newpay.webauth.dal.model.LoginUserAccount;
import com.newpay.webauth.dal.model.LoginUserToken;
import com.newpay.webauth.dal.model.MsgAuthInfo;
import com.newpay.webauth.dal.model.MsgFunctionInfo;
import com.newpay.webauth.dal.model.SignInfoManager;
import com.newpay.webauth.dal.model.SystemLogFunction;
import com.newpay.webauth.dal.request.useraccount.UserInfoModifyMobie;
import com.newpay.webauth.dal.response.ResultFactory;
import com.ruomm.base.tools.BaseWebUtils;
import com.ruomm.base.tools.FastJsonTools;
import com.ruomm.base.tools.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserAuthorizationFilter extends AuthorizationFilter {
	@Autowired
	LoginUserTokenMapper loginUserTokenMapper;
	@Autowired
	LoginUserAccountMapper loginUserAccountMapper;
	@Autowired
	MsgAuthInfoMapper msgAuthInfoMapper;
	@Autowired
	SignInfoManagerMapper signInfoManagerMapper;

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		log.debug("onAccessDenied");
		return processAccessDeniedFastJson(request, response);

	}

	private boolean processAccessDeniedFastJson(ServletRequest request, ServletResponse response) throws IOException {
		JSONObject jsonObject = null;
		String contentType = request.getContentType();

		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		boolean isPostJson = "POST".equals(httpServletRequest.getMethod().toUpperCase()) && null != contentType
				&& (contentType.contains("application/json") || contentType.contains("text/plain"));
		if (isPostJson) {
			try {
				jsonObject = JSON.parseObject(IOUtils.toString(request.getInputStream(), "UTF-8"));
				log.info("请求信息:" + jsonObject.toJSONString());
			}
			catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		else {
			jsonObject = null;
		}
		Map<String, String> jsonKeyMap = FastJsonTools.parseJsonKeyMap(jsonObject);
		if (null == jsonKeyMap) {
			String uri = ((HttpServletRequest) request).getRequestURI();
			String realUri = BaseWebUtils.getRealUri(uri);
			if (realUri.endsWith("app/repayment/callBindCardResult")) {
				log.debug("对于绑卡通知进行放行处理");
				return true;
			}
			else if (realUri.endsWith("app/fileCore/uploadFile")) {
				log.debug("对于上传文件放行");
				String userId = httpServletRequest.getHeader("userId");
				String appId = httpServletRequest.getHeader("appId");
				String tokenId = httpServletRequest.getHeader("tokenId");
				String signInfo = httpServletRequest.getHeader("signInfo");
				String uuid = httpServletRequest.getHeader("uuid");
				Map<String, String> map = new HashMap<String, String>();
				map.put("userId", userId);
				map.put("appId", appId);
				map.put("tokenId", tokenId);
				map.put("uuid", uuid);
				String token = getTokenById(tokenId, userId, appId);
				if (SignTools.verifySign(map, signInfo, token)) {
					log.debug("签名验证成功");
					return true;
				}
				{
					throwException(response, ResultFactory.ERR_TOKEN_INVALID);
					log.debug("签名验证失败");
					return false;
				}
			}
			else if (realUri.endsWith("app/fileCore/getFile")) {
				log.debug("对于下载文件放行");
				return true;
			}
			else if (realUri.endsWith("app/systemConfig/reload")) {
				log.debug("对于系统参数重置放行");
				return true;
			}
			else {
				throwException(response, ResultFactory.ERR_PARSE_REQUEST);
				return false;
			}
		}
		// 日志节点加入

		SystemLogFunction systemLogFunction = SystemLogFunctionConfig.getSystemLogFuntionInfoByURI(request);
		if (null != systemLogFunction) {
			log.debug("日志写入开启:" + systemLogFunction.toString());
			String userId = FastJsonTools.getStringByKey(jsonObject, jsonKeyMap, AppConfig.REQUEST_FIELD_USER_ID);
			String logKeyValue = FastJsonTools.getStringByKey(jsonObject, jsonKeyMap,
					systemLogFunction.getLogKeyFieldName());
			String appId = FastJsonTools.getStringByKey(jsonObject, jsonKeyMap, AppConfig.REQUEST_FIELD_APP_ID);
			String uuid = FastJsonTools.getStringByKey(jsonObject, jsonKeyMap, AppConfig.REQUEST_FIELD_UUID);
			SysLogBean sysLogBean = new SysLogBean();
			sysLogBean.setAppId(appId);
			sysLogBean.setUserId(userId);
			sysLogBean.setUuid(uuid);
			sysLogBean.setLogKeyValue(logKeyValue);
			sysLogBean.setFunctionId(systemLogFunction.getFunctionId());
			sysLogBean.setFunctionName(systemLogFunction.getFunctionName());
			sysLogBean.setMapping(systemLogFunction.getMapping());
			sysLogBean.setStartTime(new Date().getTime());
			if (null != systemLogFunction.getRequstLog() && systemLogFunction.getRequstLog() == 1) {
				String jsonStr = jsonObject.toJSONString();
				int length = StringUtils.getLength(jsonStr);
				if (length < 1000) {
					sysLogBean.setRequestInfo(jsonStr);
				}
			}
			SystemLogThreadLocal.setSysLogBean(sysLogBean);
		}
		// 进行短信验证码验证流程
		String verifyCode = FastJsonTools.getStringByKey(jsonObject, jsonKeyMap, AppConfig.REQUEST_FIELD_VERIFY_CODE);
		if (!StringUtils.isEmpty(verifyCode)) {
			log.debug("进行短信验证:" + verifyCode);
			MsgFunctionInfo msgFunctionInfo = MsgFunctionConfig.getMsgFuntionInfoByURI(request);
			if (null == msgFunctionInfo) {
				throwException(response, ResultFactory.ERR_MSGCODE_INVALID, "此功能不需要短信验证码");
				return false;
			}
			String msgUUID = null;
			if (msgFunctionInfo.getAuthType() == 0) {
				msgUUID = FastJsonTools.getStringByKey(jsonObject, jsonKeyMap, AppConfig.REQUEST_FIELD_UUID);
			}
			else {
				msgUUID = FastJsonTools.getStringByKey(jsonObject, jsonKeyMap, AppConfig.REQUEST_FIELD_USER_ID);
			}
			if (StringUtils.isEmpty(msgUUID)) {
				throwException(response, ResultFactory.ERR_MSGCODE_INVALID, "请求参数错误");
			}
			String nowDateStr = AppConfig.SDF_DB_TIME.format(new Date());
			MsgAuthInfo msgAuthInfo = new MsgAuthInfo();
			msgAuthInfo.setUuid(msgUUID);
			msgAuthInfo.setFunctionId(msgFunctionInfo.getFunctionId());
			MsgAuthInfo resultAuthInfo = msgAuthInfoMapper.selectByPrimaryKey(msgAuthInfo);
			if (null == resultAuthInfo || nowDateStr.compareTo(resultAuthInfo.getMsgValidTime()) >= 0) {
				throwException(response, ResultFactory.ERR_MSGCODE_INVALID, "你还没有获取短信验证码");
				return false;
			}
			else if (resultAuthInfo.getMsgStatus() != 1) {
				throwException(response, ResultFactory.ERR_MSGCODE_INVALID, "短信验证码已经失效，请重新获取");
				return false;
			}
			else {
				if (msgFunctionInfo.getAuthType() != 2) {
					msgAuthInfo.setMsgStatus(0);
					msgAuthInfo.setVersion(resultAuthInfo.getVersion());
					int dbResult = msgAuthInfoMapper.updateByPrimaryKeySelective(msgAuthInfo);
					if (dbResult <= 0) {
						throwException(response, ResultFactory.ERR_MSGCODE_INVALID, "短信验证码已经失效，请重新获取");
						return false;
					}
				}
				if (!verifyCode.equals(resultAuthInfo.getMsgCode())) {
					throwException(response, ResultFactory.ERR_MSGCODE_INVALID, "短信验证码不正确");
					return false;
				}
			}
			if (StringUtils.isEmpty(msgFunctionInfo.getVerfifyFieldName())) {
				String userId = FastJsonTools.getStringByKey(jsonObject, jsonKeyMap, AppConfig.REQUEST_FIELD_USER_ID);
				LoginUserAccount queryAccount = new LoginUserAccount();
				queryAccount.setLoginId(userId);
				LoginUserAccount resultAccount = loginUserAccountMapper.selectByPrimaryKey(queryAccount);
				if (null == resultAccount) {
					throwException(response, ResultFactory.ERR_MSGCODE_INVALID, "短信验证码不匹配");
					return false;
				}
				if (!resultAuthInfo.getMsgAddr().equals(resultAccount.getLoginMobile())
						&& !resultAuthInfo.getMsgAddr().equals(resultAccount.getLoginEmail())) {
					throwException(response, ResultFactory.ERR_MSGCODE_INVALID, "短信验证码不匹配");
					return false;
				}
			}
			else {
				String msgAddr = FastJsonTools.getStringByKey(jsonObject, jsonKeyMap,
						msgFunctionInfo.getVerfifyFieldName());
				if (!resultAuthInfo.getMsgAddr().equals(msgAddr)) {
					throwException(response, ResultFactory.ERR_MSGCODE_INVALID, "短信验证码不匹配");
					return false;
				}
			}
		}
		// 进行签名验证流程
		String signInfo = FastJsonTools.getStringByKey(jsonObject, jsonKeyMap, AppConfig.REQUEST_FIELD_SIGN_INFO);
		if (StringUtils.isEmpty(signInfo)) {
			return true;
		}
		else {
			String userId = FastJsonTools.getStringByKey(jsonObject, jsonKeyMap, AppConfig.REQUEST_FIELD_USER_ID);
			String appId = FastJsonTools.getStringByKey(jsonObject, jsonKeyMap, AppConfig.REQUEST_FIELD_APP_ID);
			String tokenId = FastJsonTools.getStringByKey(jsonObject, jsonKeyMap, AppConfig.REQUEST_FIELD_TOKEN_ID);

			if (AppConfig.RequestTimeStampOffSet() >= AppConfig.REQUEST_TIMESTAMP_MIN_OFFSET) {
				long dateTimeNow = new Date().getTime();
				String timeStamp = FastJsonTools.getStringByKey(jsonObject, jsonKeyMap,
						AppConfig.REQUEST_FIELD_TIMESTAMP);
				if (StringUtils.isEmpty(timeStamp)) {
					throwException(response, ResultFactory.ERR_PRARM, "必须上送timeStamp字段");
					return false;
				}
				Long reqTimeSkip = 0l;
				try {
					reqTimeSkip = Math.abs(Long.parseLong(timeStamp) - dateTimeNow);
				}
				catch (Exception e) {
					reqTimeSkip = -1l;
				}
				if (reqTimeSkip < 0 || reqTimeSkip > AppConfig.RequestTimeStampOffSet()) {
					throwException(response, ResultFactory.ERR_CLIENT_TIME);
					log.debug("终端时间不正确");
					return false;
				}
				//
				SignInfoManager signInfoManager = new SignInfoManager();
				signInfoManager.setSignInfoValue(signInfo);
				signInfoManager.setCreateTime(dateTimeNow);
				int dbResult = 0;
				try {
					dbResult = signInfoManagerMapper.insert(signInfoManager);
				}
				catch (Exception e) {
					e.printStackTrace();
					dbResult = 0;
				}
				if (dbResult <= 0) {
					throwException(response, ResultFactory.ERR_PRARM, "请求信息不可以重复。");
					log.debug("终端时间不正确");
					return false;
				}

			}
			String token = getTokenById(tokenId, userId, appId);
			if (SignTools.verifySign(jsonObject, token)) {
				log.debug("签名验证成功");
				return true;
			}
			{
				throwException(response, ResultFactory.ERR_TOKEN_INVALID);
				log.debug("签名验证失败");
				return false;
			}

		}
	}

	private boolean processAccessDeniedGson(ServletRequest request, ServletResponse response) throws IOException {
		JSONObject jsonObject = null;
		try {
			jsonObject = JSON.parseObject(IOUtils.toString(request.getInputStream(), "UTF-8"));
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		if (null == jsonObject) {
			throwException(response, ResultFactory.ERR_PARSE_REQUEST);
			return false;
		}
		// 进行短信验证码验证流程
		String verifyCode = jsonObject.getString(AppConfig.REQUEST_FIELD_VERIFY_CODE);
		UserInfoModifyMobie userInfoModifyMobie = jsonObject.toJavaObject(UserInfoModifyMobie.class);
		System.out.println(userInfoModifyMobie.toString());
		if (!StringUtils.isEmpty(verifyCode)) {
			MsgFunctionInfo msgFunctionInfo = MsgFunctionConfig.getMsgFuntionInfoByURI(request);
			if (null == msgFunctionInfo) {
				throwException(response, ResultFactory.ERR_MSGCODE_INVALID, "此功能不需要短信验证码");
				return false;
			}
			String msgUUID = null;
			if (msgFunctionInfo.getAuthType() == 0) {
				msgUUID = jsonObject.getString(AppConfig.REQUEST_FIELD_UUID);
			}
			else {
				msgUUID = jsonObject.getString(AppConfig.REQUEST_FIELD_USER_ID);
			}
			if (StringUtils.isEmpty(msgUUID)) {
				throwException(response, ResultFactory.ERR_MSGCODE_INVALID, "请求参数错误");
			}
			String nowDateStr = AppConfig.SDF_DB_TIME.format(new Date());
			MsgAuthInfo msgAuthInfo = new MsgAuthInfo();
			msgAuthInfo.setUuid(msgUUID);
			msgAuthInfo.setFunctionId(msgFunctionInfo.getFunctionId());
			MsgAuthInfo resultAuthInfo = msgAuthInfoMapper.selectByPrimaryKey(msgAuthInfo);
			if (null == resultAuthInfo || nowDateStr.compareTo(resultAuthInfo.getMsgValidTime()) >= 0) {
				throwException(response, ResultFactory.ERR_MSGCODE_INVALID, "你还没有获取短信验证码");
				return false;
			}
			else if (resultAuthInfo.getMsgStatus() != 1) {
				throwException(response, ResultFactory.ERR_MSGCODE_INVALID, "短信验证码已经失效，请重新获取");
				return false;
			}
			else {
				if (msgFunctionInfo.getAuthType() != 2) {
					msgAuthInfo.setMsgStatus(0);
					msgAuthInfo.setVersion(resultAuthInfo.getVersion());
					int dbResult = msgAuthInfoMapper.updateByPrimaryKeySelective(msgAuthInfo);
					if (dbResult <= 0) {
						throwException(response, ResultFactory.ERR_MSGCODE_INVALID, "短信验证码已经失效，请重新获取");
						return false;
					}
				}
				if (!verifyCode.equals(resultAuthInfo.getMsgCode())) {
					throwException(response, ResultFactory.ERR_MSGCODE_INVALID, "短信验证码不正确");
					return false;
				}
			}
			if (StringUtils.isEmpty(msgFunctionInfo.getVerfifyFieldName())) {
				String userId = jsonObject.getString(AppConfig.REQUEST_FIELD_USER_ID);
				LoginUserAccount queryAccount = new LoginUserAccount();
				queryAccount.setLoginId(userId);
				LoginUserAccount resultAccount = loginUserAccountMapper.selectByPrimaryKey(queryAccount);
				if (null == resultAccount) {
					throwException(response, ResultFactory.ERR_MSGCODE_INVALID, "短信验证码不匹配");
					return false;
				}
				if (!resultAuthInfo.getMsgAddr().equals(resultAccount.getLoginMobile())
						&& !resultAuthInfo.getMsgAddr().equals(resultAccount.getLoginEmail())) {
					throwException(response, ResultFactory.ERR_MSGCODE_INVALID, "短信验证码不匹配");
					return false;
				}
			}
			else {
				String msgAddr = jsonObject.getString(msgFunctionInfo.getVerfifyFieldName());
				if (!resultAuthInfo.getMsgAddr().equals(msgAddr)) {
					throwException(response, ResultFactory.ERR_MSGCODE_INVALID, "短信验证码不匹配");
					return false;
				}
			}
		}
		// 进行签名验证流程
		String signInfo = jsonObject.getString(AppConfig.REQUEST_FIELD_SIGN_INFO);
		if (StringUtils.isEmpty(signInfo)) {
			return true;
		}
		else {
			String userId = jsonObject.getString(AppConfig.REQUEST_FIELD_USER_ID);
			String appId = jsonObject.getString(AppConfig.REQUEST_FIELD_APP_ID);
			String tokenId = jsonObject.getString(AppConfig.REQUEST_FIELD_TOKEN_ID);
			String token = getTokenById(tokenId, userId, appId);
			if (SignTools.verifySign(jsonObject, token)) {
				return true;
			}
			{
				throwException(response, ResultFactory.ERR_TOKEN_INVALID);
				return false;
			}

		}
	}

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
		log.debug("isAccessAllowed");
		return false;

		// HttpServletRequest req = (HttpServletRequest) request;
		// User user = (User) SecurityUtils.getSubject().getPrincipal();
		// String userId = user.getUserId();
		// String token = user.getUserToken();
		// String requestToken = req.getHeader("token");
		// if (token.equals(requestToken)) {
		// return true;
		// }
		// else {
		// return false;
		// }
		// TODO Auto-generated method stub

	}

	public String getTokenById(String tokenId, String userId, String appId) {
		LoginUserToken loginUserToken = new LoginUserToken();
		loginUserToken.setTokenId(tokenId);
		// loginUserToken.setAppId(appId);
		// loginUserToken.setTokenId(tokenId);
		LoginUserToken resultUserToken = loginUserTokenMapper.selectByPrimaryKey(loginUserToken);
		if (null == resultUserToken || resultUserToken.getLoginStatus() != 1) {
			return null;
		}
		else if (!resultUserToken.getUserId().equals(userId)) {
			return null;
		}
		else if (!resultUserToken.getAppId().equals(appId)) {
			return null;
		}
		String nowTimeStr = AppConfig.SDF_DB_TIME.format(new Date());
		if (nowTimeStr.compareTo(resultUserToken.getValidTime()) > 0) {
			return null;
		}
		else {
			return resultUserToken.getToken();
		}
	}

	private void throwException(ServletResponse response, String errCode) throws IOException {
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		httpServletResponse.setHeader("Content-type", "application/json;charset=UTF-8");
		httpServletResponse.setHeader("Cache-Control", "no-cache, must-revalidate");
		JSONObject resultJson = ResultFactory.toNack(errCode, null);
		httpServletResponse.getWriter().write(resultJson.toJSONString());
	}

	private void throwException(ServletResponse response, String errCode, String errMsg) throws IOException {
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		httpServletResponse.setHeader("Content-type", "application/json;charset=UTF-8");
		httpServletResponse.setHeader("Cache-Control", "no-cache, must-revalidate");
		JSONObject resultJson = ResultFactory.toNack(errCode, errMsg);
		httpServletResponse.getWriter().write(resultJson.toJSONString());
	}

}
