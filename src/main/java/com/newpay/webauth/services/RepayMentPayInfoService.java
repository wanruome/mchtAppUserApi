/**
 *	@copyright wanruome-2018
 * 	@author wanruome
 * 	@create 2018年6月23日 下午10:14:39
 */
package com.newpay.webauth.services;

import com.newpay.webauth.dal.request.repaymentpayinfo.PayInfoFindPayPwdReqDto;
import com.newpay.webauth.dal.request.repaymentpayinfo.PayInfoNoPwdFlagRepDto;
import com.newpay.webauth.dal.request.repaymentpayinfo.PayInfoPayModifyPayPwdReqDto;
import com.newpay.webauth.dal.request.repaymentpayinfo.PayInfoPayPwdSetReqDto;
import com.newpay.webauth.dal.request.repaymentpayinfo.PayInfoVerifyPwdDto;
import com.newpay.webauth.dal.request.useraccount.UserAccountReqDto;

public interface RepayMentPayInfoService {
	public Object doGetPayInfo(UserAccountReqDto userAccountReqDto);

	public Object doSetPayPwd(PayInfoPayPwdSetReqDto payInfoPayPwdSetReqDto);

	public Object doModifyPayPwd(PayInfoPayModifyPayPwdReqDto payInfoPayModifyPayPwdReqDto);

	public Object doFindPayPwd(PayInfoFindPayPwdReqDto payInfoFindPayPwdReqDto);

	public Object doModifyNoPwdFlag(PayInfoNoPwdFlagRepDto payInfoNoPwdFlagRepDto);

	public Object doVerifyPassword(PayInfoVerifyPwdDto payInfoVerifyPwdDto);
}
