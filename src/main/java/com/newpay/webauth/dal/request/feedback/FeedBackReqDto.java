/**
 *	@copyright wanruome-2018
 * 	@author wanruome
 * 	@create 2018年6月24日 下午8:27:45
 */
package com.newpay.webauth.dal.request.feedback;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class FeedBackReqDto {
	@NotEmpty
	@Length(min = 24, max = 40)
	private String uuid;
	private String uuidEncrypt;
	@NotEmpty
	private String appId;
	private String userId;
	private String tokenId;
	@NotEmpty
	private String signInfo;
	@NotEmpty
	@Length(min = 6, max = 64)
	private String contact;
	@NotEmpty
	@NotBlank
	@Length(min = 1, max = 32)
	private String feedBackTitle;
	@NotEmpty
	@NotBlank
	@Length(min = 1, max = 500)
	private String feedBackContent;
}
