package com.dingtalk.isv.access.biz.corp.model.helper;

import com.dingtalk.isv.access.api.model.corp.StaffVO;
import com.dingtalk.open.client.api.model.corp.CorpUserDetail;

/**
 * Created by mint on 16-1-22.
 */
public class StaffConverter {


    public static StaffVO corpUser2StaffVO(CorpUserDetail corpUserDetail, String corpId){
        if(null==corpUserDetail){
            return null;
        }
        StaffVO staffVO = new StaffVO();
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
