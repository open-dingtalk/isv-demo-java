package com.dingtalk.isv.access.biz.dao;

import com.dingtalk.isv.access.biz.model.CorpChannelAppDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 企业使用微应用关联DAO
 * Created by lifeng.zlf on 2016/1/19.
 */
@Repository("corpChannelAppDao")
public interface CorpChannelAppDao {

    /**
     * 创建一个企业使用微应用记录
     * @param corpChannelAppDO
     */
    public void saveOrUpdateCorpChannelApp(CorpChannelAppDO corpChannelAppDO);

    /**
     * 获取一个企业使用微应用记录
     * @param corpId
     * @param appId
     */
    public CorpChannelAppDO getCorpChannelApp(@Param("corpId") String corpId, @Param("appId") Long appId);

    /**
     * 删除一个企业使用微应用记录
     * @param corpId
     * @param appId
     */
    public void deleteCorpChannelApp(@Param("corpId") String corpId, @Param("appId") Long appId);

}

