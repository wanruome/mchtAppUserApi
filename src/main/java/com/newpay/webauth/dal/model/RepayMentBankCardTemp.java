/**
 *	@copyright wanruome-2018
 * 	@author wanruome
 * 	@create 2018年5月29日 下午1:32:26
 */
package com.newpay.webauth.dal.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Table(name = "TBL_REPAYMENT_BANKCARD_TEMP")
public class RepayMentBankCardTemp {

	@Id
	@Column(name = "SEQUENCE_NO")
	private String sequenceNo;
	@Column(name = "USER_ID")
	private String userId;
	@Column(name = "BANKCARD_NO")
	private String bankcardNo;
	@Column(name = "BANKCARD_MOBILE")
	private String bankcardMobile;
	@Column(name = "BANKCARD_IDCARD")
	private String bankcardIdcard;
	@Column(name = "BANKCARD_NAME")
	private String bankcardName;
	@Column(name = "AREA")
	private String area;
	@Column(name = "RESPONESE_CODE")
	private String responseCode;
	@Column(name = "RESPONSE_REMARK")
	private String responseRemark;
	@Column(name = "BANKCARD_TYPE")
	private String bankCardType;
	@Column(name = "BANKNAME")
	private String bankName;
	@Column(name = "STATUS")
	private Integer status;
	@Column(name = "CREATE_TIME")
	private String createTime;
	@Column(name = "UPDATE_TIME")
	private String updateTime;

}
