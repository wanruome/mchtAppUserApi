/**
 *	@copyright wanruome-2018
 * 	@author wanruome
 * 	@create 2018年6月25日 下午1:11:25
 */
package com.newpay.webauth.dal.request.useraccount;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class UserInfoModifyOther {
	@NotEmpty
	private String userId;
	@NotEmpty
	private String appId;
	@NotEmpty
	private String tokenId;
	@NotEmpty
	private String signInfo;
	private String nickName;
	private String headImg;
	private String idCardNo;
	private String idCardName;
}
