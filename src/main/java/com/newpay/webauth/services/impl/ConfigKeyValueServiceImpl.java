/**
 *	@copyright wanruome-2018
 * 	@author wanruome
 * 	@create 2018年6月22日 上午12:18:43
 */
package com.newpay.webauth.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newpay.webauth.dal.mapper.ConfigKeyValueMapper;
import com.newpay.webauth.dal.model.ConfigKeyValue;
import com.newpay.webauth.services.ConfigKeyValueService;

@Service
public class ConfigKeyValueServiceImpl implements ConfigKeyValueService {
	@Autowired
	ConfigKeyValueMapper configKeyValueMapper;

	@Override
	public Map<String, String> loadAllConfig() {
		// TODO Auto-generated method stub
		try {
			Map<String, String> maps = new HashMap<>();
			List<ConfigKeyValue> listResult = configKeyValueMapper.selectAll();
			if (null != listResult && listResult.size() > 0) {
				for (ConfigKeyValue tmp : listResult) {
					maps.put(tmp.getConfigKey(), tmp.getConfigVal());
				}
			}
			return maps;
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

}
