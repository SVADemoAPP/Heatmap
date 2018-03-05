/**   
 * @Title: StatisticController.java 
 * @Package com.sva.web.controllers 
 * @Description: 用户在线时长统计Controller
 * @author labelCS   
 * @date 2018年1月25日 下午4:58:21 
 * @version V1.0   
 */
package com.sva.web.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.sva.model.AccountModel;
import com.sva.service.StatisticService;

/** 
 * @ClassName: StatisticController 
 * @Description: 用户在线时长统计Controller
 * @author labelCS 
 * @date 2018年1月25日 下午4:58:21 
 *  
 */
@Controller
@RequestMapping(value = "/stat")
public class StatisticController
{
    @Autowired
    private StatisticService service;
    /** 
     * @Title: heartbeat 
     * @Description: 心跳处理 
     * @param accountId
     * @return 
     */
    @RequestMapping(value="/heartbeat", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> heartbeat(String accountId){
        Map<String, Object> modelMap = new HashMap<String, Object>();
        if(StringUtils.isEmpty(accountId)){
            modelMap.put("error", "userId is empty");
            return modelMap;
        }
        service.refreshOnline(accountId);
        modelMap.put("data", "ok");
        return modelMap;
    }
    
    /** 
     * @Title: getOnlineInfo 
     * @Description: 获取在线信息 
     * @return 
     */
    @RequestMapping(value="/getOnlineInfo", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> getOnlineInfo(){
        Map<String, Object> modelMap = new HashMap<String, Object>();
        List<AccountModel> result = service.getOnlineInfo();
        modelMap.put("data", result);
        return modelMap;
    }
    
    /** 
     * @Title: getFullFuInfo 
     * @Description: 获取所有集齐福卡的人 
     * @return 
     */
    @RequestMapping(value="/getFullFuInfo", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> getFullFuInfo(){
        Map<String, Object> modelMap = new HashMap<String, Object>();
        List<AccountModel> result = service.getFullFuInfo();
        modelMap.put("data", result);
        return modelMap;
    }
    
    /** 
     * @Title: updateFuInfo 
     * @Description: 更新福卡的集齐状态 
     * @param accountId
     * @return 
     */
    @RequestMapping(value="/updateFuInfo", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> updateFuInfo(String accountId){
        Map<String, Object> modelMap = new HashMap<String, Object>();
        if(StringUtils.isEmpty(accountId)){
            modelMap.put("error", "userId is empty");
            return modelMap;
        }
        service.updateFuInfo(accountId, 1);
        modelMap.put("data", "ok");
        return modelMap;
    }
}
