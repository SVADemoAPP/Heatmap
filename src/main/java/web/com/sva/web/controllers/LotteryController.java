/**   
 * @Title: LotteryController.java 
 * @Package com.sva.web.controllers 
 * @Description: 抽奖页面controller
 * @author labelCS   
 * @date 2018年1月15日 上午11:14:11 
 * @version V1.0   
 */
package com.sva.web.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.sva.common.weixin.utils.WeixinUtil;
import com.sva.model.AccountModel;
import com.sva.service.LotteryService;
import com.sva.service.PushWeixin;
import com.sva.web.models.LotteryInputModel;
import com.sva.web.models.WinningInfoModel;
import com.sva.web.models.extension.WinningInfoExtension;

/** 
 * @ClassName: LotteryController 
 * @Description: 抽奖页面controller
 * @author labelCS 
 * @date 2018年1月15日 上午11:14:11 
 *  
 */
@Controller
@RequestMapping(value = "/lottery")
public class LotteryController
{
    @Autowired
    LotteryService service;
    
    /** 
     * @Fields serverUrl : 外网可以访问的服务器地址 
     */ 
    @Value("${server.url}")
    private String serverUrl;
    
    /** 
     * @Title: getNumber 
     * @Description: 抽取中奖员工 
     * @param model
     * @param result
     * @return 
     */
    @RequestMapping(value = "/getNumber", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> getNumber(@Valid @RequestBody LotteryInputModel model, BindingResult result){
        Map<String, Object> modelMap = new HashMap<String, Object>();
        // 校验用户输入
        if(result.hasErrors()){
            modelMap.put("error", result.getAllErrors());
            return modelMap;
        }
        // 具体中奖逻辑
        AccountModel winner = service.getWinningEmployee(model.getPrizeCode());
        // 推送微信
        PushWeixin thread = new PushWeixin();
        thread.setModel(winner);
        thread.setUrlLink("http://"+serverUrl+"/sva/weixin/mine?openid="+winner.getOpenid());
        thread.setMessage("恭喜您中奖了，请在规定时间内取领奖页面确认");
        new Thread(thread).start();
        
        modelMap.put("returnCode", "1");
        modelMap.put("data", winner);
        
        return modelMap;
    }
    
    /** 
     * @Title: getPrizeCount 
     * @Description: 查看剩余数额 
     * @param model
     * @param result
     * @return 
     */
    @RequestMapping(value="/getPrizeCount", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> getPrizeCount(@Valid @RequestBody LotteryInputModel model, BindingResult result){
        Map<String, Object> modelMap = new HashMap<String, Object>();
        // 校验用户输入
        if(result.hasErrors()){
            modelMap.put("error", result.getAllErrors());
            return modelMap;
        }
        // 查询奖品余额
        int r = service.getRemainPrizeCount(model.getPrizeCode());
        modelMap.put("returnCode", "1");
        modelMap.put("data", r);
        
        return modelMap;
    }
    
    /** 
     * @Title: saveWinningRecord 
     * @Description: 记录中奖信息 
     * @param model
     * @param result
     * @return 
     */
    @RequestMapping(value="/saveWinningRecord", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> saveWinningRecord(@Valid @RequestBody WinningInfoModel model, BindingResult result){
        Map<String, Object> modelMap = new HashMap<String, Object>();
        // 校验用户输入
        if(result.hasErrors()){
            modelMap.put("error", result.getAllErrors());
            return modelMap;
        }
        service.saveWinningRecord(WinningInfoExtension.toWinningRecordModel(model));
        modelMap.put("returnCode", 1);
        return modelMap;
    }
    
    /** 
     * @Title: recordAtServer 
     * @Description: 记录抽奖开始计时时的时间 
     * @param accountId
     * @return 
     */
    @RequestMapping(value="/recordAtServer", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> recordAtServer(String accountId, String code){
        Map<String, Object> modelMap = new HashMap<String, Object>();
        WeixinUtil.winnerId = accountId;
        WeixinUtil.winnerTime = System.currentTimeMillis()/1000;
        WeixinUtil.winningCode = code;
        modelMap.put("returnCode", 1);
        return modelMap;
    }
    
    @RequestMapping(value = "/pushSse", method = { RequestMethod.GET })
    public void pushSse(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        resp.setContentType("text/event-stream");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("Cache-Control", "no-cache");
        resp.setHeader("Pragma", "no-cache");
        resp.setDateHeader("Expires", 0);

        PrintWriter writer = resp.getWriter();
        writer.println("retry: 1000"); // 设置请求间隔时间
        String msg = "";
        if (StringUtils.isNotEmpty(WeixinUtil.winnerId) && WeixinUtil.winnerId.equals(id)) {
            // id匹配上才算中奖
            long winnerTime = WeixinUtil.winnerTime;
            if (winnerTime > 0) {
                int restTime = 60 + (int) ((winnerTime - System.currentTimeMillis() / 1000));
                if (restTime < 0) {
                    msg = "overtime";
                    WeixinUtil.winnerTime = 0;
                    WeixinUtil.winnerId = "";
                    WeixinUtil.winningCode = "";
                } else {
                    msg = "winner:" + restTime;
                }
            }
        }else{
            msg = "closed";
        }
        writer.println("data: " + msg + "\n");
        writer.flush();
    }
    
    /** 
     * @Title: getCandidate 
     * @Description: 幸运奖的中奖候选者 
     * @return 
     */
    @RequestMapping(value = "/getCandidate", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> getCandidate(){
        Map<String, Object> modelMap = new HashMap<String, Object>();
        // 具体中奖逻辑
        List<String> candidates = service.getLuckyCandidate();
        
        modelMap.put("returnCode", "1");
        modelMap.put("data", candidates);
        
        return modelMap;
    }
    
    /** 
     * @Title: getLuckyWinners 
     * @Description: 获取幸运奖中奖者
     * @return 
     */
    @RequestMapping(value = "/getLuckyWinners", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> getLuckyWinners(){
        Map<String, Object> modelMap = new HashMap<String, Object>();
        // 具体中奖逻辑
        List<AccountModel> winners = service.getLuckyWinners();
        if(!winners.isEmpty()){
         // 推送微信
            PushWeixin thread = new PushWeixin();
            thread.setModels(winners);
            thread.setMessage("恭喜您中了幸运奖！详细信息请在 获奖页面查看");
            thread.setUrlLink("http://"+serverUrl+"/sva/weixin/skipPrize?openid=");
            thread.setUrlImage("http://"+serverUrl+"/sva/images/prize_6.png");
            new Thread(thread).start();
        }
        
        modelMap.put("returnCode", "1");
        modelMap.put("data", winners);
        
        return modelMap;
    }
}
