/**
 *	@copyright wanruome-2018
 * 	@author wanruome
 * 	@create 2018年6月24日 下午8:25:22
 */
package com.newpay.webauth.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.newpay.webauth.dal.request.appversion.AppVersionReqDto;
import com.newpay.webauth.dal.response.ResultFactory;
import com.newpay.webauth.services.AppVersionService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("app/appVersion")
public class AppVersionController {
	@Autowired
	AppVersionService appVersionService;

	@ApiOperation("意见反馈")
	@PostMapping("/doGetAppVersion")
	public Object doGetAppVersion(@Valid @RequestBody AppVersionReqDto appVersionReqDto, BindingResult bindingResult) {
		if (null == bindingResult || bindingResult.hasErrors()) {
			return ResultFactory.toNackPARAM();
		}
		if (!"1".equals(appVersionReqDto.getAppType()) && !"2".equals(appVersionReqDto.getAppType())
				&& !"3".equals(appVersionReqDto.getAppType())) {
			return ResultFactory.toNackPARAM();
		}
		return appVersionService.doGetAppVersion(appVersionReqDto);
	}

}
