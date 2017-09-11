package com.dingtalk.isv.access.biz.suite.dao;

import com.dingtalk.isv.access.biz.suite.model.CorpAppDO;
import com.dingtalk.isv.access.biz.suite.model.SuiteDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 企业使用微应用关联DAO
 * Created by lifeng.zlf on 2016/1/19.
 */
@Repository("corpAppDao")
public interface CorpAppDao {

    /**
     * 创建一个企业使用微应用记录
     * @param corpAppDO
     */
    public void saveOrUpdateCorpApp(CorpAppDO corpAppDO);

    /**
     * 获取一个企业使用微应用记录
     * @param corpId
     * @param appId
     */
    public CorpAppDO getCorpApp(@Param("corpId")String corpId,@Param("appId")Long appId);

    /**
     * 获取一个企业所有微应用记录
     */
    public List<CorpAppDO> getAllCorpApp();

    /**
     * 删除一个企业使用微应用记录
     * @param corpId
     * @param appId
     */
    public void deleteCorpApp(@Param("corpId")String corpId,@Param("appId")Long appId);

}

