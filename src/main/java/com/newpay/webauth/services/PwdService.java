/**
 *	@copyright wanruome-2018
 * 	@author wanruome
 * 	@create 2018年6月12日 下午8:28:08
 */
package com.newpay.webauth.services;

import com.newpay.webauth.dal.core.DataEncryptPrase;
import com.newpay.webauth.dal.core.PwdRequestParse;
import com.newpay.webauth.dal.core.PwdRuleParse;

public interface PwdService {
	public PwdRuleParse parseAccountPwdRule(String pwd);

	public PwdRuleParse parsePayPwdRule(String pwd);

	public PwdRequestParse parseRequsetPwd(String pwdRequest, String pwdEncrypt, String pwdUuid);

	public DataEncryptPrase parseRequsetData(String data, String dataEncrypt, String dataUuid);
}
