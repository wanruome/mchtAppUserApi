/**
 *	@copyright wanruome-2018
 * 	@author wanruome
 * 	@create 2018年6月13日 下午8:16:25
 */
package com.newpay.webauth.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.newpay.webauth.dal.request.functionmsg.MsgSendReqDto;
import com.newpay.webauth.dal.request.functionmsg.MsgTokenGetReqDto;
import com.newpay.webauth.dal.response.ResultFactory;
import com.newpay.webauth.services.MsgSendService;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("app/msg")
public class MsgSendController {
	@Autowired
	MsgSendService functionMsgService;

	@ApiOperation("发送短信")
	@PostMapping("/doMsgSend")
	public Object doMsgSend(@Valid @RequestBody MsgSendReqDto msgSendReqDto, BindingResult bindingResult) {
		if (null == bindingResult || bindingResult.hasErrors()) {
			return ResultFactory.toNackPARAM();
		}

		return functionMsgService.doSendMsg(msgSendReqDto);
	}

	@ApiOperation("获取短信验证码")
	@PostMapping("/doGetMsgToken")
	public Object doGetMsgToken(@Valid @RequestBody MsgTokenGetReqDto msgTokenGetReqDto, BindingResult bindingResult) {
		if (null == bindingResult || bindingResult.hasErrors()) {
			return ResultFactory.toNackPARAM();
		}

		return functionMsgService.doGetMsgToken(msgTokenGetReqDto);
	}

}
