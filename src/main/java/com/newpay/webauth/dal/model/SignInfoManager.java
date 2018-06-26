/**
 *	@copyright wanruome-2018
 * 	@author wanruome
 * 	@create 2018年6月25日 下午10:53:45
 */
package com.newpay.webauth.dal.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Table(name = "TBL_SIGN_INFO_MANAGER")
public class SignInfoManager {
	@Id
	@Column(name = "SIGN_INFO_VALUE")
	private String signInfoValue;
	@Column(name = "CREATE_TIME")
	private Long createTime;
}
