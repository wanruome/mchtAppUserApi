/**
 *	@copyright wanruome-2018
 * 	@author wanruome
 * 	@create 2018年5月24日 下午2:43:34
 */
package com.newpay.webauth.dal.request.repayment;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class RepaymentUnBindCardReqDto {
	@NotEmpty
	private String userId;
	@NotEmpty
	private String appId;
	// @NotEmpty
	// private String tokenId;
	// @NotEmpty
	// private String signInfo;
	@NotEmpty
	private String sequenceNo;

}
