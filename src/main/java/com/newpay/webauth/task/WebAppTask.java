/**
 *	@copyright wanruome-2018
 * 	@author wanruome
 * 	@create 2018年6月25日 下午10:23:04
 */
package com.newpay.webauth.task;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.newpay.webauth.config.AppConfig;
import com.newpay.webauth.dal.mapper.SignInfoManagerMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WebAppTask {
	@Autowired
	SignInfoManagerMapper signInfoManagerMapper;

	public void clearHistorySignInfo() {
		log.info("清理过期的报文签名数据库");
		Long signDeleteTime = AppConfig.RequestTimeStampOffSet();
		if (signDeleteTime < AppConfig.REQUEST_TIMESTAMP_MIN_OFFSET) {
			return;
		}
		try {
			signInfoManagerMapper.deleteAllHistoryValue(new Date().getTime() - signDeleteTime * 2 - 1000l);
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

}
