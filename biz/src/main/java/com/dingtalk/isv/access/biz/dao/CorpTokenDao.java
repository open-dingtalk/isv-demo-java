package com.dingtalk.isv.access.biz.dao;

import com.dingtalk.isv.access.biz.model.CorpTokenDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository("corpTokenDao")
public interface CorpTokenDao {

	/**
	 * 创建或更新一个企业的corpTokenDO
	 * @param corpTokenDO
	 */
	public void saveOrUpdateCorpToken(CorpTokenDO corpTokenDO);

	/**
	 *
	 * @param suiteKey
	 * @return
	 */
	public CorpTokenDO getCorpToken(@Param("suiteKey") String suiteKey,@Param("corpId") String corpId);

	/**
	 * 删除企业token
	 * @param suiteKey
	 * @param corpId
     */
	public void deleteCorpToken(@Param("suiteKey") String suiteKey,@Param("corpId") String corpId);

}

