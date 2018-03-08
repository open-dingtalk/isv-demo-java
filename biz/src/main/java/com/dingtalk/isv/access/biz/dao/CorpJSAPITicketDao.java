package com.dingtalk.isv.access.biz.dao;

import com.dingtalk.isv.access.biz.model.CorpJSAPITicketDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository("corpJSAPITicketDao")
public interface CorpJSAPITicketDao {

	/**
	 * 创建或更新一个企业的corpJSTicketDO
	 * @param corpJSTicketDO
	 */
	public void saveOrUpdateCorpJSAPITicket(CorpJSAPITicketDO corpJSTicketDO);

	/**
	 * 获取企业的JSTicket
	 * @param suiteKey
	 * @return
	 */
	public CorpJSAPITicketDO getCorpJSAPITicket(@Param("suiteKey") String suiteKey, @Param("corpId") String corpId);

	/**
	 * 删除企业JSTicket
	 * @param suiteKey
	 * @param corpId
     */
	public void deleteCorpJSAPITicket(@Param("suiteKey") String suiteKey, @Param("corpId") String corpId);

}

