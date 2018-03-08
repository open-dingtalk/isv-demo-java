package com.dingtalk.isv.access.biz.dao;

import com.dingtalk.isv.access.biz.model.SuiteTokenDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("suiteTokenDao")
public interface SuiteTokenDao {

	/**
	 * 创建或更新一个套件SuiteToken
	 * @param suiteTokenDO
	 */
	public void saveOrUpdateSuiteToken(SuiteTokenDO suiteTokenDO);

	/**
	 * 根据suiteKey查询套件SuiteToken
	 * @param suiteKey
	 * @return
	 */
	public SuiteTokenDO getSuiteTokenByKey(@Param("suiteKey") String suiteKey);

	/**
	 * 查询套件SuiteToken.因为套件不多,无需分页
	 * @return
	 */
	public List<SuiteTokenDO> getAllSuiteToken();


}

