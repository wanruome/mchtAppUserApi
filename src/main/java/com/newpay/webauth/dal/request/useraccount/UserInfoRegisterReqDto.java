/**
 *	@copyright wanruome-2018
 * 	@author wanruome
 * 	@create 2018年6月11日 上午12:04:43
 */
package com.newpay.webauth.dal.request.useraccount;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class UserInfoRegisterReqDto {
	@NotEmpty
	private String uuid;
	@NotEmpty
	private String uuidEncrypt;
	@NotEmpty
	private String signInfo;
	@NotEmpty
	private String account;
	@NotEmpty
	private String accountType;
	@NotEmpty
	private String pwd;
	@NotEmpty
	private String pwdEncrypt;
	@NotEmpty
	private String msgVerifyCode;
	private String name;
	private String mobie;
	private String email;
	private String idCardName;
	private String idCardNo;
	private String nickName;
	private String headImg;

}
