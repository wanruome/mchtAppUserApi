/**
 *	@copyright wanruome-2018
 * 	@author wanruome
 * 	@create 2018年6月23日 下午10:14:53
 */
package com.newpay.webauth.services.impl;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.mchtApi.util.repayment.util.RepayMentConstant;
import com.newpay.webauth.config.AppConfig;
import com.newpay.webauth.config.EncryptConfig;
import com.newpay.webauth.dal.core.PwdErrParse;
import com.newpay.webauth.dal.mapper.RepayMentPayInfoMapper;
import com.newpay.webauth.dal.model.RepayMentPayInfo;
import com.newpay.webauth.dal.request.repaymentpayinfo.PayInfoFindPayPwdReqDto;
import com.newpay.webauth.dal.request.repaymentpayinfo.PayInfoNoPwdFlagRepDto;
import com.newpay.webauth.dal.request.repaymentpayinfo.PayInfoPayModifyPayPwdReqDto;
import com.newpay.webauth.dal.request.repaymentpayinfo.PayInfoPayPwdSetReqDto;
import com.newpay.webauth.dal.request.useraccount.UserAccountReqDto;
import com.newpay.webauth.dal.response.ResultFactory;
import com.newpay.webauth.services.PwdService;
import com.newpay.webauth.services.RepayMentPayInfoService;
import com.ruomm.base.tools.StringUtils;
import com.ruomm.base.tools.TimeUtils;

@Service
public class RepayMentPayInfoServiceImpl implements RepayMentPayInfoService {
	@Autowired
	RepayMentPayInfoMapper repayMentPayInfoMapper;
	@Autowired
	PwdService pwdService;
	private static final String PWD_ERR_NONE = "none";

	@Override
	public Object doGetPayInfo(UserAccountReqDto userAccountReqDto) {
		RepayMentPayInfo queryPayInfo = new RepayMentPayInfo();
		queryPayInfo.setUserId(userAccountReqDto.getUserId());
		RepayMentPayInfo resultPayInfo = repayMentPayInfoMapper.selectByPrimaryKey(queryPayInfo);
		if (null == resultPayInfo) {
			RepayMentPayInfo insertPayInfo = new RepayMentPayInfo();
			insertPayInfo.setUserId(userAccountReqDto.getUserId());
			insertPayInfo.setPwdErrCount(0);
			insertPayInfo.setNoPwdFlag(0);
			insertPayInfo.setNoPwdAmount(RepayMentConstant.REPAYMENT_NOPWDAMOUNT);
			insertPayInfo.setLimitAmount(RepayMentConstant.REPAYMENT_LIMITAMOUNT);
			insertPayInfo.setVersion(1);
			int dbResult = repayMentPayInfoMapper.insert(insertPayInfo);
			if (dbResult > 0) {
				resultPayInfo = insertPayInfo;
			}
			else {
				resultPayInfo = null;
			}
		}
		if (null == resultPayInfo) {
			return ResultFactory.toNackDB();
		}
		else {
			Map<String, String> mapResult = new HashMap<>();
			if (StringUtils.isEmpty(resultPayInfo.getPayPwd())) {
				mapResult.put("payPwdStatus", "0");
			}
			else {
				mapResult.put("payPwdStatus", "1");
			}
			mapResult.put("limitAmount", resultPayInfo.getLimitAmount() + "");
			mapResult.put("noPwdAmount", resultPayInfo.getNoPwdAmount() + "");
			mapResult.put("noPwdFlag", resultPayInfo.getNoPwdFlag() + "");
			return ResultFactory.toAck(mapResult);
		}
	}

	@Override
	public Object doSetPayPwd(PayInfoPayPwdSetReqDto payInfoPayPwdSetReqDto) {
		// TODO Auto-generated method stub
		RepayMentPayInfo queryPayInfo = new RepayMentPayInfo();
		queryPayInfo.setUserId(payInfoPayPwdSetReqDto.getUserId());
		RepayMentPayInfo resultPayInfo = repayMentPayInfoMapper.selectByPrimaryKey(queryPayInfo);
		if (null == resultPayInfo) {
			return ResultFactory.toNackCORE();
		}
		else if (!StringUtils.isEmpty(resultPayInfo.getPayPwd())) {
			return ResultFactory.toNackCORE("已设置了支付密码，不能再设置");
		}
		String pwdDb = EncryptConfig.encryptPayPwd(payInfoPayPwdSetReqDto.getPayPwd());
		if (StringUtils.isEmpty(pwdDb)) {
			return ResultFactory.toNackCORE();
		}
		RepayMentPayInfo updatePayInfo = new RepayMentPayInfo();
		updatePayInfo.setUserId(payInfoPayPwdSetReqDto.getUserId());
		updatePayInfo.setPayPwd(pwdDb);
		// pwdService.parseRequsetPwd(pwdRequest, pwdEncrypt, pwdUuid, isRuleCheck)
		boolean dbFlag = updateRepaymentPayInfo(resultPayInfo, updatePayInfo);
		if (dbFlag) {
			return ResultFactory.toAck(null);
		}
		else {
			return ResultFactory.toNackDB();
		}
	}

	public boolean updateRepaymentPayInfo(RepayMentPayInfo resultPayInfo, RepayMentPayInfo updatePayInfo) {

		updatePayInfo.setVersion(resultPayInfo.getVersion());
		int dbResult = repayMentPayInfoMapper.updateByPrimaryKeySelective(updatePayInfo);
		return dbResult > 0 ? true : false;
	}

	@Override
	public Object doModifyPayPwd(PayInfoPayModifyPayPwdReqDto payInfoPayModifyPayPwdReqDto) {
		// TODO Auto-generated method stub
		RepayMentPayInfo queryPayInfo = new RepayMentPayInfo();
		queryPayInfo.setUserId(payInfoPayModifyPayPwdReqDto.getUserId());
		RepayMentPayInfo resultPayInfo = repayMentPayInfoMapper.selectByPrimaryKey(queryPayInfo);
		if (null == resultPayInfo) {
			return ResultFactory.toNackCORE();
		}
		else if (StringUtils.isEmpty(resultPayInfo.getPayPwd())) {
			return ResultFactory.toNackCORE("没有设置支付密码，请先设置支付密码");
		}
		PwdErrParse pwdErrParse = parseErrCount(resultPayInfo, payInfoPayModifyPayPwdReqDto.getOldPayPwd(),
				payInfoPayModifyPayPwdReqDto.getUuid(), "旧的支付密码");
		if (!pwdErrParse.isValid()) {
			return pwdErrParse.getReturnResp();
		}
		String pwdEncrypt = EncryptConfig.encryptPayPwd(payInfoPayModifyPayPwdReqDto.getNewPayPwd());
		if (StringUtils.isEmpty(pwdEncrypt)) {
			ResultFactory.toNack(ResultFactory.ERR_PWD_PARSE, null);
		}
		RepayMentPayInfo updateBean = new RepayMentPayInfo();
		updateBean.setUserId(payInfoPayModifyPayPwdReqDto.getUserId());
		updateBean.setPayPwd(pwdEncrypt);
		updateBean.setUpdateTime(AppConfig.SDF_DB_TIME.format(new Date()));
		boolean dbFlag = updateRepaymentPayInfo(resultPayInfo, updateBean);
		if (dbFlag) {
			Map<String, String> mapResult = new HashMap<String, String>();
			mapResult.put("version", updateBean.getVersion() + "");
			return ResultFactory.toAck(mapResult);
		}
		else {
			return ResultFactory.toNackDB();
		}
	}

	@Override
	public Object doFindPayPwd(PayInfoFindPayPwdReqDto payInfoFindPayPwdReqDto) {
		// TODO Auto-generated method stub
		RepayMentPayInfo queryPayInfo = new RepayMentPayInfo();
		queryPayInfo.setUserId(payInfoFindPayPwdReqDto.getUserId());
		RepayMentPayInfo resultPayInfo = repayMentPayInfoMapper.selectByPrimaryKey(queryPayInfo);
		if (null == resultPayInfo) {
			return ResultFactory.toNackCORE("支付信息不存在");
		}
		String pwdEncrypt = EncryptConfig.encryptPayPwd(payInfoFindPayPwdReqDto.getNewPayPwd());
		if (StringUtils.isEmpty(pwdEncrypt)) {
			ResultFactory.toNack(ResultFactory.ERR_PWD_PARSE, null);
		}
		String nowTimeStr = AppConfig.SDF_DB_TIME.format(new Date());
		RepayMentPayInfo updatePayInfo = new RepayMentPayInfo();
		updatePayInfo.setUserId(resultPayInfo.getUserId());
		updatePayInfo.setPayPwd(pwdEncrypt);
		updatePayInfo.setPwdErrCount(0);
		updatePayInfo.setPwdErrTime(PWD_ERR_NONE);
		updatePayInfo.setUpdateTime(nowTimeStr);
		boolean dbFlag = updateRepaymentPayInfo(resultPayInfo, updatePayInfo);
		if (dbFlag) {
			return ResultFactory.toAck(null);
		}
		else {
			return ResultFactory.toNackDB();
		}
	}

	@Override
	public Object doModifyNoPwdFlag(PayInfoNoPwdFlagRepDto payInfoNoPwdFlagRepDto) {
		RepayMentPayInfo queryPayInfo = new RepayMentPayInfo();
		queryPayInfo.setUserId(payInfoNoPwdFlagRepDto.getUserId());
		RepayMentPayInfo resultPayInfo = repayMentPayInfoMapper.selectByPrimaryKey(queryPayInfo);
		if (null == resultPayInfo) {
			return ResultFactory.toNackCORE();
		}
		else if (StringUtils.isEmpty(resultPayInfo.getPayPwd())) {
			return ResultFactory.toNackCORE("没有设置支付密码，请先设置支付密码");
		}
		if (!StringUtils.isEmpty(payInfoNoPwdFlagRepDto.getPayPwd())) {
			PwdErrParse pwdErrParse = parseErrCount(resultPayInfo, payInfoNoPwdFlagRepDto.getPayPwd(),
					payInfoNoPwdFlagRepDto.getUuid(), "支付密码");
			if (!pwdErrParse.isValid()) {
				return pwdErrParse.getReturnResp();
			}
		}
		RepayMentPayInfo updatePayInfo = new RepayMentPayInfo();
		updatePayInfo.setUserId(resultPayInfo.getUserId());
		updatePayInfo.setNoPwdFlag(Integer.valueOf(payInfoNoPwdFlagRepDto.getNoPwdFlag()));
		updatePayInfo.setUpdateTime(AppConfig.SDF_DB_TIME.format(new Date()));
		boolean dbResult = updateRepaymentPayInfo(resultPayInfo, updatePayInfo);
		if (dbResult) {
			Map<String, String> mapResult = new HashMap<>();
			// if (StringUtils.isEmpty(resultPayInfo.getPayPwd())) {
			// mapResult.put("payPwdStatus", "0");
			// }
			// else {
			// mapResult.put("payPwdStatus", "1");
			// }
			// mapResult.put("limitAcount", resultPayInfo.getLimitAmount() + "");
			// mapResult.put("noPwdAount", resultPayInfo.getNoPwdAmount() + "");
			mapResult.put("noPwdFlag", updatePayInfo.getNoPwdFlag() + "");
			return ResultFactory.toAck(mapResult);
		}
		else {
			return ResultFactory.toNackDB();
		}
	}

	private PwdErrParse parseErrCount(RepayMentPayInfo resultPayInfo, String pwd, String uuid, String pwdTag) {
		String pwdRemark = StringUtils.isEmpty(pwdTag) ? "支付密码" : pwdTag;
		Date date = new Date();
		PwdErrParse pwdErrParse = new PwdErrParse();
		boolean isCacheOK = false;
		if (StringUtils.isEmpty(resultPayInfo.getPwdErrTime()) || PWD_ERR_NONE.equals(resultPayInfo.getPwdErrTime())) {
			isCacheOK = false;
		}
		else {
			isCacheOK = TimeUtils.isCacheOk(resultPayInfo.getPwdErrTime(), date.getTime(), AppConfig.SDF_DB_TIME,
					AppConfig.PayInfoPayPwdErrTimeLimit());
		}
		if (isCacheOK && null != resultPayInfo.getPwdErrCount()
				&& resultPayInfo.getPwdErrCount() >= AppConfig.PayInfoPayPwdErrCountLimit()) {
			// pwdErrParse.setReturnResp(
			// ResultFactory.toNack(ResultFactory.ERR_NEED_VERIFYCODE, pwdRemark +
			// "错误次数过多，请稍后重试或找回密码"));
			long time = AppConfig.PayInfoPayPwdErrTimeLimit();
			try {
				time = AppConfig.PayInfoPayPwdErrTimeLimit() - date.getTime()
						+ AppConfig.SDF_DB_TIME.parse(resultPayInfo.getPwdErrTime()).getTime();
			}
			catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				time = AppConfig.PayInfoPayPwdErrTimeLimit();
			}
			pwdErrParse.setReturnResp(ResultFactory.toNack(ResultFactory.ERR_NEED_VERIFYCODE,
					pwdRemark + "错误次数过多，" + parseErrTimeRespnseToString(time)));

			return pwdErrParse;
		}
		// 验证密码错误次数
		if (!EncryptConfig.decryptPayPwd(resultPayInfo.getPayPwd()).equals(pwd)) {
			if (isCacheOK) {
				int pwdErrCount = null == resultPayInfo.getPwdErrCount() ? 1 : resultPayInfo.getPwdErrCount() + 1;
				pwdErrParse.setValid(false);
				pwdErrParse.setPwdErrCount(pwdErrCount);
				pwdErrParse.setPwdErrTime(null);
			}
			else {
				pwdErrParse.setValid(false);
				pwdErrParse.setPwdErrCount(1);
				pwdErrParse.setPwdErrTime(AppConfig.SDF_DB_TIME.format(date));
			}
			RepayMentPayInfo updateBean = new RepayMentPayInfo();
			updateBean.setUserId(resultPayInfo.getUserId());
			updateBean.setPwdErrCount(pwdErrParse.getPwdErrCount());
			updateBean.setPwdErrTime(pwdErrParse.getPwdErrTime());
			updateBean.setVersion(resultPayInfo.getVersion());
			int dbResult = repayMentPayInfoMapper.updateByPrimaryKeySelective(updateBean);
			if (dbResult > 0) {
				resultPayInfo.setVersion(resultPayInfo.getVersion() + 1);
				pwdErrParse.setReturnResp(ResultFactory.toNack(ResultFactory.ERR_PWD_WRONG, pwdRemark + "错误"));
				return pwdErrParse;
			}
			else {
				pwdErrParse.setReturnResp(ResultFactory.toNackDB());
				return pwdErrParse;
			}
		}
		else {
			// pwdErrParse.setValid(true);
			// pwdErrParse.setPwdErrCount(0);
			// pwdErrParse.setPwdErrTime(PWD_ERR_NONE);
			// pwdErrParse.setLastAuthTime(AppConfig.SDF_DB_TIME.format(date));
			pwdErrParse.setPwdErrCount(0);
			pwdErrParse.setPwdErrTime(PWD_ERR_NONE);
			pwdErrParse.setLastAuthTime(AppConfig.SDF_DB_TIME.format(date));
			int dbResult = 1;
			if (null == resultPayInfo.getPwdErrCount() || 0 != resultPayInfo.getPwdErrCount()
					|| StringUtils.getLength(resultPayInfo.getPwdErrTime()) > 10) {
				RepayMentPayInfo updateBean = new RepayMentPayInfo();
				updateBean.setUserId(resultPayInfo.getUserId());
				updateBean.setPwdErrCount(0);
				updateBean.setPwdErrTime(PWD_ERR_NONE);
				updateBean.setVersion(resultPayInfo.getVersion());
				dbResult = repayMentPayInfoMapper.updateByPrimaryKeySelective(updateBean);
				resultPayInfo.setVersion(resultPayInfo.getVersion() + 1);
			}
			if (dbResult > 0) {
				pwdErrParse.setValid(true);
				return pwdErrParse;
			}
			else {
				pwdErrParse.setValid(false);
				pwdErrParse.setReturnResp(ResultFactory.toNackDB());
				return pwdErrParse;
			}
		}

	}

	public static String parseErrTimeRespnseToString(long errTime) {
		if (errTime <= 1000l * 60) {
			return "1分钟后重试或找回密码";
		}
		long timeMin = errTime / (1000l * 60);
		if (timeMin < 60l) {
			return timeMin + "分钟后重试或找回密码";
		}
		else if (timeMin % 60 < 30) {
			return timeMin / 60 + "小时后重试或找回密码";
		}
		else {
			return (timeMin / 60 + 1) + "小时后重试或找回密码";
		}
	}

}
