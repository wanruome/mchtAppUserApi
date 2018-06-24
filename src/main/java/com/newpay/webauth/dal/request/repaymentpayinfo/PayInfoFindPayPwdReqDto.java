/**
 *	@copyright wanruome-2018
 * 	@author wanruome
 * 	@create 2018年6月11日 上午12:04:43
 */
package com.newpay.webauth.dal.request.repaymentpayinfo;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class PayInfoFindPayPwdReqDto {
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
	private String newPayPwd;
	@NotEmpty
	private String newPayPwdEncrypt;
	@NotEmpty
	private String msgVerifyCode;

}
