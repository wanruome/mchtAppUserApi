/**
 *	@copyright wanruome-2018
 * 	@author wanruome
 * 	@create 2018年6月25日 上午11:03:28
 */
package com.newpay.webauth.services.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.mchtApi.util.repayment.util.RepayMentConstant;
import com.newpay.webauth.config.AppConfig;
import com.newpay.webauth.config.ConfigUtil;
import com.newpay.webauth.config.MsgFunctionConfig;
import com.newpay.webauth.config.SystemLogFunctionConfig;
import com.newpay.webauth.dal.mapper.ManagerTokenMapper;
import com.newpay.webauth.dal.model.ManagerToken;
import com.newpay.webauth.dal.response.ResultFactory;
import com.newpay.webauth.services.ManagerTokenSerice;
import com.ruomm.base.tools.TokenUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ManagerTokenSericeImpl implements ManagerTokenSerice {
	@Autowired
	ManagerTokenMapper managerTokenMapper;

	@Override
	public Object resetSysConfigByTokenID(String manager, String token) {
		ManagerToken queryToken = new ManagerToken();
		queryToken.setManager(manager);
		ManagerToken resultToken = managerTokenMapper.selectByPrimaryKey(queryToken);
		log.info("resultToken:", resultToken);
		ManagerToken updateToken = new ManagerToken();
		updateToken.setManager(manager);
		updateToken.setAuthToken(TokenUtil.generateSysreloadToken());
		String nowTimeStr = AppConfig.SDF_DB_TIME.format(new Date());
		int dbResult = 0;
		if (null == resultToken) {
			updateToken.setCreateTime(nowTimeStr);
			updateToken.setUpdateTime(nowTimeStr);
			updateToken.setVersion(1);
			dbResult = managerTokenMapper.insert(updateToken);
		}
		else {
			updateToken.setUpdateTime(nowTimeStr);
			updateToken.setVersion(resultToken.getVersion());
			dbResult = managerTokenMapper.updateByPrimaryKeySelective(updateToken);
		}
		if (dbResult > 0) {
			if (null != resultToken && token.equals(resultToken.getAuthToken())) {
				ConfigUtil.forceLoadProperty();
				RepayMentConstant.forceLoadProperty();
				SystemLogFunctionConfig.forceLoad();
				MsgFunctionConfig.forceLoad();
				return ResultFactory.toAck(null);
			}
			else {
				return ResultFactory.toNackCORE("授权码过期");
			}

		}
		else {
			return ResultFactory.toNackCORE("授权码过期");
		}
	}
}
