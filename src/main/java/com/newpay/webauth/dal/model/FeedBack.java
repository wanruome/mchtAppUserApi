/**
 *	@copyright wanruome-2018
 * 	@author wanruome
 * 	@create 2018年6月24日 下午8:20:38
 */
package com.newpay.webauth.dal.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import tk.mybatis.mapper.annotation.Version;

@Data
@Table(name = "TBL_FEEDBACK")
public class FeedBack {
	@Id
	@Column(name = "FEEDBACK_ID")
	private String feedBackId;
	@Column(name = "USER_ID")
	private String userId;
	@Column(name = "UUID")
	private String uuid;
	@Column(name = "APP_ID")
	private String appId;
	@Column(name = "CONTACT")
	private String contact;
	@Column(name = "FEEDBACK_TITLE")
	private String feedBackTitle;
	@Column(name = "FEEDBACK_CONTENT")
	private String feedBackContent;
	@Column(name = "STATUS")
	private Integer status;
	@Column(name = "CREATE_TIME")
	private String createTime;
	@Column(name = "UPDATE_TIME")
	private String updateTime;
	@Version
	@Column(name = "VERSION")
	private Integer version;
}
