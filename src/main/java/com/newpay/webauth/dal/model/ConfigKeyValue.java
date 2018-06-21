/**
 *	@copyright wanruome-2018
 * 	@author wanruome
 * 	@create 2018年6月21日 下午11:57:19
 */
package com.newpay.webauth.dal.model;

import javax.persistence.Column;
import javax.persistence.Table;

import lombok.Data;

@Data
@Table(name = "TBL_SYSTEM_CONFIG")
public class ConfigKeyValue {
	@Column(name = "CONFIG_KEY")
	private String configKey;
	@Column(name = "CONFIG_VAL")
	private String configVal;
	@Column(name = "REMARK")
	private String remark;
}
