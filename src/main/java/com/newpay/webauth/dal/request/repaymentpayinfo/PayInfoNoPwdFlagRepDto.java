/**
 *	@copyright wanruome-2018
 * 	@author wanruome
 * 	@create 2018年6月13日 上午11:27:35
 */
package com.newpay.webauth.dal.request.repaymentpayinfo;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class PayInfoNoPwdFlagRepDto {
	@NotEmpty
	private String uuid;
	@NotEmpty
	private String userId;
	@NotEmpty
	private String appId;
	@NotEmpty
	private String tokenId;
	@NotEmpty
	private String signInfo;
	@NotEmpty
	private String noPwdFlag;

	private String payPwd;

	private String payPwdEncrypt;

	private String pwd;

	private String pwdEncrypt;
}
