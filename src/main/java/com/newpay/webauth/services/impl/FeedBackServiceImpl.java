/**
 *	@copyright wanruome-2018
 * 	@author wanruome
 * 	@create 2018年6月24日 下午8:25:54
 */
package com.newpay.webauth.services.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newpay.webauth.config.AppConfig;
import com.newpay.webauth.dal.mapper.FeedBackMapper;
import com.newpay.webauth.dal.model.FeedBack;
import com.newpay.webauth.dal.request.feedback.FeedBackReqDto;
import com.newpay.webauth.dal.response.ResultFactory;
import com.newpay.webauth.services.DbSeqService;
import com.newpay.webauth.services.FeedBackService;

@Service
public class FeedBackServiceImpl implements FeedBackService {
	@Autowired
	private FeedBackMapper feedBackMapper;
	@Autowired
	private DbSeqService dbSeqService;

	@Override
	public Object doFeedBack(FeedBackReqDto feedBackReqDto) {
		// TODO Auto-generated method stub
		String nowTimeStr = AppConfig.SDF_DB_TIME.format(new Date());
		FeedBack updateFeedBack = new FeedBack();
		updateFeedBack.setFeedBackId(dbSeqService.getFeedBackNewPk());
		updateFeedBack.setUserId(feedBackReqDto.getUserId());
		updateFeedBack.setUuid(feedBackReqDto.getUuid());
		updateFeedBack.setAppId(feedBackReqDto.getAppId());
		updateFeedBack.setContact(feedBackReqDto.getContact());
		updateFeedBack.setFeedBackTitle(feedBackReqDto.getFeedBackTitle());
		updateFeedBack.setFeedBackContent(feedBackReqDto.getFeedBackContent());
		updateFeedBack.setStatus(1);
		updateFeedBack.setCreateTime(nowTimeStr);
		updateFeedBack.setUpdateTime(nowTimeStr);
		updateFeedBack.setVersion(1);
		int dbResult = feedBackMapper.insert(updateFeedBack);
		if (dbResult > 0) {
			return ResultFactory.toAck(null);
		}
		else {
			return ResultFactory.toNackDB();
		}
	}

}
