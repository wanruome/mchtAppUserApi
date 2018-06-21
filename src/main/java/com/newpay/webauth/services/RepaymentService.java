/**
 *	@copyright wanruome-2018
 * 	@author wanruome
 * 	@create 2018年6月21日 下午1:39:27
 */
package com.newpay.webauth.services;

import com.newpay.webauth.dal.request.repayment.RepaymentBindCardReqDto;
import com.newpay.webauth.dal.request.repayment.RepaymentUnBindCardReqDto;

public interface RepaymentService {
	public Object doBindCard(RepaymentBindCardReqDto repaymentBindCardReqDto);

	public Object doUnBindCard(RepaymentUnBindCardReqDto repaymentUnBindCardReqDto);

	public Object callBindCardResult(String requestStr);
}
