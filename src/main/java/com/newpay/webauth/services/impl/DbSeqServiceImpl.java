/**
 *	@copyright wanruome-2018
 * 	@author wanruome
 * 	@create 2018年6月10日 下午11:30:41
 */
package com.newpay.webauth.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.newpay.webauth.dal.mapper.DbSequenceMapper;
import com.newpay.webauth.dal.model.DbSequence;
import com.newpay.webauth.services.DbSeqService;
import com.ruomm.base.tools.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DbSeqServiceImpl implements DbSeqService {
	@Autowired
	DbSequenceMapper dbSequenceMapper;
	@Value("${MyBatis.DBTYPE}")
	private String dbType;

	private String getSeqByName(String seqName, String headerName, int insertValue) {
		if (StringUtils.isEmpty(seqName)) {
			return null;
		}
		if (null == dbType || !dbType.toLowerCase().equals("oracle")) {
			log.debug("数据库类型1：" + dbType);
			int value = getSeqForMySQL(seqName, insertValue);
			if (value > 0) {
				return StringUtils.nullStrToEmpty(headerName) + value + "";
			}
			else {
				return null;
			}
		}
		else {
			log.debug("数据库类型2：" + dbType);
			String sqlStr = "select " + seqName + ".Nextval from dual";
			long value = dbSequenceMapper.getSeqNextval(sqlStr);
			// long value = dbSequenceMapper.getSeqNextval(seqName);
			// int value = getSeqForMySQL(seqName, insertValue);
			if (value > 0) {
				return StringUtils.nullStrToEmpty(headerName) + value + "";
			}
			else {
				return null;
			}
		}
	}

	private int getSeqForMySQL(String name, int insertValue) {
		// TODO Auto-generated method stub
		String seqName = StringUtils.isEmpty(name) ? "default_seq_key" : name;
		DbSequence querySeq = new DbSequence();
		querySeq.setSeqName(seqName);
		DbSequence resultDbSeq = dbSequenceMapper.selectByPrimaryKey(querySeq);
		int dbResult = 0;
		DbSequence updateDbSeq = new DbSequence();
		if (null == resultDbSeq) {
			updateDbSeq.setSeqName(seqName);
			if (insertValue > 0) {
				updateDbSeq.setSeqValue(insertValue);
			}
			else {
				updateDbSeq.setSeqValue(1);
			}
			updateDbSeq.setVersion(1);
			dbResult = dbSequenceMapper.insertSelective(updateDbSeq);
		}
		else {

			updateDbSeq.setSeqName(resultDbSeq.getSeqName());
			updateDbSeq.setSeqValue(resultDbSeq.getSeqValue() + 1);
			updateDbSeq.setVersion(resultDbSeq.getVersion());
			dbResult = dbSequenceMapper.updateByPrimaryKeySelective(updateDbSeq);
		}
		return dbResult > 0 ? updateDbSeq.getSeqValue() : -9999;
	}

	@Override
	public String getLoginUserNewPK() {
		return getSeqByName("SEQ_LOGIN_USER_NEW_PK", null, 100000);
	}

	@Override
	public String getMsgInfoNewPk() {
		return getSeqByName("SEQ_MSG_INFO_NEW_PK", null, 100000);
	}

	@Override
	public String getLoginTokenNewPk() {
		// TODO Auto-generated method stub
		return getSeqByName("SEQ_LOGIN_TOKEN_NEW_PK", null, 100000);
	}

	@Override
	public String getLoginAppInfoNewPk() {
		// TODO Auto-generated method stub
		return getSeqByName("SEQ_LOGIN_APP_INFO_NEW_PK", null, 100000);
	}

	@Override
	public String getSystemLogNewPk() {
		// TODO Auto-generated method stub
		return getSeqByName("SEQ_SYSTEM_LOG_NEW_PK", null, 100000);
	}

	@Override
	public String getRMBankCardTempNewPK() {
		return getSeqByName("SEQ_RM_BANKCARD_TEMP_NEW_PK", null, 100000);
	}

	@Override
	public String getRMBankCardNewPK() {
		return getSeqByName("SEQ_RM_BANKCARD_NEW_PK", null, 100000);
	}

	@Override
	public String getFeedBackNewPk() {
		return getSeqByName("SEQ_FEED_BACK_NEW_PK", null, 100000);
	}

	@Override
	public String getLoginTermInfoNewPk() {
		return getSeqByName("SEQ_LOGIN_TERM_INFO_NEW_PK", null, 100000);
	}

	@Override
	public String getTermInfoLogNewPk() {
		return getSeqByName("SEQ_TERM_INFO_LOG_NEW_PK", null, 100000);
	}

}
