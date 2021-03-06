/**
 *	@copyright wanruome-2018
 * 	@author wanruome
 * 	@create 2018年6月13日 上午11:27:35
 */
package com.newpay.webauth.dal.request.useraccount;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.ruomm.base.tools.RegexUtil;

import lombok.Data;

@Data
public class UserInfoModifyEmail {
	@NotEmpty
	private String userId;
	@NotEmpty
	private String appId;
	@NotEmpty
	private String tokenId;
	@NotEmpty
	private String signInfo;
	@NotEmpty
	private String msgVerifyCode;
	@NotEmpty
	@Length(min = 6, max = 64)
	@Pattern(regexp = RegexUtil.EMAILS)
	private String newEmail;
}
