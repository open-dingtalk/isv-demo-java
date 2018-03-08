package com.dingtalk.isv.access.biz.dao;

import com.dingtalk.isv.access.biz.model.ISVBizLockDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository("isvBizLockDao")
public interface ISVBizLockDAO {

	public void saveISVBizLock(@Param("lockKey")String lockKey,@Param("expire")Date expire);

	public ISVBizLockDO getISVBizLockByLockKey(@Param("lockKey")String lockKey);

	public void removeISVBizLock(@Param("lockKey")String lockKey);

}

