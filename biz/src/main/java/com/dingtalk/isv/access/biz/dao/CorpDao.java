package com.dingtalk.isv.access.biz.dao;

import com.dingtalk.isv.access.biz.model.CorpDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository("corpDao")
public interface CorpDao {


	/**
	 * 创建一个企业信息
	 * @param corpDO
	 */
	public void saveOrUpdateCorp(CorpDO corpDO);

	/**
	 * 根据corpId查询企业
	 * @param corpId
	 * @return
	 */
	public CorpDO getCorpByCorpId(@Param("corpId") String corpId);


}

