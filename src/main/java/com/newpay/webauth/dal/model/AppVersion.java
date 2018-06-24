/**
 *	@copyright wanruome-2018
 * 	@author wanruome
 * 	@create 2018年6月24日 下午9:09:00
 */
package com.newpay.webauth.dal.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import tk.mybatis.mapper.annotation.Version;

@Data
@Table(name = "TBL_APP_VERSION")
public class AppVersion {
	@Id
	@Column(name = "APP_NAME")
	private String appName;
	@Id
	@Column(name = "APP_TYPE")
	private String appType;
	@Column(name = "APP_VERSION")
	private String appVersion;
	@Column(name = "DOWN_RUL")
	private String downUrl;
	@Column(name = "UPDATE_STATUS")
	private Integer updateStatus;
	@Column(name = "UPDATE_TIME")
	private String updateTime;
	@Column(name = "CREATE_TIME")
	private String createTime;
	@Version
	@Column(name = "VERSION")
	private Integer version;
}
