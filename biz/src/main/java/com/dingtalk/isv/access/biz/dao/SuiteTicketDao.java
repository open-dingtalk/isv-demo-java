package com.dingtalk.isv.access.biz.dao;

import com.dingtalk.isv.access.biz.model.SuiteTicketDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("suiteTicketDao")
public interface SuiteTicketDao {

	/**
	 * 创建或更新一个套件SuiteTicket
	 * @param suiteTicketDO
	 */
	public void saveOrUpdateSuiteTicket(SuiteTicketDO suiteTicketDO);

	/**
	 * 根据suiteKey查询套件SuiteTicket
	 * @param suiteKey
	 * @return
	 */
	public SuiteTicketDO getSuiteTicketByKey(@Param("suiteKey") String suiteKey);

	/**
	 * 查询套件SuiteTicket,无需分页
	 * @return
     */
	public List<SuiteTicketDO> getAllSuiteTicket();


}

