/**
 *	@copyright wanruome-2018
 * 	@author wanruome
 * 	@create 2018年6月25日 下午12:00:24
 */
package com.newpay.webauth.dal.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import tk.mybatis.mapper.annotation.Version;

@Table(name = "TBL_USER_INFO")
public class UserInfo {
	@Id
	@Column(name = "USER_ID")
	private String userId;
	@Column(name = "NICK_NAME")
	private String nickName;
	@Column(name = "HEADIMG")
	private String headImg;
	@Column(name = "IDNAME")
	private String idName;
	@Column(name = "IDCARD")
	private String idCard;
	@Column(name = "CREATE_TIME")
	private String createTime;
	@Column(name = "UPDATE_TIME")
	private String updateTime;
	@Version
	@Column(name = "VERSION")
	private Integer version;
}
