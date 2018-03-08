package com.dingtalk.isv.access.biz.dao;

import com.dingtalk.isv.access.biz.model.CorpSuiteAuthFaileDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by mint on 16-1-26.
 */
@Repository("corpSuiteAuthFaileDao")
public interface CorpSuiteAuthFaileDao {

    /**
     * 创建一个授权失败的对象
     * @param corpSuiteAuthFaileDO
     */
    public void addOrUpdateCorpSuiteAuthFaileDO(CorpSuiteAuthFaileDO corpSuiteAuthFaileDO);



    public List<CorpSuiteAuthFaileDO> getCorpSuiteAuthFaileList(@Param("offset")Integer offset, @Param("limit")Integer limit);


    public void deleteById(@Param("id")Long id);
}
