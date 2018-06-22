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
import tk.mybatis.mapper.annotation.Version;

@Data
@Table(name = "TBL_REPAYMENT_BANKCARD")
public class RepayMentBankCard {

	@Id
	@Column(name = "CARD_INDEX")
	private String cardIndex;
	@Column(name = "USER_ID")
	private String userId;
	@Column(name = "BANKCARD_NO")
	private String bankCardNo;
	@Column(name = "BANKCARD_MOBILE")
	private String bankCardMobile;
	@Column(name = "BANKCARD_IDCARD")
	private String bankCardIdcard;
	@Column(name = "BANKCARD_NAME")
	private String bankCardName;
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
	@Column(name = "BIND_STATUS")
	private Integer bindStatus;
	@Column(name = "CREATE_TIME")
	private String createTime;
	@Column(name = "UPDATE_TIME")
	private String updateTime;
	@Column(name = "CARD_FINGER")
	private String cardFinger;
	@Version
	@Column(name = "VERSION")
	private Integer version;

}
