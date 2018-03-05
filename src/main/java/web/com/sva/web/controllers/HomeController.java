package com.sva.web.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.sva.web.models.HeatmapModel;

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
    
    private static final Logger LOG = Logger.getLogger(HomeController.class);
    
    /** 
     * @Title: showHeatmap 
     * @Description: 显示热力图页面 
     * @param requestModel
     * @param model
     * @return 
     */
    @RequestMapping(value="/heatmap", method={RequestMethod.GET})
    public String showHeatmap(HeatmapModel requestModel, Model model){
        model.addAttribute("param", requestModel);
        
        return "web/heatmap";
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
