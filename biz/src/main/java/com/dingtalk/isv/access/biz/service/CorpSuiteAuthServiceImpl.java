package com.dingtalk.isv.access.biz.service;

import com.alibaba.fastjson.JSON;
import com.dingtalk.isv.access.api.enums.suite.CorpCallBackTypeEnum;
import com.dingtalk.isv.access.api.model.CorpAppVO;
import com.dingtalk.isv.access.api.model.CorpAuthInfoVO;
import com.dingtalk.isv.access.api.model.CorpTokenVO;
import com.dingtalk.isv.access.api.model.CorpVO;
import com.dingtalk.isv.access.api.model.CorpChannelAppVO;
import com.dingtalk.isv.access.api.model.CorpSuiteAuthVO;
import com.dingtalk.isv.access.api.model.CorpSuiteCallBackVO;
import com.dingtalk.isv.access.api.model.SuiteTokenVO;
import com.dingtalk.isv.access.api.model.SuiteVO;
import com.dingtalk.isv.access.api.service.CorpManageService;
import com.dingtalk.isv.access.api.service.suite.CorpSuiteAuthService;
import com.dingtalk.isv.access.api.service.suite.SuiteManageService;
import com.dingtalk.isv.access.biz.dao.*;
import com.dingtalk.isv.access.biz.dingutil.ConfOapiRequestHelper;
import com.dingtalk.isv.access.biz.dingutil.CorpOapiRequestHelper;
import com.dingtalk.isv.access.biz.model.*;
import com.dingtalk.isv.access.biz.model.converter.CorpAppConverter;
import com.dingtalk.isv.access.biz.model.converter.CorpChannelAppConverter;
import com.dingtalk.isv.access.biz.model.converter.CorpSuiteAuthConverter;
import com.dingtalk.isv.access.common.code.ServiceResultCode;
import com.dingtalk.isv.access.common.log.format.LogFormatter;
import com.dingtalk.isv.access.common.model.ServiceResult;
import com.dingtalk.open.client.api.service.isv.IsvService;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.List;

public class CorpSuiteAuthServiceImpl implements CorpSuiteAuthService {
    private static final Logger bizLogger = LoggerFactory.getLogger("CORP_SUITE_AUTH_LOGGER");
    private static final Logger mainLogger = LoggerFactory.getLogger(CorpSuiteAuthServiceImpl.class);
    @Resource
    private CorpSuiteAuthDao corpSuiteAuthDao;
    @Resource
    private CorpAppDao corpAppDao;
    @Resource
    private AppDao appDao;
    @Autowired
    private CorpSuiteAuthFaileDao corpSuiteAuthFaileDao;
    @Resource
    private CorpChannelAppDao corpChannelAppDao;
    @Resource
    private ChannelDao channelDao;
    @Resource
    private SuiteManageService suiteManageService;
    @Resource
    private CorpManageService corpManageService;
    @Resource
    private IsvService isvService;
    @Resource
    private ConfOapiRequestHelper confOapiRequestHelper;
    @Resource
    private CorpOapiRequestHelper corpOapiRequestHelper;
    @Override
    public ServiceResult<CorpSuiteAuthVO> getCorpSuiteAuth(String corpId, String suiteKey) {
        bizLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.START,
                LogFormatter.KeyValue.getNew("corpId", corpId),
                LogFormatter.KeyValue.getNew("suiteKey", suiteKey)
        ));
        try {
            CorpSuiteAuthDO corpSuiteAuthDO = corpSuiteAuthDao.getCorpSuiteAuth(corpId, suiteKey);
            if (null == corpSuiteAuthDO) {
                bizLogger.warn(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                        "授权关系未找到",
                        LogFormatter.KeyValue.getNew("corpId", corpId),
                        LogFormatter.KeyValue.getNew("suiteKey", suiteKey)
                ));
                return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(),ServiceResultCode.SYS_ERROR.getErrMsg());
            }
            CorpSuiteAuthVO corpSuiteAuthVO = CorpSuiteAuthConverter.CorpSuiteAuthDO2CorpSuiteAuthVO(corpSuiteAuthDO);
            return ServiceResult.success(corpSuiteAuthVO);
        } catch (Exception e) {
            bizLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    "系统异常",
                    LogFormatter.KeyValue.getNew("corpId", corpId),
                    LogFormatter.KeyValue.getNew("suiteKey", suiteKey)
            ), e);
            mainLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    "系统异常",
                    LogFormatter.KeyValue.getNew("corpId", corpId),
                    LogFormatter.KeyValue.getNew("suiteKey", suiteKey)
            ), e);
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(),ServiceResultCode.SYS_ERROR.getErrMsg());
        }
    }

    /**
     * 存储或更新企业授权的永久授权码信息
     * 包含企业对套件微应用授权的永久授权码
     * 也包含企业对套件服务窗授权的永久授权码
     *
     * @param suiteKey  套件SuiteKey
     * @param tmpAuthCode  临时授权码
     */
    private ServiceResult<CorpSuiteAuthVO> saveOrUpdateCorpPCode(String suiteKey,String tmpAuthCode) {
        bizLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.START,
                LogFormatter.KeyValue.getNew("suiteKey", suiteKey),
                LogFormatter.KeyValue.getNew("tmpAuthCode", tmpAuthCode)
        ));
        try{
            //获取套件Token
            ServiceResult<SuiteTokenVO> suiteTokenSr = suiteManageService.getSuiteToken(suiteKey);
            String suiteToken = suiteTokenSr.getResult().getSuiteToken();
            //临时授权码换取永久授权码。这里注意错误码"不存在的临时授权码"。
            //因为钉钉开放平台为了保证临时授权码的正确接收。会连续推送三次临时授权码
            ServiceResult<CorpSuiteAuthVO> getPCodeSr = confOapiRequestHelper.getPermanentCode(suiteKey,tmpAuthCode,suiteToken);
            if(!getPCodeSr.isSuccess()){
                //如果真的出现了获取永久授权码失败.打印LOG,方便排查原因
                bizLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                        "获取企业对套件授权永久授权码信息失败",
                        getPCodeSr.getCode(),
                        getPCodeSr.getMessage(),
                        LogFormatter.KeyValue.getNew("suiteKey", suiteKey),
                        LogFormatter.KeyValue.getNew("tmpAuthCode", tmpAuthCode)
                ));
                return ServiceResult.failure(getPCodeSr.getCode(),getPCodeSr.getMessage());
            }
            if(null==getPCodeSr.getResult()){
                //如果是出现了不存在的临时授权码情况,返回成功,结果为null。不算作失败逻辑。
                return ServiceResult.success(null);
            }
            CorpSuiteAuthVO corpSuiteAuthVO = getPCodeSr.getResult();
            //根据企业的授权,获取企业的详细信息。
            CorpSuiteAuthDO corpSuiteAuthDO = CorpSuiteAuthConverter.CorpSuiteAuthVO2CorpSuiteAuthDO(corpSuiteAuthVO);
            corpSuiteAuthDao.addOrUpdateCorpSuiteAuth(corpSuiteAuthDO);
            return ServiceResult.success(corpSuiteAuthVO);
        } catch (Exception e) {
            String errLog = LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    "系统异常",
                    LogFormatter.KeyValue.getNew("suiteKey", suiteKey),
                    LogFormatter.KeyValue.getNew("tmpAuthCode", tmpAuthCode)
            );
            bizLogger.error(errLog, e);
            mainLogger.error(errLog, e);
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(),ServiceResultCode.SYS_ERROR.getErrMsg());
        }
    }

    @Override
    public ServiceResult<Void> deleteCorpSuiteAuth(String corpId, String suiteKey) {
        bizLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.START,
                LogFormatter.KeyValue.getNew("corpId", corpId),
                LogFormatter.KeyValue.getNew("suiteKey", suiteKey)
        ));
        try {
            corpSuiteAuthDao.deleteCorpSuiteAuth(corpId, suiteKey);
            return ServiceResult.success(null);
        } catch (Exception e) {
            bizLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    "系统异常",
                    LogFormatter.KeyValue.getNew("corpId", corpId),
                    LogFormatter.KeyValue.getNew("suiteKey", suiteKey)
            ), e);
            mainLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    "系统异常",
                    LogFormatter.KeyValue.getNew("corpId", corpId),
                    LogFormatter.KeyValue.getNew("suiteKey", suiteKey)
            ), e);
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(),ServiceResultCode.SYS_ERROR.getErrMsg());
        }
    }

    @Override
    public ServiceResult<CorpAppVO> getCorpApp(String corpId, Long appId) {
        bizLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.START,
                LogFormatter.KeyValue.getNew("corpId", corpId),
                LogFormatter.KeyValue.getNew("appId", appId)
        ));
        CorpAppDO corpAppDO = corpAppDao.getCorpApp(corpId, appId);
        if (null == corpAppDO) {
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(),ServiceResultCode.SYS_ERROR.getErrMsg());
        }
        CorpAppVO corpAppVO = CorpAppConverter.corpAppDO2CorpAppVO(corpAppDO);
        return ServiceResult.success(corpAppVO);
    }

    @Override
    public ServiceResult<Void> deleteCorpApp(String corpId, Long appId) {
        bizLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.START,
                LogFormatter.KeyValue.getNew("corpId", corpId),
                LogFormatter.KeyValue.getNew("appId", appId)
        ));
        corpAppDao.deleteCorpApp(corpId, appId);
        return ServiceResult.success(null);
    }

    @Override
    public ServiceResult<CorpSuiteAuthVO> saveOrUpdateCorpSuiteAuth(String suiteKey, String tmpAuthCode) {
        try {
            bizLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.START,
                    LogFormatter.KeyValue.getNew("tmpAuthCode", tmpAuthCode),
                    LogFormatter.KeyValue.getNew("suiteKey", suiteKey)
            ));
            ServiceResult<CorpSuiteAuthVO> savePCodeSr = saveOrUpdateCorpPCode(suiteKey,tmpAuthCode);
            if(!savePCodeSr.isSuccess()){
                return ServiceResult.failure(savePCodeSr.getCode(),savePCodeSr.getMessage());
            }
            if(null==savePCodeSr.getResult()){
                return ServiceResult.success(null);
            }
            CorpSuiteAuthVO corpSuiteAuthVO = savePCodeSr.getResult();
            //激活套件。激活完成之后,在钉钉的客户端企业员工就能够实时看到微应用了
            //这个流程可以做成异步逻辑。加速套件开通时间。
            ServiceResult<Void> activeAppSr = activeCorpApp(suiteKey, corpSuiteAuthVO.getCorpId());
            if(!activeAppSr.isSuccess()){
                bizLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                        "激活微应用失败",
                        activeAppSr.getCode(),
                        activeAppSr.getMessage()
                ));
                return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(),ServiceResultCode.SYS_ERROR.getErrMsg());
            }
            //根据企业授权获取企业信息。存储到本地DB
            //获取企业信息可以做成异步逻辑。加速套件开通时间。
            ServiceResult<Void> getCorpInfoSr = this.saveOrUpdateCorpInfo(suiteKey,corpSuiteAuthVO.getCorpId());
            if(!getCorpInfoSr.isSuccess()){
                return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(),ServiceResultCode.SYS_ERROR.getErrMsg());
            }
            //注册企业的回调,当授权企业发生变更的时候,ISV可以同步接收到企业的变更
            //这个步骤不是必须的。ISV可以根据自己的需要来选择是不是监听企业的变更
            //注册企业的回调可以做成异步逻辑。加速套件开通时间。
            ServiceResult<Void> registerSr = registerCorpCallBack(suiteKey,corpSuiteAuthVO.getCorpId());
            if(!registerSr.isSuccess()){
                return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(),ServiceResultCode.SYS_ERROR.getErrMsg());
            }
            return ServiceResult.success(corpSuiteAuthVO);
        } catch (Exception e) {
            String errLog = LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    "未知异常"+e.toString(),
                    LogFormatter.KeyValue.getNew("suiteKey", suiteKey),
                    LogFormatter.KeyValue.getNew("tmpAuthCode", tmpAuthCode)
            );
            bizLogger.error(errLog,e);
            mainLogger.error(errLog,e);
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(),ServiceResultCode.SYS_ERROR.getErrMsg());
        }
    }

    /**
     * 调用钉钉平台方法激活企业app
     *
     * @param suiteKey  套件SuiteKey
     * @param corpId    授权企业的CorpId
     */
    private ServiceResult<Void> activeCorpApp(String suiteKey, String corpId) {
        try{
            bizLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.START,
                    LogFormatter.KeyValue.getNew("suiteKey", suiteKey),
                    LogFormatter.KeyValue.getNew("corpId", corpId)
            ));
            ServiceResult<SuiteTokenVO> suiteTokenSr = suiteManageService.getSuiteToken(suiteKey);
            //这里是用SDK方式来调用激活接口。激活失败有ServiceException抛出。
            //注意业务日志和主日志
            isvService.activateSuite(suiteTokenSr.getResult().getSuiteToken(), suiteKey, corpId);
            return ServiceResult.success(null);
        }catch (Exception e){
            String errLog = LogFormatter.getKVLogData(LogFormatter.LogEvent.START,
                    LogFormatter.KeyValue.getNew("suiteKey", suiteKey),
                    LogFormatter.KeyValue.getNew("corpId", corpId)
            );
            bizLogger.error(errLog,e);
            mainLogger.error(errLog,e);
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(),ServiceResultCode.SYS_ERROR.getErrMsg());
        }
    }

    /**
     * 访问钉钉开放平平台,获取授权企业的企业信息。将企业信息存入本地DB
     * @param suiteKey  套件SuiteKey
     * @param corpId    授权企业的CorpId
     */
    private ServiceResult<Void> saveOrUpdateCorpInfo(String suiteKey, String corpId) {
        try {
            bizLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.START,
                    LogFormatter.KeyValue.getNew("suiteKey", suiteKey),
                    LogFormatter.KeyValue.getNew("corpId", corpId)
            ));
            ServiceResult<SuiteTokenVO> suiteTokenSr = suiteManageService.getSuiteToken(suiteKey);
            //1.同步企业信息,这里采用SDK的方式来访问钉钉开放平台
            ServiceResult<CorpAuthInfoVO> sr = confOapiRequestHelper.getAuthInfo(suiteKey,corpId,suiteTokenSr.getResult().getSuiteToken());
            CorpAuthInfoVO corpAuthInfoVO = sr.getResult();
            CorpVO corpVO = new CorpVO();
            corpVO.setCorpId(corpId);
            corpVO.setCorpLogoUrl(corpAuthInfoVO.getAuth_corp_info().getCorp_logo_url());
            corpVO.setCorpName(corpAuthInfoVO.getAuth_corp_info().getCorp_name());
            corpVO.setIndustry(corpAuthInfoVO.getAuth_corp_info().getIndustry());
            corpVO.setInviteCode(corpAuthInfoVO.getAuth_corp_info().getInvite_code());
            corpVO.setInviteUrl(corpAuthInfoVO.getAuth_corp_info().getInvite_url());
            ServiceResult<Void> addCorpSr = corpManageService.saveOrUpdateCorp(corpVO);
            if(!addCorpSr.isSuccess()){
                return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(),ServiceResultCode.SYS_ERROR.getErrMsg());
            }
            //2.同步企业开通的该套件下的微应用信息
            List<CorpAuthInfoVO.Agent> agentList = corpAuthInfoVO.getAuth_info().getAgent();
            for (CorpAuthInfoVO.Agent agent : agentList) {
                CorpAppVO corpAppVO = new CorpAppVO();
                corpAppVO.setCorpId(corpId);
                corpAppVO.setAgentId(agent.getAgentid());
                corpAppVO.setAgentName(agent.getAgent_name());
                corpAppVO.setLogoUrl(agent.getLogo_url());
                corpAppVO.setAppId(agent.getAppid());
                //存入DB
                CorpAppDO corpAppDO = CorpAppConverter.corpAppVO2CorpAppDO(corpAppVO);
                corpAppDao.saveOrUpdateCorpApp(corpAppDO);
            }
            //3.同步企业下的该套件下的服务窗信息
            List<CorpAuthInfoVO.ChannelAgent> channelAgentList = corpAuthInfoVO.getChannel_auth_info().getChannelAgent();
            for (CorpAuthInfoVO.ChannelAgent agent : channelAgentList) {
                CorpChannelAppVO corpChannelAppVO = new CorpChannelAppVO();
                corpChannelAppVO.setCorpId(corpId);
                corpChannelAppVO.setAgentId(agent.getAgentid());
                corpChannelAppVO.setAgentName(agent.getAgent_name());
                corpChannelAppVO.setLogoUrl(agent.getLogo_url());
                corpChannelAppVO.setAppId(agent.getAppid());
                //存入DB
                CorpChannelAppDO corpChannelAppDO = CorpChannelAppConverter.corpChannelAppVO2CorpChannelAppDO(corpChannelAppVO);
                corpChannelAppDao.saveOrUpdateCorpChannelApp(corpChannelAppDO);
            }
            return ServiceResult.success(null);
        } catch (Exception e) {
            String errLog = LogFormatter.getKVLogData(LogFormatter.LogEvent.START,
                    LogFormatter.KeyValue.getNew("suiteKey", suiteKey),
                    LogFormatter.KeyValue.getNew("corpId", corpId)
            );
            bizLogger.error(errLog,e);
            mainLogger.error(errLog,e);
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(),ServiceResultCode.SYS_ERROR.getErrMsg());
        }
    }

    @Override
    public ServiceResult<Void> handleChangeAuth(String suiteKey, String corpId) {
        bizLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.START,
                LogFormatter.KeyValue.getNew("suiteKey", suiteKey),
                LogFormatter.KeyValue.getNew("corpId", corpId)
        ));
        String suiteToken;
        String permanentCode;
        try {
            //1.获取suiteToken
            ServiceResult<SuiteTokenVO> suiteTokenSr = suiteManageService.getSuiteToken(suiteKey);
            ServiceResult<CorpSuiteAuthVO> corpSuiteAuthSr = this.getCorpSuiteAuth(corpId, suiteKey);
            if (!corpSuiteAuthSr.isSuccess() || null == corpSuiteAuthSr.getResult()) {
                bizLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                        corpSuiteAuthSr.getCode(),
                        corpSuiteAuthSr.getMessage(),
                        "授权关系不存在或者已经解除",
                        LogFormatter.KeyValue.getNew("suiteKey", suiteKey),
                        LogFormatter.KeyValue.getNew("corpId", corpId)
                ));
                return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(),ServiceResultCode.SYS_ERROR.getErrMsg());
            }
            suiteToken = suiteTokenSr.getResult().getSuiteToken();
            permanentCode = corpSuiteAuthSr.getResult().getPermanentCode();
        } catch (Exception e) {
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(),ServiceResultCode.SYS_ERROR.getErrMsg());
        }
        return ServiceResult.success(null);
    }


    @Override
    public ServiceResult<Void> handleRelieveAuth(String suiteKey, String corpId) {
        bizLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.START,
                LogFormatter.KeyValue.getNew("suiteKey", suiteKey),
                LogFormatter.KeyValue.getNew("corpId", corpId)
        ));
        //1.删除掉企业对套件的授权信息
        ServiceResult<Void> deleteAuthSr = this.deleteCorpSuiteAuth(corpId, suiteKey);
        //2.删除掉企业使用的微应用
        List<AppDO> appList = appDao.getAppBySuiteKey(suiteKey);
        for (AppDO appDO : appList) {
            corpAppDao.deleteCorpApp(corpId, appDO.getAppId());
        }
        //3.删除掉企业开通的服务窗应用
        List<ChannelDO> channelList = channelDao.getAppBySuiteKey(suiteKey);
        for (ChannelDO channelDO : channelList) {
            channelDao.deleteCorpApp(corpId,channelDO.getAppId());
        }
        //4.删除企业token.这个必须删除,一旦出现解除授权立即授权的情况,之前的token是不可用的
        corpManageService.deleteCorpToken(suiteKey, corpId);
        //5.删除企业的服务窗token.这个必须删除,一旦出现解除授权立即授权的情况,之前的token是不可用的
        corpManageService.deleteCorpChannelToken(suiteKey, corpId);
        return ServiceResult.success(null);
    }

    @Override
    public ServiceResult<List<CorpSuiteAuthVO>> getCorpSuiteAuthByPage(String suiteKey, int startRow, int pageSize) {
        bizLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.START,
                LogFormatter.KeyValue.getNew("suiteKey", suiteKey),
                LogFormatter.KeyValue.getNew("startRow", startRow),
                LogFormatter.KeyValue.getNew("pageSize", pageSize)
        ));
        System.out.println(suiteKey + "|" + startRow + "|" + pageSize);
        try {
            List<CorpSuiteAuthDO> list = corpSuiteAuthDao.getCorpSuiteAuthByPage(suiteKey, startRow, pageSize);
            System.out.println(JSON.toJSONString(list));
            if (null == list || list.isEmpty()) {
                bizLogger.warn(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                        "该套件没有被授权给任何企业",
                        LogFormatter.KeyValue.getNew("suiteKey", suiteKey)
                ));
                return ServiceResult.success(null);
            }
            List<CorpSuiteAuthVO> resultList = Lists.newArrayList();
            for (CorpSuiteAuthDO item : list) {
                resultList.add(CorpSuiteAuthConverter.CorpSuiteAuthDO2CorpSuiteAuthVO(item));
            }
            return ServiceResult.success(resultList);
        } catch (Exception e) {
            bizLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    "系统异常",
                    LogFormatter.KeyValue.getNew("suiteKey", suiteKey)
            ), e);
            mainLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    "系统异常",
                    LogFormatter.KeyValue.getNew("suiteKey", suiteKey)
            ), e);
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(),ServiceResultCode.SYS_ERROR.getErrMsg());
        }
    }

    /**
     * 注册企业的回调。当企业信息(例如企业解散,人员,部门,角色变化等等)发生变更的时候
     * @param suiteKey 套件SuiteKey
     * @param corpId   授权企业CorpId
     */
    private ServiceResult<Void> registerCorpCallBack(String suiteKey, String corpId) {
        try{
            bizLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.START,
                    LogFormatter.KeyValue.getNew("suiteKey", suiteKey),
                    LogFormatter.KeyValue.getNew("corpId", corpId)
            ));
            ServiceResult<SuiteVO> suiteSr = suiteManageService.getSuiteByKey(suiteKey);
            ServiceResult<CorpTokenVO> tokenSr = corpManageService.getCorpToken(suiteKey, corpId);
            //用于解密钉钉企业变更回调的Token。值可以自己设置,这里直接使用了套件加解密的Token
            String token = suiteSr.getResult().getToken();
            //用于解密钉钉企业变更回调的EncodingAesKey。值可以自己设置,这里直接使用了套件加解密的EncodingAesKey
            String encodingAesKey = suiteSr.getResult().getEncodingAesKey();
            //用于接收钉钉企业变更回调加解密的URL地址。地址为钉钉JAVA版本DEMO的运行ECS地址。
            String callBakUrl = "http://139.196.215.81:8080/ding-isv-access/corp/callback/"+suiteSr.getResult().getSuiteKey();
            String corpToken = tokenSr.getResult().getCorpToken();
            //查询一下是否有当前套件注册的回调
            ServiceResult<CorpSuiteCallBackVO> callBackSr = corpOapiRequestHelper.getCorpSuiteCallback(suiteKey, corpId, corpToken);
            ServiceResult<Void> registerSr;
            //请阅读开放平台文档,理解企业回调各个TAG含义。注册ISV关心的时间变更。
            //钉钉开放平台回调数据量比较大。请ISV关注自己服务器处理HTTP请求能力。
            //不要因为接收过多回调导致提供的正常业务功能不可用。
            List<String> tagList = CorpCallBackTypeEnum.getAllTag();
            if(!callBackSr.isSuccess()||null==callBackSr.getResult()){
                //查询不到那么就注册一个回调。一个新的企业开通微应用套件的时候,钉钉开放平台是没有该套件对应的回调的。
                //理论上应该一定走到这个逻辑分支
                registerSr = corpOapiRequestHelper.registerCorpCallback(suiteKey, corpId, corpToken, token, encodingAesKey, callBakUrl, tagList);
            }else{
                //如果查询到已经有了回调,那么则更新回调。
                //一个企业如果对微应用套件进行了解除授权操作,钉钉是会删除企业对该套件的回调的。
                //理论上不会走到这个分支。
                registerSr = corpOapiRequestHelper.updateCorpCallback(suiteKey, corpId, corpToken, token, encodingAesKey, callBakUrl, tagList);
            }
            if(!registerSr.isSuccess()){
                bizLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.START,
                        LogFormatter.KeyValue.getNew("suiteKey", suiteKey),
                        LogFormatter.KeyValue.getNew("corpId", corpId),
                        LogFormatter.KeyValue.getNew("tagList", JSON.toJSONString(tagList))
                ));
                return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(),ServiceResultCode.SYS_ERROR.getErrMsg());
            }
            return ServiceResult.success(null);
        }catch (Exception e){
            String errLog = LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    "系统异常",
                    LogFormatter.KeyValue.getNew("suiteKey", suiteKey),
                    LogFormatter.KeyValue.getNew("corpId", corpId)
            );
            bizLogger.error(errLog, e);
            mainLogger.error(errLog, e);
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(),ServiceResultCode.SYS_ERROR.getErrMsg());
        }
    }
}
