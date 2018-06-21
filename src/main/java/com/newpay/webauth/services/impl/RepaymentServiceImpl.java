/**
 *	@copyright wanruome-2018
 * 	@author wanruome
 * 	@create 2018年6月21日 下午1:40:09
 */
package com.newpay.webauth.services.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.base.mchtApi.util.repayment.util.RepayMentConfig;
import com.base.mchtApi.util.repayment.util.RepayMentConstant;
import com.base.mchtApi.util.repayment.util.ServiceTool;
import com.base.mchtApi.util.repayment.util.http.Httper;
import com.base.mchtApi.util.repayment.util.util.Strings;
import com.newpay.webauth.dal.mapper.RepayMentBankCardMapper;
import com.newpay.webauth.dal.model.RepayMentBankCard;
import com.newpay.webauth.dal.request.repayment.RepaymentBindCardReqDto;
import com.newpay.webauth.dal.request.repayment.RepaymentUnBindCardReqDto;
import com.newpay.webauth.dal.response.ResultFactory;
import com.newpay.webauth.services.RepaymentService;
import com.ruomm.base.tools.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RepaymentServiceImpl implements RepaymentService {
	private final static int YUFUAPI_CONNECT_TIME = 5;
	private final static int YUFUAPI_READ_TIME = 60;
	private final static String[] reqSignFields890001 = { "serviceCode", "processCode", "merchantNo", "sequenceNo",
			"accountNo", "mobileNo", "idcardNo", "name", "asynResUrl", "synResUrl", "area" };

	// 需要签名的返回字段，按顺序
	private final static String[] resSignFields890001 = { "serviceCode", "processCode", "merchantNo", "sequenceNo",
			"bankName", "accountType", "responseCode", "returnType" };
	private final static String[] resSignFieldsSync = { "serviceCode", "processCode", "merchantNo", "sequenceNo",
			"bankName", "accountType", "responseCode" };
	private final static String[] reqSignFields890002 = { "serviceCode", "processCode", "merchantNo", "accountNo" };
	private final static String[] resSignFields890002 = { "serviceCode", "processCode", "merchantNo", "qrNo",
			"responseCode" };
	private final static String[] reqSignFields890003 = { "serviceCode", "processCode", "merchantNo", "accountNo",
			"mobileNo", "orderDate" };
	private final static String[] resSignFields890003 = { "serviceCode", "processCode", "merchantNo", "responseCode" };
	private final static String[] reqSignFields890004 = { "serviceCode", "processCode", "merchantNo", "accountNo",
			"mobileNo" };
	private final static String[] resSignFields890004 = { "serviceCode", "processCode", "merchantNo", "responseCode" };
	@Autowired
	DbSeqServiceImpl dbSeqServiceImpl;
	@Autowired
	RepayMentBankCardMapper repayMentBankCardMapper;

	@Override
	public Object doBindCard(RepaymentBindCardReqDto repaymentBindCardReqDto) {
		// TODO Auto-generated method stub
		// if (!StringUtils.isEmpty(repaymentBindCardReqDto.getIdcardNo())) {
		//
		// }
		// if (!StringUtils.isEmpty(repaymentBindCardReqDto.getName())) {
		//
		// }
		//
		// RepayMentBankCard queryBankCard = new RepayMentBankCard();
		// queryBankCard.setBankcardNo(repaymentBindCardReqDto.getAccountNo());
		// queryBankCard.setUserId(repaymentBindCardReqDto.getUserId());
		// queryBankCard.setBindStatus(1);
		// List<RepayMentBankCard> resultLst = repayMentBankCardMapper.select(queryBankCard);
		// if (null != resultLst && resultLst.size() > 0) {
		// return ResultDtoFactory.toNack(ResponseMessageConstant.SUCCESS_REPAYMENT_BINDCARD +
		// ":用户已经绑定了该银行卡");
		// }

		// SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmssSSS");
		// String sequenceNo=sdf.format(new Date())+phoneNumber;
		String sequenceNo = dbSeqServiceImpl.getRepayMentBankCardNewPK();
		if (StringUtils.isEmpty(sequenceNo)) {
			return ResultFactory.toNackDB();
		}

		String tmp = null;
		Map<String, String> bean = new HashMap<String, String>();
		try {

			bean.put("serviceCode", "0800");
			bean.put("processCode", "890001");
			bean.put("merchantNo", RepayMentConstant.REPAYMENT_MACHNTNO);// 201706150034//102700000025
			bean.put("sequenceNo", sequenceNo);
			// bean.put("accountNo", "6226090000000048");
			// bean.put("mobileNo", "18100000000");
			// bean.put("idcardNo", "510265790128303");
			// bean.put("name", "张三");
			// bean.put("area", "0310");
			bean.put("accountNo", repaymentBindCardReqDto.getAccountNo());
			bean.put("mobileNo", repaymentBindCardReqDto.getMobileNo());
			bean.put("idcardNo", repaymentBindCardReqDto.getIdcardNo());
			bean.put("name", repaymentBindCardReqDto.getName());
			bean.put("area", repaymentBindCardReqDto.getArea());
			bean.put("asynResUrl", RepayMentConstant.REPAYMENT_YUFU_NOTIRY_URL + "mobile/repayment/syncBindCardResult");
			bean.put("synResUrl", RepayMentConstant.REPAYMENT_YUFU_NOTIRY_URL + "mobile/repayment/syncBindCardResult");
			bean.put("signMsg", ServiceTool.sign(bean, reqSignFields890001,
					RepayMentConstant.REPAYMENT_MCHT_PRIVATEKEY_PATH, RepayMentConstant.REPAYMENT_MCHT_PRIVATEKEY_PWD));
			String json = JSON.toJSONString(bean);
			log.info(json);
			String request = ServiceTool.encrypt(json, RepayMentConstant.REPAYMENT_EASY_PUBLICKEY_PATH);
			log.info("req：" + request);
			tmp = Httper.sendAndGet(new String[] { RepayMentConstant.REPAYMENT_YUFU_URL + "repayment/repaymentApp" },
					YUFUAPI_CONNECT_TIME, YUFUAPI_READ_TIME, request);
			if (!Strings.isNullOrEmpty(tmp)) {

				log.info(" 返回报文：" + tmp);
			}
			else {
				log.info(" 返回报文：NULL");
			}

		}
		catch (Exception e) {
			e.printStackTrace();
		}
		JSONObject responseMap = null;
		responseMap = ServiceTool.parseResponse(tmp, resSignFields890001,
				RepayMentConstant.REPAYMENT_MCHT_PRIVATEKEY_PATH, RepayMentConstant.REPAYMENT_MCHT_PRIVATEKEY_PWD,
				RepayMentConstant.REPAYMENT_EASY_PUBLICKEY_PATH);

		if (RepayMentConfig.RES_SUCCESS.equals(responseMap.getString("responseCode"))) {

			RepayMentBankCard repayMentBankCard = new RepayMentBankCard();
			repayMentBankCard.setSequenceNo(sequenceNo);
			repayMentBankCard.setUserId(repayMentBankCard.getUserId());
			repayMentBankCard.setBankcardNo(bean.get("accountNo"));
			repayMentBankCard.setBankcardMobile(bean.get("mobileNo"));
			repayMentBankCard.setBankcardIdcard(bean.get("idcardNo"));
			repayMentBankCard.setBankcardName(bean.get("name"));
			repayMentBankCard.setArea(bean.get("area"));
			repayMentBankCard.setResponseCode(responseMap.getString("responseCode"));
			repayMentBankCard.setResponseRemark(responseMap.getString("responseRemark"));
			repayMentBankCard.setBindStatus(0);
			repayMentBankCard.setSyncNum(0);
			repayMentBankCard.setBankName(responseMap.getString("bankName"));
			repayMentBankCard.setBankCardType(responseMap.getString("accountType"));
			int dbResult = repayMentBankCardMapper.insertSelective(repayMentBankCard);
			if (dbResult > 0) {
				return ResultFactory.toAck(responseMap);
			}
			else {
				return ResultFactory.toNackDB();
			}
		}
		else {
			return ResultFactory.toNackREPAYMEN("上端数据获取错误", responseMap);
		}
	}

	@Override
	public Object doUnBindCard(RepaymentUnBindCardReqDto repaymentUnBindCardReqDto) {
		// TODO Auto-generated method stub
		RepayMentBankCard queryBankCard = new RepayMentBankCard();
		queryBankCard.setSequenceNo(repaymentUnBindCardReqDto.getSequenceNo());
		RepayMentBankCard resultBankCard = repayMentBankCardMapper.selectByPrimaryKey(queryBankCard);

		if (null == resultBankCard || resultBankCard.getBindStatus() != 1) {
			return ResultFactory.toNackCORE(":该银行卡没有绑定");
		}

		// SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmssSSS");
		// String sequenceNo=sdf.format(new Date())+phoneNumber;

		String tmp = null;
		Map<String, String> bean = new HashMap<String, String>();
		try {

			bean.put("serviceCode", "0800");
			bean.put("processCode", "890004");
			bean.put("merchantNo", RepayMentConstant.REPAYMENT_MACHNTNO);// 201706150034//102700000025
			bean.put("accountNo", resultBankCard.getBankcardNo());
			bean.put("mobileNo", resultBankCard.getBankcardMobile());
			bean.put("signMsg", ServiceTool.sign(bean, reqSignFields890004,
					RepayMentConstant.REPAYMENT_MCHT_PRIVATEKEY_PATH, RepayMentConstant.REPAYMENT_MCHT_PRIVATEKEY_PWD));
			String json = JSON.toJSONString(bean);
			log.info(json);
			String request = ServiceTool.encrypt(json, RepayMentConstant.REPAYMENT_EASY_PUBLICKEY_PATH);
			log.info("req：" + request);
			tmp = Httper.sendAndGet(new String[] { RepayMentConstant.REPAYMENT_YUFU_URL + "repayment/repaymentApp" },
					YUFUAPI_CONNECT_TIME, YUFUAPI_READ_TIME, request);
			if (!Strings.isNullOrEmpty(tmp)) {

				log.info(" 返回报文：" + tmp);
			}
			else {
				log.info(" 返回报文：NULL");
			}

		}
		catch (Exception e) {
			e.printStackTrace();
		}
		JSONObject responseMap = null;
		responseMap = ServiceTool.parseResponse(tmp, resSignFields890004,
				RepayMentConstant.REPAYMENT_MCHT_PRIVATEKEY_PATH, RepayMentConstant.REPAYMENT_MCHT_PRIVATEKEY_PWD,
				RepayMentConstant.REPAYMENT_EASY_PUBLICKEY_PATH);
		if (RepayMentConfig.RES_SUCCESS.equals(responseMap.getString("responseCode"))) {

			RepayMentBankCard repayMentBankCard = new RepayMentBankCard();
			repayMentBankCard.setSequenceNo(resultBankCard.getSequenceNo());
			repayMentBankCard.setBindStatus(2);

			int dbResult = repayMentBankCardMapper.updateByPrimaryKeySelective(repayMentBankCard);
			if (dbResult > 0) {
				return ResultFactory.toAck(responseMap);
			}
			else {
				return ResultFactory.toNackDB();
			}
		}
		else {
			return ResultFactory.toNackREPAYMEN("上端数据获取错误", responseMap);
		}
	}

}
