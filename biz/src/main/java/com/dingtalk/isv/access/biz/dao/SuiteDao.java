package com.dingtalk.isv.access.biz.dao;

import com.dingtalk.isv.access.biz.model.SuiteDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("suiteDao")
public interface SuiteDao{

	/**
	 * 因为作为一个正常的isv来说,套件的格式不会太多
	 * 所以这个接口不需要加分页
	 * @return
	 */
	public List<SuiteDO> getAllSuite();

	/**
	 * 创建一个套件
	 * @param suiteDO
	 */
	public void addSuite(SuiteDO suiteDO);

	/**
	 * 根据suiteKey查询套件
	 * @param suiteKey
	 * @return
	 */
	public SuiteDO getSuiteByKey(@Param("suiteKey") String suiteKey);


}

