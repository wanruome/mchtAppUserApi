/**
 *	@copyright wanruome-2018
 * 	@author wanruome
 * 	@create 2018年5月24日 下午2:24:59
 */
package com.newpay.webauth.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.newpay.webauth.dal.request.repayment.RepaymentBindCardReqDto;
import com.newpay.webauth.dal.request.repayment.RepaymentUnBindCardReqDto;
import com.newpay.webauth.dal.response.ResultFactory;
import com.newpay.webauth.services.RepaymentService;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/app/repayment")
public class RepaymentController {
	@Autowired
	RepaymentService repaymentService;

	@ApiOperation("银联绑定银行卡调用")
	@PostMapping("/doBindCard")
	public Object doBindCard(@Valid @RequestBody RepaymentBindCardReqDto repaymentBindCardReqDto,
			BindingResult bindingResult) {
		if (null == bindingResult || bindingResult.hasErrors()) {
			return ResultFactory.toNackPARAM();
		}

		return repaymentService.doBindCard(repaymentBindCardReqDto);
	}

	@ApiOperation("解除银行卡绑定")
	@PostMapping("/doUnBindCard")
	public Object doUnBindCard(@Valid @RequestBody RepaymentUnBindCardReqDto repaymentUnBindCardReqDto,
			BindingResult bindingResult) {
		if (null == bindingResult || bindingResult.hasErrors()) {
			return ResultFactory.toNackPARAM();
		}

		return repaymentService.doUnBindCard(repaymentUnBindCardReqDto);
	}

	@ApiOperation("解除银行卡绑定")
	@PostMapping("/callBindCardResult")
	public Object callBindCardResult(@Valid @RequestBody RepaymentUnBindCardReqDto repaymentUnBindCardReqDto,
			BindingResult bindingResult) {
		if (null == bindingResult || bindingResult.hasErrors()) {
			return ResultFactory.toNackPARAM();
		}

		return repaymentService.callBindCardResult(repaymentUnBindCardReqDto.getSequenceNo());
	}

}
