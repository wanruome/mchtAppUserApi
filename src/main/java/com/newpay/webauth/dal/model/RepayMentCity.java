/**
 *	@copyright wanruome-2018
 * 	@author wanruome
 * 	@create 2018年6月22日 下午10:19:01
 */
package com.newpay.webauth.dal.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Table(name = "TBL_REPAYMENT_CITYS")
public class RepayMentCity {
	@Id
	@Column(name = "ID")
	private String id;
	@Column(name = "PROVINCE")
	private String province;
	@Column(name = "CITY")
	private String city;
	@Column(name = "CODE")
	private String code;
}
