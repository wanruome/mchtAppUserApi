/**
 *	@copyright wanruome-2018
 * 	@author wanruome
 * 	@create 2018年6月25日 上午10:54:53
 */
package com.newpay.webauth.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.newpay.webauth.dal.response.ResultFactory;
import com.newpay.webauth.services.ManagerTokenSerice;
import com.ruomm.base.tools.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("app/systemConfig")
public class SystemConfigController {
	@Autowired
	ManagerTokenSerice managerTokenSerice;

	@GetMapping("reload")
	public Object getFile(@RequestParam Map<String, String> requestParam) {
		String managerToken = requestParam.get("token");
		if (StringUtils.isEmpty(managerToken)) {
			return ResultFactory.toNackCORE("需要填写授权码");
		}
		return managerTokenSerice.resetSysConfigByTokenID("sysreload", managerToken);

	}
}
