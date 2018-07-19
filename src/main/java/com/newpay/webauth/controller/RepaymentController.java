/**
 *	@copyright wanruome-2018
 * 	@author wanruome
 * 	@create 2018年5月24日 下午2:24:59
 */
package com.newpay.webauth.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.newpay.webauth.dal.core.DataEncryptPrase;
import com.newpay.webauth.dal.request.repayment.RepaymentBindCardReqDto;
import com.newpay.webauth.dal.request.repayment.RepaymentQrCodeCallDto;
import com.newpay.webauth.dal.request.repayment.RepaymentQueryBindCardDto;
import com.newpay.webauth.dal.request.repayment.RepaymentQueryCitysDto;
import com.newpay.webauth.dal.request.repayment.RepaymentQueryOrdersDto;
import com.newpay.webauth.dal.request.repayment.RepaymentUnBindCardReqDto;
import com.newpay.webauth.dal.response.ResultFactory;
import com.newpay.webauth.services.PwdService;
import com.newpay.webauth.services.RepaymentService;
import com.ruomm.base.tools.StringUtils;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/app/repayment")
public class RepaymentController {
	@Autowired
	RepaymentService repaymentService;
	@Autowired
	PwdService pwdService;

	@ApiOperation("银联绑定银行卡调用")
	@PostMapping("/doBindCard")
	public Object doBindCard(@Valid @RequestBody RepaymentBindCardReqDto repaymentBindCardReqDto,
			BindingResult bindingResult) {
		if (null == bindingResult || bindingResult.hasErrors()) {
			return ResultFactory.toNackPARAM();
		}
		DataEncryptPrase dataEncryptPrase = pwdService.parseRequsetData(repaymentBindCardReqDto.getAccountNo(),
				repaymentBindCardReqDto.getDataEncrypt(), repaymentBindCardReqDto.getUuid());
		if (null == dataEncryptPrase || !dataEncryptPrase.isValid()) {
			return ResultFactory.toNack(ResultFactory.ERR_DATA_PARSE, null);
		}
		int length = StringUtils.getLength(dataEncryptPrase.getDataClear());
		if (length < 8 || length > 20) {

			return ResultFactory.toNackPARAM("银行卡号必须8-20位");
		}
		repaymentBindCardReqDto.setAccountNo(dataEncryptPrase.getDataClear());
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

	@ApiOperation("银联绑定银行卡反馈信息处理")
	@PostMapping("/callBindCardResult")
	public Object callBindCardResult(HttpServletRequest request, HttpServletResponse response) {
		String requestStr = null;
		try {
			requestStr = IOUtils.toString(request.getInputStream(), "UTF-8");
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			requestStr = null;
		}
		JSONObject resultObject = repaymentService.callBindCardResult(requestStr);
		if (ResultFactory.isAck(resultObject)) {
			return "OK";
		}
		else {
			try {
				response.sendError(404);
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		// return repaymentService.callBindCardResult(requestStr);
	}

	@ApiOperation("申请二维码")
	@PostMapping("/doCallQrcode")
	public Object doCallQrcode(@Valid @RequestBody RepaymentQrCodeCallDto repaymentQrCodeCallDto,
			BindingResult bindingResult) {
		if (null == bindingResult || bindingResult.hasErrors()) {
			return ResultFactory.toNackPARAM();
		}

		return repaymentService.doCallQrcode(repaymentQrCodeCallDto);
	}

	@ApiOperation("查询订单数据")
	@PostMapping("/doQueryOrders")
	public Object doQueryOrders(@Valid @RequestBody RepaymentQueryOrdersDto repaymentQueryOrdersDto,
			BindingResult bindingResult) {
		if (null == bindingResult || bindingResult.hasErrors()) {
			return ResultFactory.toNackPARAM();
		}

		return repaymentService.doQueryOrders(repaymentQueryOrdersDto);
	}

	@ApiOperation("查询绑定银行卡列表")
	@PostMapping("/doQueryBindCards")
	public Object doQueryBindCards(@Valid @RequestBody RepaymentQueryBindCardDto repaymentQueryBindCardDto,
			BindingResult bindingResult) {
		if (null == bindingResult || bindingResult.hasErrors()) {
			return ResultFactory.toNackPARAM();
		}

		return repaymentService.doQueryBindCards(repaymentQueryBindCardDto);
	}

	@ApiOperation("查询城市列表")
	@PostMapping("/doQueryAllCitys")
	public Object doQueryAllCitys(@Valid @RequestBody RepaymentQueryCitysDto repaymentQueryCitysDto,
			BindingResult bindingResult) {
		if (null == bindingResult || bindingResult.hasErrors()) {
			return ResultFactory.toNackPARAM();
		}

		return repaymentService.doQueryAllCitys(repaymentQueryCitysDto);
	}

}
