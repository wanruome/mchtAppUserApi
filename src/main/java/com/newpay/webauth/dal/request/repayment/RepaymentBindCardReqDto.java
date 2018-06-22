/**
 *	@copyright wanruome-2018
 * 	@author wanruome
 * 	@create 2018年5月24日 下午2:43:34
 */
package com.newpay.webauth.dal.request.repayment;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class RepaymentBindCardReqDto {
	@NotEmpty
	private String userId;
	@NotEmpty
	private String appId;
	@NotEmpty
	private String tokenId;
	@NotEmpty
	private String signInfo;
	@NotEmpty
	@Length(min = 8, max = 20)
	private String accountNo;
	@NotEmpty
	@Length(min = 8, max = 15)
	private String mobileNo;
	@NotEmpty
	private String area;
	private String idcardNo;
	private String name;

}
