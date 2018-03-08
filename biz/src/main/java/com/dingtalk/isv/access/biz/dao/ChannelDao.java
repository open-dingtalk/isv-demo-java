package com.dingtalk.isv.access.biz.dao;

import com.dingtalk.isv.access.biz.model.ChannelDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("channelDao")
public interface ChannelDao {


	/**
	 * 增加一个服务窗应用
	 * @param channelDO
	 */
	public void addChannel(ChannelDO channelDO);

	/**
	 * 根据suiteKey查询服务窗应用
	 * @param suiteKey
	 * @return
	 */
	public List<ChannelDO> getAppBySuiteKey(@Param("suiteKey") String suiteKey);

	/**
	 * 删除服务窗应用
	 * @param corpId
	 * @param appId
     */
	public void deleteCorpApp(@Param("corpId")String corpId,@Param("appId")Long appId);


}

