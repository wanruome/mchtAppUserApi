/**
 *	@copyright wanruome-2018
 * 	@author wanruome
 * 	@create 2018年6月22日 上午12:17:06
 */
package com.newpay.webauth.services;

import java.util.Map;

public interface ConfigKeyValueService {
	public Map<String, String> loadAllConfig();
}
