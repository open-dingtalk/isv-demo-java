package com.dingtalk.isv.access.biz.dao;

import com.dingtalk.isv.access.biz.model.CorpAppDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

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
     * 删除一个企业使用微应用记录
     * @param corpId
     * @param appId
     */
    public void deleteCorpApp(@Param("corpId")String corpId,@Param("appId")Long appId);

}

