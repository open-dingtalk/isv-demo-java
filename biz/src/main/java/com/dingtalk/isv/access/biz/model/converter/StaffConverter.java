package com.dingtalk.isv.access.biz.model.converter;

import com.dingtalk.isv.access.api.model.EmpVO;
import com.dingtalk.open.client.api.model.corp.CorpUserDetail;

/**
 * Created by mint on 16-1-22.
 */
public class StaffConverter {


    public static EmpVO corpUser2StaffVO(CorpUserDetail corpUserDetail, String corpId){
        if(null==corpUserDetail){
            return null;
        }
        EmpVO staffVO = new EmpVO();
        staffVO.setCorpId(corpId);
        staffVO.setStaffId(corpUserDetail.getUserid());
        staffVO.setName(corpUserDetail.getName());
        staffVO.setTel(corpUserDetail.getTel());
        staffVO.setWorkPlace(corpUserDetail.getWorkPlace());
        staffVO.setRemark(corpUserDetail.getRemark());
        staffVO.setMobile(corpUserDetail.getMobile());
        staffVO.setEmail(corpUserDetail.getEmail());
        staffVO.setActive(corpUserDetail.getActive());
        //staffVO.setOrderInDepts(corpUser.getOrderInDepts());
        staffVO.setIsAdmin(corpUserDetail.getIsAdmin());
        staffVO.setIsBoss(corpUserDetail.getIsBoss());
        staffVO.setDingId(corpUserDetail.getDingId());
        //staffVO.setIsLeaderInDepts(corpUser.getIsLeaderInDepts());
        staffVO.setIsHide(corpUserDetail.getIsHide());
        staffVO.setDepartment(corpUserDetail.getDepartment());
        staffVO.setPosition(corpUserDetail.getPosition());
        staffVO.setAvatar(corpUserDetail.getAvatar());
        staffVO.setJobnumber(corpUserDetail.getJobnumber());
        staffVO.setExtattr(corpUserDetail.getExtattr());
        return staffVO;
    }

}
