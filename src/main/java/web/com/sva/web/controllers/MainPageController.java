/**   
 * @Title: MainPageController.java 
 * @Package com.sva.web.controllers 
 * @Description: web主页Controller 
 * @author labelCS   
 * @date 2018年1月19日 下午2:34:56 
 * @version V1.0   
 */
package com.sva.web.controllers;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.sva.service.LotteryService;
import com.sva.service.SystemService;
import com.sva.service.WeixinService;

/** 
 * @ClassName: MainPageController 
 * @Description: web主页Controller
 * @author labelCS 
 * @date 2018年1月19日 下午2:34:56 
 *  
 */
@Controller
@RequestMapping(value="/mainPage")
public class MainPageController
{
    @Autowired
    private LotteryService service;
    
    @Autowired
    private SystemService systemService;
    
    @Autowired
    private WeixinService weixinService;
    /** 
     * @Title: getPrizeDetail 
     * @Description: 获取所有的奖品信息
     * @return 
     */
    @RequestMapping(value="/getPrizeDetail", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> getPrizeDetail(){
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("returnCode", 1);
        model.put("data", service.getAllPrizeDetail());
        return model;
    }
    
    /** 
     * @Title: getRecord 
     * @Description: 获取中奖记录 
     * @return 
     */
    @RequestMapping(value="/getRecord", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> getRecord(){
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("returnCode", 1);
        model.put("data", service.getAllWinRecord());
        return model;
    }
    
    /** 
     * @Title: updateFuInfo 
     * @Description: 更新奖品领取状态 
     * @param accountId
     * @return 
     */
    @RequestMapping(value="/updatePrizeConfrim", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> updatePrizeConfrim(String accountId){
        Map<String, Object> modelMap = new HashMap<String, Object>();
        if(StringUtils.isEmpty(accountId)){
            modelMap.put("error", "userId is empty");
            return modelMap;
        }
        service.updatePrizeDetail(accountId, 1);
        modelMap.put("data", "ok");
        return modelMap;
    }
    
    
    /** 
     * @Title: refresh 
     * @Description: 将数据恢复到最初状态 
     * @return 
     */
    @RequestMapping(value="/refresh", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> refresh(){
        Map<String, Object> model = new HashMap<String, Object>();
        systemService.refresh();
        weixinService.fuReturnStart();
        model.put("returnCode", 1);
        return model;
    }
    
    /** 
     * @Title: sendFu 
     * @Description: 将未发放完的福发出去 
     * @return 
     */
    @RequestMapping(value="/sendFu", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> sendFu(){
        Map<String, Object> model = new HashMap<String, Object>();
        int result = weixinService.checkAndSend();
        model.put("returnCode", result);
        return model;
    }
}
