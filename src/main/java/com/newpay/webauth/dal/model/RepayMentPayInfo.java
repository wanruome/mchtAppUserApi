/**
 *	@copyright wanruome-2018
 * 	@author wanruome
 * 	@create 2018年6月23日 下午10:07:51
 */
package com.newpay.webauth.dal.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import tk.mybatis.mapper.annotation.Version;

@Data
@Table(name = "TBL_REPAYMENT_PAYINFO")
public class RepayMentPayInfo {
	@Id
	@Column(name = "USER_ID")
	private String userId;
	@Column(name = "PAY_PWD")
	private String payPwd;
	@Column(name = "PWD_ERR_COUNT")
	private Integer pwdErrCount;
	@Column(name = "PWD_ERR_TIME")
	private String pwdErrTime;
	@Column(name = "NO_PWD_FLAG")
	private Integer noPwdFlag;
	@Column(name = "NO_PWD_AMOUNT")
	private Integer noPwdAmount;
	@Column(name = "LIMIT_AMOUNT")
	private Integer limitAmount;
	@Column(name = "UPDATE_TIME")
	private String updateTime;
	@Version
	@Column(name = "VERSION")
	private Integer version;
}
