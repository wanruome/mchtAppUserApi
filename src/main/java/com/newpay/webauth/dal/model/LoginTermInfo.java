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
@Table(name = "TBL_LOGIN_TERM_INFO")
public class LoginTermInfo {
	@Id
	@Column(name = "LOGIN_SEQ")
	private String loginSeq;
	@Column(name = "LOGIN_ID")
	private String loginId;
	@Column(name = "LAT")
	private String lat;
	@Column(name = "LNG")
	private String lng;
	@Column(name = "COUNTRY")
	private String country;
	@Column(name = "PROVINCE")
	private String province;
	@Column(name = "CITY")
	private String city;
	@Column(name = "ADDRESS")
	private String address;
	@Column(name = "IP")
	private String ip;
	@Column(name = "TERM_INFO")
	private String termInfo;
	@Column(name = "CREATE_TIME")
	private String createTime;
	@Column(name = "UPDATE_TIME")
	private String updateTime;
	@Version
	@Column(name = "VERSION")
	private Integer version;
}
