/**
 *	@copyright wanruome-2018
 * 	@author wanruome
 * 	@create 2018年6月25日 下午10:57:01
 */
package com.newpay.webauth.dal.mapper;

import org.apache.ibatis.annotations.Param;

import com.newpay.webauth.dal.model.SignInfoManager;

import tk.mybatis.mapper.common.Mapper;

public interface SignInfoManagerMapper extends Mapper<SignInfoManager> {

	public int deleteAllHistoryValue(@Param("createTime") long createTime);

}
