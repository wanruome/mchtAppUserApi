/**
 *	@copyright wanruome-2018
 * 	@author wanruome
 * 	@create 2018年6月22日 下午10:21:00
 */
package com.newpay.webauth.dal.mapper;

import java.util.List;

import com.newpay.webauth.dal.model.RepayMentCity;

import tk.mybatis.mapper.common.Mapper;

public interface RepayMentCityMapper extends Mapper<RepayMentCity> {

	public List<RepayMentCity> selectAllCitys();

}
