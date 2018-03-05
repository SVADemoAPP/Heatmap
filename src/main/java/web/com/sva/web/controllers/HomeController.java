package com.sva.web.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.sva.model.PrizeModel;
import com.sva.service.LotteryService;

/**
 * <p>Title:HomeController</p>
 * <p>Description:页面菜单跳转controller</p>
 * <p>Company: ICS</p>
 * @author label
 * @date 2016年6月30日 下午2:59:26
 */
@Controller
@RequestMapping(value = "/home")
public class HomeController
{
    @Autowired
    private LotteryService lottery;
    
    private static final Logger LOG = Logger.getLogger(HomeController.class);

    /** 
     * @Title: showSvaMng 
     * @Description: 抽奖起始页
     * @param model
     * @return 
     */
    @RequestMapping(value = "/showMain", method = {RequestMethod.GET})
    public String showMain()
    {
        return "web/main";
    }
    
    /** 
     * @Title: showLottery 
     * @Description: 抽奖页面
     * @return 
     */
    @RequestMapping(value = "/showLottery/{prizeCode}", method = {RequestMethod.GET})
    public String showLottery(@PathVariable("prizeCode")String prizeCode, Model model){
        PrizeModel p = lottery.getPrizeDetail(prizeCode);
        model.addAttribute("prizeCode", prizeCode);
        model.addAttribute("prize", p.getName());
        String linkUrl = "";
        if("6".equals(prizeCode)){
            linkUrl = "web/luckyLottery";
        }else{
            linkUrl = "web/lottery";
        }
        return linkUrl;
    }
    
    /** 
     * @Title: showPushAd 
     * @Description: 广告推送界面 
     * @return 
     */
    @RequestMapping(value = "/showPushAd", method = {RequestMethod.GET})
    public String showPushAd(@ModelAttribute("info") String info, Model model){
        model.addAttribute("info", info);
        return "web/pushAd";
    }
    
    /** 
     * @Title: showCollectRecord 
     * @Description: 显示集卡兑奖记录
     * @return 
     */
    @RequestMapping(value = "/record", method = {RequestMethod.GET})
    public String showCollectRecord(){
        return "web/collectCardRecord";
    }

    /** 
     * @Title: handleException 
     * @Description: 未知异常处理
     * @param ex
     * @return 
     */
    @ExceptionHandler(RuntimeException.class)
    public String handleException(RuntimeException ex, Model model)
    {
        String info = "未知错误: " + ex.getMessage();
        model.addAttribute("info", info);
        return "error";
    }
}
