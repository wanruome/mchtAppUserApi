/**
 *	@copyright wanruome-2018
 * 	@author wanruome
 * 	@create 2018年6月24日 下午8:25:42
 */
package com.newpay.webauth.services;

import com.newpay.webauth.dal.request.feedback.FeedBackReqDto;

public interface FeedBackService {
	public Object doFeedBack(FeedBackReqDto feedBackReqDto);

}
