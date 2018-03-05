/**   
 * @Title: SystemService.java 
 * @Package com.sva.service 
 * @Description: 系统服务  
 * @author labelCS   
 * @date 2018年1月29日 上午10:52:21 
 * @version V1.0   
 */
package com.sva.service;

import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.sva.common.weixin.utils.WeixinUtil;
import com.sva.dao.AccountDao;
import com.sva.dao.WinningRecordDao;
import com.sva.model.AccountModel;

/** 
 * @ClassName: SystemService 
 * @Description: 系统服务 
 * @author labelCS 
 * @date 2018年1月29日 上午10:52:21 
 *  
 */
@Service
public class SystemService
{
    /** 
     * @Fields daoAccount : 账户dao 
     */ 
    @Autowired
    private AccountDao daoAccount;
    
    /** 
     * @Fields daoRecord : 获奖记录dao
     */ 
    @Autowired
    private WinningRecordDao daoRecord;
    
    @Value("${rate.initRate}")
    private String initRate;
    
    /** 
     * @Fields serverUrl : 外网可以访问的服务器地址 
     */ 
    @Value("${server.url}")
    private String serverUrl;
    
    /** 
     * @Title: refresh 
     * @Description: 初始化数据库  
     */
    public void refresh(){
        // 员工在线时长归零
        daoAccount.refreshAccount();
        // 部门获奖概率恢复初始值
        String[] initRateArry = initRate.split(",");
        for(int i = 0; i < initRateArry.length; i++){
        	daoRecord.refreshDeptRate((i + 1) + "",Integer.parseInt(initRateArry[i]));
        }
        // 员工获奖记录清空
        daoRecord.refreshRecord();
    }
    
    /** 
     * @Title: pushAd 
     * @Description: 将广告推送给微信用户 
     * @param image
     * @param text 
     */
    public void pushAd(String image, String title, String text){
        // 获取推送对象
        List<AccountModel> list = daoAccount.getAdReceiver();
        // 推送消息
        if(StringUtils.isEmpty(image)){
            for(AccountModel am:list){
                WeixinUtil.pushText(am.getOpenid(), text);
            }
        }else{
            for(AccountModel am:list){
                WeixinUtil.pushNews(
                        am.getOpenid(), 
                        title, 
                        text, 
                        "", 
                        "http://"+serverUrl+"/sva/upload/ad/" + image
                );
            }
        }
    }
}
