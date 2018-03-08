package com.dingtalk.isv.access.biz.dao;

import com.dingtalk.isv.access.biz.model.CorpSuiteAuthDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by lifeng.zlf on 2016/1/19.
 */
@Repository("corpSuiteAuthDao")
public interface CorpSuiteAuthDao {

    /**
     * 创建一个企业对套件授权对象
     *
     * @param corpSuiteAuthDO
     */
    public void addOrUpdateCorpSuiteAuth(CorpSuiteAuthDO corpSuiteAuthDO);

    /**
     * 根据suiteKey,corpId查询授权信息
     *
     * @param corpId
     * @param suiteKey
     * @return
     */
    public CorpSuiteAuthDO getCorpSuiteAuth(@Param("corpId") String corpId, @Param("suiteKey") String suiteKey);


    /**
     * 根据suiteKey,corpId解除企业对套件的授权
     *
     * @param corpId
     * @param suiteKey
     * @return
     */
    public void deleteCorpSuiteAuth(@Param("corpId") String corpId, @Param("suiteKey") String suiteKey);

    /**
     * 分页查询被授权的企业
     *
     * @param suiteKey
     * @param startRow
     * @param pageSize
     * @return
     */
    List<CorpSuiteAuthDO> getCorpSuiteAuthByPage(@Param("suiteKey") String suiteKey, //
                                                 @Param("startRow") int startRow, //
                                                 @Param("pageSize") int pageSize);//
}
