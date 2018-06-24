/**
 *	@copyright wanruome-2018
 * 	@author wanruome
 * 	@create 2018年6月24日 下午8:25:22
 */
package com.newpay.webauth.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.newpay.webauth.dal.request.feedback.FeedBackReqDto;
import com.newpay.webauth.dal.response.ResultFactory;
import com.newpay.webauth.services.FeedBackService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("app/feedBack")
public class FeedBackController {
	@Autowired
	FeedBackService feedBackService;

	@ApiOperation("意见反馈")
	@PostMapping("/doFeedBack")
	public Object doFeedBack(@Valid @RequestBody FeedBackReqDto feedBackReqDto, BindingResult bindingResult) {
		if (null == bindingResult || bindingResult.hasErrors()) {
			return ResultFactory.toNackPARAM();
		}
		// boolean isParamValid = false;
		// if (StringUtils.isEmpty(feedBackReqDto.getSignInfo()) &&
		// StringUtils.isEmpty(feedBackReqDto.getUserId())
		// && StringUtils.isEmpty(feedBackReqDto.getTokenId())) {
		// isParamValid = true;
		// }
		// if (!StringUtils.isEmpty(feedBackReqDto.getSignInfo()) &&
		// !StringUtils.isEmpty(feedBackReqDto.getUserId())
		// && !StringUtils.isEmpty(feedBackReqDto.getTokenId())) {
		// isParamValid = true;
		// }
		// if(!isParamValid){
		// return ResultFactory.toNackPARAM();
		// }
		return feedBackService.doFeedBack(feedBackReqDto);
	}

}
