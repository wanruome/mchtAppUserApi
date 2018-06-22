/**
 *	@copyright wanruome-2018
 * 	@author wanruome
 * 	@create 2018年6月22日 下午5:05:53
 */
package com.newpay.webauth.dal.request.repayment;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class RepaymentQueryBindCardDto {
	@NotEmpty
	private String userId;
	@NotEmpty
	private String appId;
	@NotEmpty
	private String tokenId;
	@NotEmpty
	private String signInfo;
}
