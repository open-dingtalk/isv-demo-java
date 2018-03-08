package com.dingtalk.isv.access.biz.dao;

import com.dingtalk.isv.access.biz.model.CorpChannelTokenDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository("corpChannelTokenDao")
public interface CorpChannelTokenDao {

	/**
	 * 创建或更新一个企业的corpChTokenDO
	 * @param corpChTokenDO
	 */
	public void saveOrUpdateCorpChannelToken(CorpChannelTokenDO corpChTokenDO);

	/**
	 *
	 * @param suiteKey
	 * @return
	 */
	public CorpChannelTokenDO getCorpChannelToken(@Param("suiteKey") String suiteKey, @Param("corpId") String corpId);

	/**
	 * 删除企业token
	 * @param suiteKey
	 * @param corpId
     */
	public void deleteCorpChannelToken(@Param("suiteKey") String suiteKey, @Param("corpId") String corpId);

}

