/**
 *	@copyright wanruome-2018
 * 	@author wanruome
 * 	@create 2018年6月21日 下午1:39:27
 */
package com.newpay.webauth.services;

import com.alibaba.fastjson.JSONObject;
import com.newpay.webauth.dal.request.repayment.RepaymentBindCardReqDto;
import com.newpay.webauth.dal.request.repayment.RepaymentQrCodeCallDto;
import com.newpay.webauth.dal.request.repayment.RepaymentQueryBindCardDto;
import com.newpay.webauth.dal.request.repayment.RepaymentQueryCitysDto;
import com.newpay.webauth.dal.request.repayment.RepaymentQueryOrdersDto;
import com.newpay.webauth.dal.request.repayment.RepaymentUnBindCardReqDto;

public interface RepaymentService {
	public Object doBindCard(RepaymentBindCardReqDto repaymentBindCardReqDto);

	public Object doUnBindCard(RepaymentUnBindCardReqDto repaymentUnBindCardReqDto);

	public JSONObject callBindCardResult(String requestStr);

	public Object doCallQrcode(RepaymentQrCodeCallDto repaymentQrCodeCallDto);

	public Object doQueryOrders(RepaymentQueryOrdersDto repaymentQueryOrdersDto);

	public Object doQueryBindCards(RepaymentQueryBindCardDto repaymentQueryBindCardDto);

	public Object doQueryAllCitys(RepaymentQueryCitysDto repaymentQueryCitysDto);
}
