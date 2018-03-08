package com.dingtalk.isv.access.biz.dao;

import com.dingtalk.isv.access.biz.model.CorpChannelJSAPITicketDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository("corpChannelJSAPITicketDao")
public interface CorpChannelJSAPITicketDao {

	/**
	 * 创建或更新一个企业的corpChannelJSAPITicketDO
	 * @param corpChannelJSAPITicketDO
	 */
	public void saveOrUpdateCorpChannelJSAPITicket(CorpChannelJSAPITicketDO corpChannelJSAPITicketDO);

	/**
	 * 获取企业的JSTicket
	 * @param suiteKey
	 * @return
	 */
	public CorpChannelJSAPITicketDO getCorpChannelJSAPITicket(@Param("suiteKey") String suiteKey, @Param("corpId") String corpId);

	/**
	 * 删除企业JSTicket
	 * @param suiteKey
	 * @param corpId
     */
	public void deleteCorpChannelJSAPITicket(@Param("suiteKey") String suiteKey, @Param("corpId") String corpId);

}

