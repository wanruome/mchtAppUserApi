/**
 *	@copyright wanruome-2018
 * 	@author wanruome
 * 	@create 2018年6月25日 下午1:08:44
 */
package com.newpay.webauth.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newpay.webauth.dal.mapper.UserInfoMapper;
import com.newpay.webauth.services.UserInfoService;

@Service
public class UserInfoServiceImpl implements UserInfoService {
	@Autowired
	UserInfoMapper userInfoMapper;
}
