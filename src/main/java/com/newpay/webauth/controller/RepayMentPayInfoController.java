/**
 *	@copyright wanruome-2018
 * 	@author wanruome
 * 	@create 2018年6月23日 下午10:13:47
 */
package com.newpay.webauth.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.newpay.webauth.dal.core.PwdRequestParse;
import com.newpay.webauth.dal.core.PwdRuleParse;
import com.newpay.webauth.dal.request.repaymentpayinfo.PayInfoFindPayPwdReqDto;
import com.newpay.webauth.dal.request.repaymentpayinfo.PayInfoNoPwdFlagRepDto;
import com.newpay.webauth.dal.request.repaymentpayinfo.PayInfoPayModifyPayPwdReqDto;
import com.newpay.webauth.dal.request.repaymentpayinfo.PayInfoPayPwdSetReqDto;
import com.newpay.webauth.dal.request.useraccount.UserAccountReqDto;
import com.newpay.webauth.dal.response.ResultFactory;
import com.newpay.webauth.services.PwdService;
import com.newpay.webauth.services.RepayMentPayInfoService;
import com.ruomm.base.tools.StringUtils;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/app/repaymentPayInfo")
public class RepayMentPayInfoController {
	@Autowired
	RepayMentPayInfoService repayMentPayInfoService;
	@Autowired
	PwdService pwdService;

	@ApiOperation("获取支付信息")
	@PostMapping("/doGetPayInfo")
	public Object doGetPayInfo(@Valid @RequestBody UserAccountReqDto userAccountReqDto, BindingResult bindingResult) {
		if (null == bindingResult || bindingResult.hasErrors()) {
			return ResultFactory.toNackPARAM();
		}

		return repayMentPayInfoService.doGetPayInfo(userAccountReqDto);
	}

	@ApiOperation("设置支付密码")
	@PostMapping("/doSetPayPwd")
	public Object doSetPayPwd(@Valid @RequestBody PayInfoPayPwdSetReqDto payInfoPayPwdSetReqDto,
			BindingResult bindingResult) {
		if (null == bindingResult || bindingResult.hasErrors()) {
			return ResultFactory.toNackPARAM();
		}
		PwdRequestParse pwdRequestParse = pwdService.parseRequsetPwd(payInfoPayPwdSetReqDto.getPayPwd(),
				payInfoPayPwdSetReqDto.getPayPwdEncrypt(), payInfoPayPwdSetReqDto.getUuid());
		if (!pwdRequestParse.isValid()) {
			return pwdRequestParse.getReturnResp();
		}
		PwdRuleParse pwdRuleParse = pwdService.parsePayPwdRule(pwdRequestParse.getPwdClear());
		if (!pwdRuleParse.isValid()) {
			return pwdRuleParse.getReturnResp();
		}
		payInfoPayPwdSetReqDto.setPayPwd(pwdRequestParse.getPwdParse());
		return repayMentPayInfoService.doSetPayPwd(payInfoPayPwdSetReqDto);
	}

	@ApiOperation("修改支付密码")
	@PostMapping("/doModifyPayPwd")
	public Object doModifyPayPwd(@Valid @RequestBody PayInfoPayModifyPayPwdReqDto payInfoPayModifyPayPwdReqDto,
			BindingResult bindingResult) {
		if (null == bindingResult || bindingResult.hasErrors()) {
			return ResultFactory.toNackPARAM();
		}
		PwdRequestParse oldPwdParse = pwdService.parseRequsetPwd(payInfoPayModifyPayPwdReqDto.getOldPayPwd(),
				payInfoPayModifyPayPwdReqDto.getOldPayPwdEncrypt(), payInfoPayModifyPayPwdReqDto.getUuid());
		PwdRequestParse newPwdParse = pwdService.parseRequsetPwd(payInfoPayModifyPayPwdReqDto.getNewPayPwd(),
				payInfoPayModifyPayPwdReqDto.getNewPayPwdEncrypt(), payInfoPayModifyPayPwdReqDto.getUuid());
		if (!newPwdParse.isValid()) {
			return newPwdParse.getReturnResp();
		}
		if (!oldPwdParse.isValid()) {
			return newPwdParse.getReturnResp();
		}
		if (newPwdParse.getPwdParse().equals(oldPwdParse.getPwdParse())) {
			return ResultFactory.toNackCORE("新支付密码不能和旧支付密码一致");
		}
		PwdRuleParse pwdRuleParse = pwdService.parsePayPwdRule(newPwdParse.getPwdClear());
		if (!pwdRuleParse.isValid()) {
			return pwdRuleParse.getReturnResp();
		}
		payInfoPayModifyPayPwdReqDto.setNewPayPwd(newPwdParse.getPwdParse());
		payInfoPayModifyPayPwdReqDto.setOldPayPwd(oldPwdParse.getPwdParse());
		return repayMentPayInfoService.doModifyPayPwd(payInfoPayModifyPayPwdReqDto);
	}

	@ApiOperation("找回支付密码")
	@PostMapping("/doFindPayPwd")
	public Object doFindPayPwd(@Valid @RequestBody PayInfoFindPayPwdReqDto payInfoFindPayPwdReqDto,
			BindingResult bindingResult) {
		if (null == bindingResult || bindingResult.hasErrors()) {
			return ResultFactory.toNackPARAM();
		}
		if (null == bindingResult || bindingResult.hasErrors()) {
			return ResultFactory.toNackPARAM();
		}
		PwdRequestParse newPwdParse = pwdService.parseRequsetPwd(payInfoFindPayPwdReqDto.getNewPayPwd(),
				payInfoFindPayPwdReqDto.getNewPayPwdEncrypt(), payInfoFindPayPwdReqDto.getUuid());
		if (!newPwdParse.isValid()) {
			return newPwdParse.getReturnResp();
		}
		PwdRuleParse pwdRuleParse = pwdService.parsePayPwdRule(newPwdParse.getPwdClear());
		if (!pwdRuleParse.isValid()) {
			return pwdRuleParse.getReturnResp();
		}
		payInfoFindPayPwdReqDto.setNewPayPwd(newPwdParse.getPwdParse());
		return repayMentPayInfoService.doFindPayPwd(payInfoFindPayPwdReqDto);
	}

	@ApiOperation("修改免密支付状态")
	@PostMapping("/doModifyNoPwdFlag")
	public Object doModifyNoPwdFlag(@Valid @RequestBody PayInfoNoPwdFlagRepDto payInfoNoPwdFlagRepDto,
			BindingResult bindingResult) {
		if (null == bindingResult || bindingResult.hasErrors()) {
			return ResultFactory.toNackPARAM();
		}
		boolean noPwdFlagValid = "0".equals(payInfoNoPwdFlagRepDto.getNoPwdFlag())
				|| "1".equals(payInfoNoPwdFlagRepDto.getNoPwdFlag());
		if (!noPwdFlagValid) {
			return ResultFactory.toNackPARAM();
		}
		if (!StringUtils.isEmpty(payInfoNoPwdFlagRepDto.getPayPwd())) {
			PwdRequestParse newPwdParse = pwdService.parseRequsetPwd(payInfoNoPwdFlagRepDto.getPayPwd(),
					payInfoNoPwdFlagRepDto.getPayPwdEncrypt(), payInfoNoPwdFlagRepDto.getUuid());
			if (!newPwdParse.isValid()) {
				return newPwdParse.getReturnResp();
			}
			PwdRuleParse pwdRuleParse = pwdService.parsePayPwdRule(newPwdParse.getPwdClear());
			if (!pwdRuleParse.isValid()) {
				return pwdRuleParse.getReturnResp();
			}
			payInfoNoPwdFlagRepDto.setPayPwd(newPwdParse.getPwdParse());
		}
		return repayMentPayInfoService.doModifyNoPwdFlag(payInfoNoPwdFlagRepDto);
	}
}
