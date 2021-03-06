/**
 *	@copyright wanruome-2018
 * 	@author wanruome
 * 	@create 2018年6月10日 下午3:09:24
 */
package com.newpay.webauth.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.newpay.webauth.config.EncryptConfig;
import com.newpay.webauth.dal.core.PwdRequestParse;
import com.newpay.webauth.dal.core.PwdRuleParse;
import com.newpay.webauth.dal.request.useraccount.UserAccountReqDto;
import com.newpay.webauth.dal.request.useraccount.UserInfoFindPwd;
import com.newpay.webauth.dal.request.useraccount.UserInfoLoginReqDto;
import com.newpay.webauth.dal.request.useraccount.UserInfoLogout;
import com.newpay.webauth.dal.request.useraccount.UserInfoModifyEmail;
import com.newpay.webauth.dal.request.useraccount.UserInfoModifyMobie;
import com.newpay.webauth.dal.request.useraccount.UserInfoModifyName;
import com.newpay.webauth.dal.request.useraccount.UserInfoModifyOther;
import com.newpay.webauth.dal.request.useraccount.UserInfoModifyPwd;
import com.newpay.webauth.dal.request.useraccount.UserInfoRegisterReqDto;
import com.newpay.webauth.dal.request.useraccount.UserInfoVerifyPwdDto;
import com.newpay.webauth.dal.response.ResultFactory;
import com.newpay.webauth.services.PwdService;
import com.newpay.webauth.services.UserAccountService;
import com.ruomm.base.tools.IDCardUtils;
import com.ruomm.base.tools.IPUtils;
import com.ruomm.base.tools.StringUtils;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/app/userAccount")

public class UserAccoutController {
	@Autowired
	UserAccountService userAccountService;
	@Autowired
	PwdService pwdService;

	@ApiOperation("用户注册")
	@PostMapping("/doRegister")
	public Object doRegister(@Valid @RequestBody UserInfoRegisterReqDto userInfoRegister, BindingResult bindingResult) {
		if (null == bindingResult || bindingResult.hasErrors()) {
			return ResultFactory.toNackPARAM();
		}
		// if (StringUtils.isBlank(userInfoRegister.getPwdEncrypt())) {
		// userInfoRegister.setPwdEncrypt(AppConfig.UserPwdEncryptDefault);
		// }
		if (!StringUtils.isEmpty(userInfoRegister.getIdCardName())) {
			if (userInfoRegister.getIdCardName().length() > 10) {
				return ResultFactory.toNackPARAM("真实姓名不能超过10字符");
			}
		}
		if (!StringUtils.isEmpty(userInfoRegister.getIdCardNo())) {
			boolean idFlag = false;
			try {
				idFlag = IDCardUtils.isIDCardValidate(userInfoRegister.getIdCardNo());
			}
			catch (Exception e) {
				e.printStackTrace();
				idFlag = false;
			}
			if (!idFlag) {
				return ResultFactory.toNackPARAM("身份证号有错误");
			}
		}
		if (!StringUtils.isEmpty(userInfoRegister.getNickName())) {
			if (userInfoRegister.getNickName().length() > 30) {
				return ResultFactory.toNackPARAM("昵称不能超过30位");
			}
		}
		if (!StringUtils.isEmpty(userInfoRegister.getHeadImg())) {
			if (userInfoRegister.getHeadImg().length() > 100) {
				return ResultFactory.toNackPARAM("头像路径太长了");
			}
		}
		PwdRequestParse pwdParse = pwdService.parseRequsetPwd(userInfoRegister.getPwd(),
				userInfoRegister.getPwdEncrypt(), userInfoRegister.getUuid());
		if (!pwdParse.isValid()) {
			return pwdParse.getReturnResp();
		}
		PwdRuleParse pwdRuleParse = pwdService.parseAccountPwdRule(pwdParse.getPwdClear());
		if (!pwdRuleParse.isValid()) {
			return pwdRuleParse.getReturnResp();
		}
		// 密码加密存储
		String pwdEncrypt = EncryptConfig.encryptPWD(pwdParse.getPwdParse());
		if (StringUtils.isEmpty(pwdEncrypt)) {
			ResultFactory.toNack(ResultFactory.ERR_PWD_PARSE, null);
		}
		userInfoRegister.setPwd(pwdEncrypt);
		return userAccountService.doRegister(userInfoRegister);

	}

	@ApiOperation("用户登录")
	@PostMapping("/doLogin")
	public Object doLogin(HttpServletRequest request, @Valid @RequestBody UserInfoLoginReqDto userInfoLoginReqDto,
			BindingResult bindingResult) {
		if (null == bindingResult || bindingResult.hasErrors()) {
			return ResultFactory.toNackPARAM();
		}
		String ip = IPUtils.getRequestIP(request);
		userInfoLoginReqDto.setIp(ip);
		PwdRequestParse pwdParse = pwdService.parseRequsetPwd(userInfoLoginReqDto.getPwd(),
				userInfoLoginReqDto.getPwdEncrypt(), userInfoLoginReqDto.getUuid());
		if (!pwdParse.isValid()) {
			return pwdParse.getReturnResp();
		}
		userInfoLoginReqDto.setPwd(pwdParse.getPwdParse());
		return userAccountService.doLogin(userInfoLoginReqDto);
	}

	@ApiOperation("修改密码")
	@PostMapping("/doModifyPwd")
	public Object doModifyPwd(@Valid @RequestBody UserInfoModifyPwd userInfoModifyPwd, BindingResult bindingResult) {
		if (null == bindingResult || bindingResult.hasErrors()) {
			return ResultFactory.toNackPARAM();
		}
		PwdRequestParse oldPwdParse = pwdService.parseRequsetPwd(userInfoModifyPwd.getOldPwd(),
				userInfoModifyPwd.getOldPwdEncrypt(), userInfoModifyPwd.getUuid());
		PwdRequestParse newPwdParse = pwdService.parseRequsetPwd(userInfoModifyPwd.getNewPwd(),
				userInfoModifyPwd.getNewPwdEncrypt(), userInfoModifyPwd.getUuid());
		if (!newPwdParse.isValid()) {
			return newPwdParse.getReturnResp();
		}
		if (!oldPwdParse.isValid()) {
			return oldPwdParse.getReturnResp();
		}
		if (newPwdParse.getPwdParse().equals(oldPwdParse.getPwdParse())) {
			return ResultFactory.toNackCORE("新密码不能和旧密码一致");
		}
		PwdRuleParse pwdRuleParse = pwdService.parseAccountPwdRule(newPwdParse.getPwdClear());
		if (!pwdRuleParse.isValid()) {
			return pwdRuleParse.getReturnResp();
		}
		userInfoModifyPwd.setNewPwd(newPwdParse.getPwdParse());
		userInfoModifyPwd.setOldPwd(oldPwdParse.getPwdParse());
		return userAccountService.doModifyPwd(userInfoModifyPwd);
	}

	@ApiOperation("找回密码")
	@PostMapping("/doFindPwd")
	public Object doFindPwd(@Valid @RequestBody UserInfoFindPwd userInfoFindPwd, BindingResult bindingResult) {
		if (null == bindingResult || bindingResult.hasErrors()) {
			return ResultFactory.toNackPARAM();
		}
		PwdRequestParse newPwdParse = pwdService.parseRequsetPwd(userInfoFindPwd.getNewPwd(),
				userInfoFindPwd.getNewPwdEncrypt(), userInfoFindPwd.getUuid());
		if (!newPwdParse.isValid()) {
			return newPwdParse.getReturnResp();
		}
		PwdRuleParse pwdRuleParse = pwdService.parseAccountPwdRule(newPwdParse.getPwdClear());
		if (!pwdRuleParse.isValid()) {
			return pwdRuleParse.getReturnResp();
		}
		userInfoFindPwd.setNewPwd(newPwdParse.getPwdParse());
		return userAccountService.doFindPwd(userInfoFindPwd);
	}

	@ApiOperation("修改手机号")
	@PostMapping("/doModifyMobile")
	public Object doModifyMobile(@Valid @RequestBody UserInfoModifyMobie userInfoModifyMobie,
			BindingResult bindingResult) {
		if (null == bindingResult || bindingResult.hasErrors()) {
			return ResultFactory.toNackPARAM();
		}
		return userAccountService.doModifyMobile(userInfoModifyMobie);
	}

	@ApiOperation("修改邮箱号码")
	@PostMapping("/doModifyEmail")
	public Object doModifyEmail(@Valid @RequestBody UserInfoModifyEmail userInfoModifyEmail,
			BindingResult bindingResult) {
		if (null == bindingResult || bindingResult.hasErrors()) {
			return ResultFactory.toNackPARAM();
		}
		return userAccountService.doModifyEmail(userInfoModifyEmail);
	}

	@ApiOperation("修改登录名")
	@PostMapping("/doModifyName")
	public Object doModifyName(@Valid @RequestBody UserInfoModifyName userInfoModifyName, BindingResult bindingResult) {
		if (null == bindingResult || bindingResult.hasErrors()) {
			return ResultFactory.toNackPARAM();
		}
		return userAccountService.doModifyName(userInfoModifyName);
	}

	@ApiOperation("用户登出")
	@PostMapping("/doLogout")
	public Object doLogout(@Valid @RequestBody UserInfoLogout userInfoLogout, BindingResult bindingResult) {
		if (null == bindingResult || bindingResult.hasErrors()) {
			return ResultFactory.toNackPARAM();
		}
		return userAccountService.doLogout(userInfoLogout);

	}

	@ApiOperation("修改用户其他信息")
	@PostMapping("/doModifyUserInfo")
	public Object doModifyUserInfo(@Valid @RequestBody UserInfoModifyOther userInfoModifyOther,
			BindingResult bindingResult) {
		if (null == bindingResult || bindingResult.hasErrors()) {
			return ResultFactory.toNackPARAM();
		}
		// if (StringUtils.isBlank(userInfoRegister.getPwdEncrypt())) {
		// userInfoRegister.setPwdEncrypt(AppConfig.UserPwdEncryptDefault);
		// }
		if (!StringUtils.isEmpty(userInfoModifyOther.getIdCardName())) {
			if (userInfoModifyOther.getIdCardName().length() > 10) {
				return ResultFactory.toNackPARAM("真实姓名不能超过10字符");
			}
		}
		if (!StringUtils.isEmpty(userInfoModifyOther.getIdCardNo())) {
			boolean idFlag = false;
			try {
				idFlag = IDCardUtils.isIDCardValidate(userInfoModifyOther.getIdCardNo());
			}
			catch (Exception e) {
				e.printStackTrace();
				idFlag = false;
			}
			if (!idFlag) {
				return ResultFactory.toNackPARAM("身份证号有错误");
			}
		}
		if (!StringUtils.isEmpty(userInfoModifyOther.getNickName())) {
			if (userInfoModifyOther.getNickName().length() > 30) {
				return ResultFactory.toNackPARAM("昵称不能超过30位");
			}
		}
		if (!StringUtils.isEmpty(userInfoModifyOther.getHeadImg())) {
			if (userInfoModifyOther.getHeadImg().length() > 100) {
				return ResultFactory.toNackPARAM("头像路径太长了");
			}
		}
		return userAccountService.doModifyUserInfo(userInfoModifyOther);
	}

	@ApiOperation("修改用户其他信息")
	@PostMapping("/doGetUserInfo")
	public Object doGetUserInfo(@Valid @RequestBody UserAccountReqDto userAccountReqDto, BindingResult bindingResult) {
		if (null == bindingResult || bindingResult.hasErrors()) {
			return ResultFactory.toNackPARAM();
		}
		return userAccountService.doGetUserInfo(userAccountReqDto);
	}

	@ApiOperation("验证用户密码")
	@PostMapping("/doVerifyPassword")
	public Object doVerifyPassword(@Valid @RequestBody UserInfoVerifyPwdDto userInfoVerifyPwd,
			BindingResult bindingResult) {
		if (null == bindingResult || bindingResult.hasErrors()) {
			return ResultFactory.toNackPARAM();
		}
		PwdRequestParse oldPwdParse = pwdService.parseRequsetPwd(userInfoVerifyPwd.getPwd(),
				userInfoVerifyPwd.getPwdEncrypt(), userInfoVerifyPwd.getUuid());
		if (!oldPwdParse.isValid()) {
			return oldPwdParse.getReturnResp();
		}
		userInfoVerifyPwd.setPwd(oldPwdParse.getPwdParse());
		return userAccountService.doVerifyPassword(userInfoVerifyPwd);
	}

}
