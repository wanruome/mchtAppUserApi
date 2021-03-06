/**
 *	@copyright wanruome-2018
 * 	@author wanruome
 * 	@create 2018年6月12日 下午3:24:25
 */
package com.newpay.webauth.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.newpay.webauth.dal.request.keypair.UuidKeyPairReqDto;
import com.newpay.webauth.dal.response.ResultFactory;
import com.newpay.webauth.services.UuidKeyPairService;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/app/keypair")
public class UuidKeyPairController {
	@Autowired
	UuidKeyPairService uuidKeyPairService;

	@ApiOperation("依据UUID获取RSA公钥")
	@PostMapping("/getPublicKeyByUuid")
	public Object getPublicKeyByUuid(@Valid @RequestBody UuidKeyPairReqDto uuidKeyPairReqDto,
			BindingResult bindingResult) {
		if (null == bindingResult || bindingResult.hasErrors()) {
			return ResultFactory.toNackPARAM();
		}
		return uuidKeyPairService.getPublicKeyByUuid(uuidKeyPairReqDto);

	}

	@ApiOperation("依据UUID获取RSA公钥")
	@PostMapping("/getKeyPairForStore")
	public Object getKeyPairForStore(@Valid @RequestBody UuidKeyPairReqDto uuidKeyPairReqDto,
			BindingResult bindingResult) {
		if (null == bindingResult || bindingResult.hasErrors()) {
			return ResultFactory.toNackPARAM();
		}
		return uuidKeyPairService.getKeyPairForStore(uuidKeyPairReqDto);

	}

}
