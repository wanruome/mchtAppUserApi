/**
 *	@copyright wanruome-2018
 * 	@author wanruome
 * 	@create 2018年6月25日 上午10:57:35
 */
package com.newpay.webauth.dal.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import tk.mybatis.mapper.annotation.Version;

@Data
@Table(name = "TBL_MANAGER_TOKEN")
public class ManagerToken {
	@Id
	@Column(name = "MANAGER")
	private String manager;
	@Column(name = "AUTH_TOKEN")
	private String authToken;
	@Column(name = "CREATE_TIME")
	private String createTime;
	@Column(name = "UPDATE_TIME")
	private String updateTime;
	@Version
	@Column(name = "VERSION")
	private Integer version;
}
