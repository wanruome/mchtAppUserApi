/**
 *	@copyright wanruome-2018
 * 	@author wanruome
 * 	@create 2018年6月24日 下午9:15:18
 */
package com.newpay.webauth.dal.request.appversion;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class AppVersionReqDto {
	@NotEmpty
	@Length(max = 16)
	private String appName;
	@NotEmpty
	private String appType;
	@Length(max = 16)
	@NotEmpty
	private String appVersion;
}
