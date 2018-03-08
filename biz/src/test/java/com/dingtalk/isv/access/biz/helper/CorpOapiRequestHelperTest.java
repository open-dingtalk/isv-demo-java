package com.dingtalk.isv.access.biz.helper;

import com.alibaba.fastjson.JSON;
import com.dingtalk.isv.access.api.model.CorpChatVO;
import com.dingtalk.isv.access.api.model.CorpTokenVO;
import com.dingtalk.isv.access.api.service.CorpManageService;
import com.dingtalk.isv.access.api.service.suite.SuiteManageService;
import com.dingtalk.isv.access.biz.base.BaseTestCase;
import com.dingtalk.isv.access.biz.dingutil.CorpChatOapiRequestHelper;
import com.dingtalk.isv.access.biz.dingutil.CorpDeptOapiRequestHelper;
import com.dingtalk.isv.access.biz.dingutil.CorpOapiRequestHelper;
import com.dingtalk.isv.access.biz.dingutil.ISVRequestHelper;
import com.dingtalk.isv.access.common.model.ServiceResult;
import com.dingtalk.open.client.api.model.corp.MessageBody;
import com.dingtalk.open.client.api.model.corp.MessageType;
import com.google.common.collect.Lists;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lifeng.zlf on 2017/12/1.
 */
public class CorpOapiRequestHelperTest extends BaseTestCase {
    @Resource
    private ISVRequestHelper isvRequestHelper;
    @Resource
    private CorpOapiRequestHelper corpOapiRequestHelper;
    @Resource
    private SuiteManageService suiteManageService;
    @Resource
    private CorpManageService corpManageService;
    @Resource
    private CorpDeptOapiRequestHelper corpDeptOapiRequestHelper;
    @Resource
    private CorpChatOapiRequestHelper corpChatOapiRequestHelper;
    @Test
    public void test(){
        String corpId = "ding9f50b15bccd16741";
        String ssoCorpSecret = "";
        String corpSecret = "";

        //这一组账号创建外部联系人没权限
        //corpId = "dingb428d1170d66cd7035c2f4657eb6378f";
        //corpSecret="1LSHw0oGhsky77r3qKM12nbujVl0qwuTCc56W2EM1pqzJtpu3jwvm_2RVZSXNMJz";

        String suiteKey = "suitexdhgv7mn5ufoi9ui";
        CorpTokenVO corpTokenVO = corpManageService.getCorpToken(suiteKey,corpId).getResult();
        System.err.println("corpTokenVO::::"+ JSON.toJSONString(corpTokenVO));
        /**
        ISVSSOTokenVO isvSSOTokenVO =  isvRequestHelper.getSSOToken(corpId,ssoCorpSecret).getResult();
        System.err.println("isvSSOTokenVO::::"+ JSON.toJSONString(isvSSOTokenVO));
        System.err.println(isvSSOTokenVO.getExpiredTime());
        ServiceResult<CorpSuiteCallBackVO> sr = corpOapiRequestHelper.getCorpSuiteCallback(suiteKey,corpId,corpTokenVO.getCorpToken());
        System.err.println(JSON.toJSONString(sr));
        SuiteVO suiteVO = suiteManageService.getSuiteByKey(suiteKey).getResult();
        ServiceResult<Void> updateSr = corpOapiRequestHelper.updateCorpCallback(suiteKey,corpId,corpTokenVO.getCorpToken(),suiteVO.getToken(),suiteVO.getEncodingAesKey(),"http://baidu.com", CorpCallBackTypeEnum.getAllTag());
        System.err.println(JSON.toJSONString(updateSr));
        sr = corpOapiRequestHelper.getCorpSuiteCallback(suiteKey,corpId,corpTokenVO.getCorpToken());
        System.err.println(JSON.toJSONString(sr));
        ServiceResult<List<Long>> subDeptListSr = corpDeptOapiRequestHelper.getSubDeptIdList(suiteKey,corpId,corpTokenVO.getCorpToken(),642185L);
        System.err.println("subDeptListSr::::"+JSON.toJSONString(subDeptListSr));
         **/
        CorpTokenVO isvCorpTokenVO =  isvRequestHelper.getCorpToken(corpId,corpSecret).getResult();
        System.err.println("isvCorpTokenVO::::"+ JSON.toJSONString(isvCorpTokenVO));
        List<String> chatUserList = new ArrayList<String>();
        chatUserList.add("lifeng.zlf");
        chatUserList.add("043425659249");
        chatUserList.add("06654942081038711");
        chatUserList.add("042348834225");



        List<String> chatExtList = new ArrayList<String>();
        chatExtList.add("042260794833");
        chatExtList.add("43391529240");
        chatExtList.add("0639560260641536");
        chatExtList.add("02200260401069719");
        chatExtList.add("08471724461239273");
        chatExtList.add("01592355021038074");
        chatExtList.add("083309024829170414");
        chatExtList.add("0554195521654200");
        chatExtList.add("030801316126307056");
        chatExtList.add("1547002767868863895");
        //chatExtList.add("031912320428027866");

        //ISV默认没有这个接口权限。只能用企业自己的TOKEN进行测试
        //042348834225 星峰
        //使用默认参数创建群 create {"errcode":0,"errmsg":"ok","chatid":"chat23ea4d5d3dcf7da8f05356c0b26cd946","openConversationId":"cidpuqEf0HbIMy8zsXKJJ3D1g==","conversationTag":2}
        //ServiceResult<String> createChatSr = corpChatOapiRequestHelper.createChat(suiteKey,corpId,isvCorpTokenVO.getCorpToken(),"浩倡573_0","lifeng.zlf",chatUserList,
        //        1,null,null,null);

        //1521055018802442297 星峰小号
        /** **/
        ServiceResult<String> createChatSr = corpChatOapiRequestHelper.createChat(suiteKey,corpId,isvCorpTokenVO.getCorpToken(),"浩倡573_e","1547002767868863895",null,
                1,"ext",0L,chatExtList);//042260794833 ~~  lifeng.zlf 1521055018802442297
        System.err.println("createChatSr::::"+JSON.toJSONString(createChatSr));
        ServiceResult<CorpChatVO> chatSr = corpChatOapiRequestHelper.getChat(suiteKey,corpId,isvCorpTokenVO.getCorpToken(),createChatSr.getResult());
        System.err.println("chatSr::::"+JSON.toJSONString(chatSr.getResult()));
        CorpChatVO corpChatVO = chatSr.getResult();
        /**
        String s = "{\"chatId\":\"chat58f10d9d8fbfcbaed57da8cb27e19bbb\",\"chatName\":\"浩倡573_d\",\"conversationTag\":0,\"extIdList\":[\"1547002767868863895\",\"02200260401069719\",\"08471724461239273\",\"01592355021038074\",\"083309024829170414\",\"042260794833\",\"43391529240\",\"0639560260641536\",\"0554195521654200\",\"030801316126307056\"],\"ownerUserId\":\"1521055018802442297\",\"userIdlist\":[\"lifeng.zlf\",\"042348834225\",\"043425659249\",\"06654942081038711\",\"1521055018802442297\"]}";
        CorpChatVO corpChatVO = JSON.parseObject(s,CorpChatVO.class);
        corpChatOapiRequestHelper.updateChat(suiteKey, corpId, isvCorpTokenVO.getCorpToken(), corpChatVO.getChatId(),"浩倡573换名字","lifeng.zlf",
                "emp",Lists.<String>newArrayList("0100024243958604"),null,Lists.newArrayList("01592355021038074"), null);
        **/

        String chatId = corpChatVO.getChatId();
        MessageBody.OABody message = new MessageBody.OABody();
        MessageBody.OABody.Head head = new MessageBody.OABody.Head();
        MessageBody.OABody.Body body = new MessageBody.OABody.Body();
        head.setText("HEAD");
        head.setBgcolor("ffffa328");
        head.setText("客户详情");
        body.setContent("标题 \r\n 换行");
        body.setTitle("xxxxxxxx \r\n  换行");
        message.setHead(head);
        message.setBody(body);
        message.setMessage_url("http://taobao.com");
        ServiceResult<Void> sendSr = corpChatOapiRequestHelper.sendChatMsg(suiteKey,corpId,isvCorpTokenVO.getCorpToken(),chatId, MessageType.OA,message);
        System.err.println("sendSr::::"+JSON.toJSONString(sendSr));
        /**
        ServiceResult<DingDepartmentVO> departmentVOSr = corpDeptOapiRequestHelper.getDeptById(suiteKey,corpId,corpTokenVO.getCorpToken(),642185L);
        System.err.println("departmentVOSr::::"+JSON.toJSONString(departmentVOSr));
       **/
    }

}
