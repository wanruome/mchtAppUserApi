/**
 *	@copyright wanruome-2018
 * 	@author wanruome
 * 	@create 2018年6月21日 下午1:40:09
 */
package com.newpay.webauth.services.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.base.mchtApi.util.repayment.util.RepayMentConfig;
import com.base.mchtApi.util.repayment.util.RepayMentConstant;
import com.base.mchtApi.util.repayment.util.RepeyMentTest;
import com.base.mchtApi.util.repayment.util.ServiceTool;
import com.base.mchtApi.util.repayment.util.http.Httper;
import com.base.mchtApi.util.repayment.util.util.Strings;
import com.newpay.webauth.config.AppConfig;
import com.newpay.webauth.dal.mapper.RepayMentBankCardMapper;
import com.newpay.webauth.dal.mapper.RepayMentBankCardTempMapper;
import com.newpay.webauth.dal.model.RepayMentBankCard;
import com.newpay.webauth.dal.model.RepayMentBankCardTemp;
import com.newpay.webauth.dal.request.repayment.RepaymentBindCardReqDto;
import com.newpay.webauth.dal.request.repayment.RepaymentUnBindCardReqDto;
import com.newpay.webauth.dal.response.ResultFactory;
import com.newpay.webauth.services.DbSeqService;
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
	DbSeqService dbSeqServiceImpl;
	@Autowired
	RepayMentBankCardTempMapper repayMentBankCardTempMapper;
	@Autowired
	RepayMentBankCardMapper repayMentBankCardMapper;

	@Override
	public Object doBindCard(RepaymentBindCardReqDto repaymentBindCardReqDto) {
		RepayMentBankCard queryBankCard = new RepayMentBankCard();
		queryBankCard.setBankcardNo(repaymentBindCardReqDto.getAccountNo());
		queryBankCard.setUserId(repaymentBindCardReqDto.getUserId());
		queryBankCard.setBindStatus(1);
		List<RepayMentBankCard> resultLst = repayMentBankCardMapper.select(queryBankCard);
		if (null != resultLst && resultLst.size() > 0) {
			return ResultFactory.toNackCORE("用户已经绑定了该银行卡");
		}
		String sequenceNo = dbSeqServiceImpl.getRMBankCardTempNewPK();
		if (StringUtils.isEmpty(sequenceNo)) {
			return ResultFactory.toNackDB();
		}
		// 插入绑卡数据
		RepayMentBankCardTemp insertBankCardTemp = new RepayMentBankCardTemp();
		insertBankCardTemp.setSequenceNo(sequenceNo);
		insertBankCardTemp.setUserId(repaymentBindCardReqDto.getUserId());
		insertBankCardTemp.setBankcardNo(repaymentBindCardReqDto.getAccountNo());
		insertBankCardTemp.setBankcardMobile(repaymentBindCardReqDto.getMobileNo());
		insertBankCardTemp.setBankcardIdcard(repaymentBindCardReqDto.getIdcardNo());
		insertBankCardTemp.setBankcardName(repaymentBindCardReqDto.getName());
		insertBankCardTemp.setArea(repaymentBindCardReqDto.getArea());
		insertBankCardTemp.setStatus(0);
		String nowTimeStr = AppConfig.SDF_DB_VERSION.format(new Date());
		insertBankCardTemp.setCreateTime(nowTimeStr);
		insertBankCardTemp.setUpdateTime(nowTimeStr);
		// repayMentBankCardTemp.setResponseCode(responseMap.getString("responseCode"));
		// repayMentBankCardTemp.setResponseRemark(responseMap.getString("responseRemark"));
		// repayMentBankCardTemp.setBankName(responseMap.getString("bankName"));
		// repayMentBankCardTemp.setBankCardType(responseMap.getString("accountType"));
		int dbResult = repayMentBankCardTempMapper.insertSelective(insertBankCardTemp);
		if (dbResult <= 0) {
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
		// 模拟成功
		responseMap = RepeyMentTest.createSucess();
		// 更新数据
		RepayMentBankCardTemp updateBean = new RepayMentBankCardTemp();
		updateBean.setSequenceNo(insertBankCardTemp.getSequenceNo());
		updateBean.setResponseCode(responseMap.getString("responseCode"));
		updateBean.setResponseRemark(responseMap.getString("responseRemark"));
		updateBean.setStatus(1);
		updateBean.setBankName(responseMap.getString("bankName"));
		updateBean.setBankCardType(responseMap.getString("accountType"));
		String updateTimeStr = AppConfig.SDF_DB_VERSION.format(new Date());
		insertBankCardTemp.setUpdateTime(updateTimeStr);
		dbResult = repayMentBankCardTempMapper.updateByPrimaryKeySelective(updateBean);

		if (RepayMentConfig.RES_SUCCESS.equals(responseMap.getString("responseCode"))) {
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
		queryBankCard.setCardIndex(repaymentUnBindCardReqDto.getSequenceNo());
		RepayMentBankCard resultBankCard = repayMentBankCardMapper.selectByPrimaryKey(queryBankCard);
		if (null == resultBankCard || !repaymentUnBindCardReqDto.getUserId().equals(resultBankCard.getUserId())) {
			return ResultFactory.toNackCORE("用户没有绑定该银行卡");
		}
		else if (null == resultBankCard.getBindStatus() || resultBankCard.getBindStatus() != 1) {
			return ResultFactory.toNackCORE("用户已经解绑了银行卡");
		}

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
		responseMap = RepeyMentTest.createUnBind();
		if (RepayMentConfig.RES_SUCCESS.equals(responseMap.getString("responseCode"))) {

			RepayMentBankCard repayMentBankCard = new RepayMentBankCard();
			repayMentBankCard.setCardIndex(resultBankCard.getCardIndex());
			repayMentBankCard.setBindStatus(2);
			repayMentBankCard.setVersion(resultBankCard.getVersion());
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

	@Override
	public Object callBindCardResult(String requestStr) {
		// TODO Auto-generated method stub
		JSONObject responseMap = RepeyMentTest.createSucess();
		String sequenceNo = requestStr;
		if (RepayMentConfig.RES_SUCCESS.equals(responseMap.getString("responseCode"))) {
			RepayMentBankCardTemp queryBankCardTemp = new RepayMentBankCardTemp();
			queryBankCardTemp.setSequenceNo(sequenceNo);
			RepayMentBankCardTemp resultBankCardTemp = repayMentBankCardTempMapper
					.selectByPrimaryKey(queryBankCardTemp);
			if (null == resultBankCardTemp) {
				return ResultFactory.toNackDB();
			}
			String resBankName = responseMap.getString("bankName");
			String resAccountType = responseMap.getString("accountType");
			String realBankName = StringUtils.isEmpty(resBankName) ? resultBankCardTemp.getBankName() : resBankName;
			String realAccountType = StringUtils.isEmpty(resAccountType) ? resultBankCardTemp.getBankCardType()
					: resAccountType;
			RepayMentBankCard insertCard = new RepayMentBankCard();
			insertCard.setUserId(resultBankCardTemp.getUserId());
			insertCard.setBankcardNo(resultBankCardTemp.getBankcardNo());
			insertCard.setBankcardMobile(resultBankCardTemp.getBankcardMobile());
			insertCard.setBankcardIdcard(resultBankCardTemp.getBankcardIdcard());
			insertCard.setBankcardName(resultBankCardTemp.getBankcardName());
			insertCard.setArea(resultBankCardTemp.getArea());

			insertCard.setBankName(realAccountType);
			insertCard.setBankCardType(realAccountType);
			insertCard.setBindStatus(1);
			insertCard.setResponseCode(responseMap.getString("responseCode"));
			insertCard.setResponseRemark(responseMap.getString("responseRemark"));
			String nowTimeStr = AppConfig.SDF_DB_VERSION.format(new Date());
			// insertCard.setCreateTime(nowTimeStr);
			insertCard.setUpdateTime(nowTimeStr);
			RepayMentBankCard queryBankCard = new RepayMentBankCard();
			queryBankCard.setUserId(resultBankCardTemp.getUserId());
			queryBankCard.setBankcardNo(resultBankCardTemp.getBankcardNo());
			RepayMentBankCard resultBankCard = repayMentBankCardMapper.selectOne(queryBankCard);
			int dbResult = 0;
			if (null == resultBankCard) {
				String cardIndex = dbSeqServiceImpl.getRMBankCardTempNewPK();
				insertCard.setCreateTime(nowTimeStr);
				insertCard.setCardIndex(cardIndex);
				insertCard.setVersion(1);
				dbResult = repayMentBankCardMapper.insertSelective(insertCard);
			}
			else {
				insertCard.setCardIndex(resultBankCard.getCardIndex());
				insertCard.setVersion(resultBankCard.getVersion());
				dbResult = repayMentBankCardMapper.updateByPrimaryKey(insertCard);
			}
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
