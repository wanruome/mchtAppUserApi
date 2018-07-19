/**
 *	@copyright wanruome-2018
 * 	@author wanruome
 * 	@create 2018年7月19日 上午10:33:55
 */
package com.newpay.webauth.dal.core;

import lombok.Data;

@Data
public class DataEncryptPrase {
	private boolean isValid = false;
	private String dataClear;
}
