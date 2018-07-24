/**
 *	@copyright wanruome-2018
 * 	@author wanruome
 * 	@create 2018年7月23日 下午4:08:21
 */
package com.newpay.webauth.dal.mapper;

import org.apache.ibatis.annotations.Param;

import com.newpay.webauth.dal.model.LoginTermInfo;

import tk.mybatis.mapper.common.Mapper;

public interface LoginTermInfoMapper extends Mapper<LoginTermInfo> {
	public LoginTermInfo queryLastLoginTermInfo(@Param("loginTermInfo") LoginTermInfo loginTermInfo);
}
