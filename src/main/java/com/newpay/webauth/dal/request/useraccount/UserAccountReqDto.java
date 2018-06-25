/**
 *	@copyright wanruome-2018
 * 	@author wanruome
 * 	@create 2018年6月13日 上午11:27:35
 */
package com.newpay.webauth.dal.request.useraccount;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class UserAccountReqDto {
	@NotEmpty
	private String userId;
	@NotEmpty
	private String appId;
	@NotEmpty
	private String tokenId;
	@NotEmpty
	private String signInfo;
}
