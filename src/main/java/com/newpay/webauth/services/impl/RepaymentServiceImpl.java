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
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.base.mchtApi.util.repayment.util.RepayMentConfig;
import com.base.mchtApi.util.repayment.util.RepayMentConstant;
import com.base.mchtApi.util.repayment.util.ServiceTool;
import com.base.mchtApi.util.repayment.util.http.Httper;
import com.base.mchtApi.util.repayment.util.util.QrTools;
import com.base.mchtApi.util.repayment.util.util.Strings;
import com.newpay.webauth.config.AppConfig;
import com.newpay.webauth.config.EncryptConfig;
import com.newpay.webauth.dal.mapper.RepayMentBankCardMapper;
import com.newpay.webauth.dal.mapper.RepayMentBankCardTempMapper;
import com.newpay.webauth.dal.model.RepayMentBankCard;
import com.newpay.webauth.dal.model.RepayMentBankCardTemp;
import com.newpay.webauth.dal.request.repayment.RepaymentBindCardReqDto;
import com.newpay.webauth.dal.request.repayment.RepaymentQrCodeCallDto;
import com.newpay.webauth.dal.request.repayment.RepaymentQueryOrdersDto;
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
		if (StringUtils.getLengthByChar(repaymentBindCardReqDto.getAccountNo()) > 19
				|| StringUtils.getLengthByChar(repaymentBindCardReqDto.getMobileNo()) > 15
				|| StringUtils.getLengthByChar(repaymentBindCardReqDto.getIdcardNo()) > 20
				|| StringUtils.getLengthByChar(repaymentBindCardReqDto.getName()) > 20
				|| StringUtils.getLengthByChar(repaymentBindCardReqDto.getArea()) > 64)

		{
			return ResultFactory.toNackPARAM();
		}
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
		// 加密银行卡四要素
		insertBankCardTemp.setBankcardNo(EncryptConfig.encryptRepayment(repaymentBindCardReqDto.getAccountNo()));
		insertBankCardTemp.setBankcardMobile(EncryptConfig.encryptRepayment(repaymentBindCardReqDto.getMobileNo()));
		insertBankCardTemp.setBankcardIdcard(EncryptConfig.encryptRepayment(repaymentBindCardReqDto.getIdcardNo()));
		insertBankCardTemp.setBankcardName(EncryptConfig.encryptRepayment(repaymentBindCardReqDto.getName()));
		insertBankCardTemp.setArea(repaymentBindCardReqDto.getArea());
		insertBankCardTemp.setStatus(0);
		String nowTimeStr = AppConfig.SDF_DB_TIME.format(new Date());
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
			bean.put("asynResUrl", RepayMentConstant.REPAYMENT_YUFU_NOTIRY_URL);
			bean.put("synResUrl", RepayMentConstant.REPAYMENT_YUFU_NOTIRY_URL);
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
		// responseMap = RepeyMentTest.createSucess();
		// 更新数据
		RepayMentBankCardTemp updateBean = new RepayMentBankCardTemp();
		updateBean.setSequenceNo(insertBankCardTemp.getSequenceNo());
		updateBean.setResponseCode(responseMap.getString("responseCode"));
		updateBean.setResponseRemark(responseMap.getString("responseRemark"));
		updateBean.setStatus(1);
		updateBean.setBankName(responseMap.getString("bankName"));
		updateBean.setBankCardType(responseMap.getString("accountType"));
		String updateTimeStr = AppConfig.SDF_DB_TIME.format(new Date());
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
			if (null != RepayMentConstant.REPAYMENT_AUTOUNLOCK && RepayMentConstant.REPAYMENT_AUTOUNLOCK) {
				if (RepayMentConfig.RES_ERROR_HISBIND.equals(responseMap.getString("responseCode"))
						&& RepayMentConfig.RES_ERROR_HISBIND_MSG.equals(responseMap.getString("responseRemark"))) {
					String cardNo = repaymentBindCardReqDto.getAccountNo();
					String mobileNo = repaymentBindCardReqDto.getMobileNo();
					tryUnBindCardAuto(cardNo, mobileNo);

				}
			}
			return ResultFactory.toNackREPAYMEN("上端数据获取错误", responseMap);
		}
	}

	@Override
	public Object doUnBindCard(RepaymentUnBindCardReqDto repaymentUnBindCardReqDto) {
		// TODO Auto-generated method stub
		RepayMentBankCard queryBankCard = new RepayMentBankCard();
		queryBankCard.setCardIndex(repaymentUnBindCardReqDto.getCardIndex());
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
			String cardNoTemp = EncryptConfig.decryptRepayment(resultBankCard.getBankcardNo());
			String mobieTemp = EncryptConfig.decryptRepayment(resultBankCard.getBankcardMobile());
			bean.put("serviceCode", "0800");
			bean.put("processCode", "890004");
			bean.put("merchantNo", RepayMentConstant.REPAYMENT_MACHNTNO);// 201706150034//102700000025
			bean.put("accountNo", cardNoTemp);
			bean.put("mobileNo", mobieTemp);
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
		// responseMap = RepeyMentTest.createUnBind();
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
		log.info("syncREQ：" + requestStr);
		JSONObject responseMap = null;
		responseMap = ServiceTool.parseResponse(requestStr, resSignFieldsSync,
				RepayMentConstant.REPAYMENT_MCHT_PRIVATEKEY_PATH, RepayMentConstant.REPAYMENT_MCHT_PRIVATEKEY_PWD,
				RepayMentConstant.REPAYMENT_EASY_PUBLICKEY_PATH);
		log.info("syncREQ:" + JSON.toJSONString(responseMap));
		if (RepayMentConfig.RES_SUCCESS.equals(responseMap.getString("responseCode"))
				&& "0810".equals(responseMap.getString("serviceCode"))
				&& "890001".equals(responseMap.getString("processCode"))) {
			RepayMentBankCardTemp queryBankCardTemp = new RepayMentBankCardTemp();
			queryBankCardTemp.setSequenceNo(responseMap.getString("sequenceNo"));
			RepayMentBankCardTemp resultBankCardTemp = repayMentBankCardTempMapper
					.selectByPrimaryKey(queryBankCardTemp);
			if (null == resultBankCardTemp) {
				return ResultFactory.toNackDB();
			}
			String cardNoTemp = EncryptConfig.decryptRepayment(resultBankCardTemp.getBankcardNo());
			String cardFinger = RepayMentConfig.getCardFinger(cardNoTemp);
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

			insertCard.setBankName(realBankName);
			insertCard.setBankCardType(realAccountType);
			insertCard.setBindStatus(1);
			insertCard.setResponseCode(responseMap.getString("responseCode"));
			insertCard.setResponseRemark(responseMap.getString("responseRemark"));
			String nowTimeStr = AppConfig.SDF_DB_TIME.format(new Date());
			// insertCard.setCreateTime(nowTimeStr);
			insertCard.setUpdateTime(nowTimeStr);
			RepayMentBankCard queryBankCard = new RepayMentBankCard();
			queryBankCard.setUserId(resultBankCardTemp.getUserId());
			queryBankCard.setCardFinger(cardFinger);
			RepayMentBankCard resultBankCard = repayMentBankCardMapper.selectOne(queryBankCard);
			int dbResult = 0;
			if (null == resultBankCard) {
				String cardIndex = dbSeqServiceImpl.getRMBankCardTempNewPK();
				insertCard.setCardIndex(cardIndex);
				insertCard.setCreateTime(nowTimeStr);
				insertCard.setCardFinger(cardFinger);
				insertCard.setVersion(1);
				dbResult = repayMentBankCardMapper.insertSelective(insertCard);
			}
			else {
				insertCard.setCardIndex(resultBankCard.getCardIndex());
				insertCard.setCreateTime(resultBankCard.getCreateTime());
				insertCard.setCardFinger(cardFinger);
				insertCard.setVersion(resultBankCard.getVersion());
				dbResult = repayMentBankCardMapper.updateByPrimaryKey(resultBankCard);
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

	@Override
	public Object doQueryOrders(RepaymentQueryOrdersDto repaymentQueryOrdersDto) {
		String bankCardNo = null;
		if (StringUtils.isEmpty(repaymentQueryOrdersDto.getMobileNo())
				&& StringUtils.isEmpty(repaymentQueryOrdersDto.getCardIndex())) {
			return ResultFactory.toNackPARAM();
		}
		if (!StringUtils.isEmpty(repaymentQueryOrdersDto.getCardIndex())) {
			RepayMentBankCard queryBankCard = new RepayMentBankCard();
			queryBankCard.setUserId(repaymentQueryOrdersDto.getUserId());
			queryBankCard.setCardIndex(repaymentQueryOrdersDto.getCardIndex());
			RepayMentBankCard resultBankCard = repayMentBankCardMapper.selectOne(queryBankCard);
			if (null == resultBankCard || resultBankCard.getBindStatus() != 1) {
				return ResultFactory.toNackCORE("用户没有绑定该银行卡");
			}
			bankCardNo = EncryptConfig.decryptRepayment(resultBankCard.getBankcardNo());
		}
		String tmp = null;
		Map<String, String> bean = new HashMap<String, String>();
		try {

			bean.put("serviceCode", "0800");
			bean.put("processCode", "890003");
			bean.put("merchantNo", RepayMentConstant.REPAYMENT_MACHNTNO);// 201706150034//102700000025
			bean.put("accountNo", bankCardNo);
			bean.put("mobileNo", repaymentQueryOrdersDto.getMobileNo());
			bean.put("orderDate", repaymentQueryOrdersDto.getOrderDate());
			bean.put("signMsg", ServiceTool.sign(bean, reqSignFields890003,
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
		JSONObject responseMap = ServiceTool.parseResponse(tmp, resSignFields890003,
				RepayMentConstant.REPAYMENT_MCHT_PRIVATEKEY_PATH, RepayMentConstant.REPAYMENT_MCHT_PRIVATEKEY_PWD,
				RepayMentConstant.REPAYMENT_EASY_PUBLICKEY_PATH);
		log.info(JSON.toJSONString(responseMap));
		if (RepayMentConfig.RES_SUCCESS.equals(responseMap.getString("responseCode"))) {
			try {
				JSONArray jsonArr = responseMap.getJSONArray("transInfo");
				if (null != jsonArr) {
					responseMap.remove("transInfo");
					responseMap.put("transInfo", jsonArr);
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}

			return ResultFactory.toAck(responseMap);
		}
		else {
			return ResultFactory.toNackREPAYMEN("上端数据获取错误", responseMap);
		}
	}

	private void tryUnBindCardAuto(final String cardNo, final String mobileNo) {
		if (null != RepayMentConstant.REPAYMENT_AUTOUNLOCK_ASYNC && RepayMentConstant.REPAYMENT_AUTOUNLOCK_ASYNC) {
			new Thread() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					super.run();
					tryUnBindCardAutoCore(cardNo, mobileNo);
				}
			}.start();
		}
		else {
			tryUnBindCardAutoCore(cardNo, mobileNo);
		}

	}

	private void tryUnBindCardAutoCore(String cardNo, String mobileNo) {
		try {
			RepayMentBankCard repayMentBankCard = new RepayMentBankCard();
			repayMentBankCard.setCardFinger(RepayMentConfig.getCardFinger(cardNo));
			repayMentBankCard.setBindStatus(1);
			List<RepayMentBankCard> lstResult = repayMentBankCardMapper.select(repayMentBankCard);
			if (null != lstResult && lstResult.size() > 0) {
				return;
			}

			String tmp = null;
			Map<String, String> bean = new HashMap<String, String>();
			bean.put("serviceCode", "0800");
			bean.put("processCode", "890004");
			bean.put("merchantNo", RepayMentConstant.REPAYMENT_MACHNTNO);// 201706150034//102700000025
			bean.put("accountNo", cardNo);
			bean.put("mobileNo", mobileNo);
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
	}

	@Override
	public Object doCallQrcode(RepaymentQrCodeCallDto repaymentQrCodeCallDto) {
		RepayMentBankCard queryBankCard = new RepayMentBankCard();
		queryBankCard.setUserId(repaymentQrCodeCallDto.getUserId());
		queryBankCard.setCardIndex(repaymentQrCodeCallDto.getCardIndex());
		RepayMentBankCard resultBankCard = repayMentBankCardMapper.selectOne(queryBankCard);
		if (null == resultBankCard || resultBankCard.getBindStatus() != 1) {
			return ResultFactory.toNackCORE("用户没有绑定该银行卡");
		}
		String tmp = null;
		Map<String, String> bean = new HashMap<String, String>();
		try {

			bean.put("serviceCode", "0800");
			bean.put("processCode", "890002");
			bean.put("merchantNo", RepayMentConstant.REPAYMENT_MACHNTNO);// 201706150034//102700000025
			bean.put("accountNo", EncryptConfig.decryptRepayment(resultBankCard.getBankcardNo()));
			bean.put("signMsg", ServiceTool.sign(bean, reqSignFields890002,
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
		JSONObject responseMap = ServiceTool.parseResponse(tmp, resSignFields890002,
				RepayMentConstant.REPAYMENT_MCHT_PRIVATEKEY_PATH, RepayMentConstant.REPAYMENT_MCHT_PRIVATEKEY_PWD,
				RepayMentConstant.REPAYMENT_EASY_PUBLICKEY_PATH);
		log.info(JSON.toJSONString(responseMap));
		if (RepayMentConfig.RES_SUCCESS.equals(responseMap.getString("responseCode"))
				&& StringUtils.isNotEmpty(responseMap.getString("qrNo"))) {

			return ResultFactory.toAck(responseMap);
		}
		else {
			if (RepayMentConstant.REPAYMENT_QRCODE_TEST) {
				responseMap.put("serviceCode", "0810");
				responseMap.put("processCode", "890002");
				responseMap.put("merchantNo", RepayMentConstant.REPAYMENT_MACHNTNO);
				responseMap.put("responseCode", RepayMentConfig.RES_SUCCESS);
				responseMap.put("responseRemark", "成功");
				responseMap.put("qrNo", QrTools.createNewQrCode("622", 19));
				return ResultFactory.toAck(responseMap);
			}
			else {
				return ResultFactory.toNackREPAYMEN("上端数据获取错误", responseMap);
			}
		}
	}

}
