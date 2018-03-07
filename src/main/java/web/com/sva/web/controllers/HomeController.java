package com.sva.web.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.sva.model.LocationModel;
import com.sva.model.MapsModel;
import com.sva.service.HeatmapService;
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
    /** 
     * @Fields servic : 热力图服务 
     */ 
    @Autowired
    private HeatmapService service;
    
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
     * @Title: getMapInfoByPosition 
     * @Description: 获取指定楼层的地图信息 
     * @param mapId
     * @return 
     */
    @RequestMapping(value = "/getMapInfoByPosition", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> getMapInfoByPosition(@RequestParam("mapId") String mapId){
        MapsModel result = service.getMapInfoById(mapId);
        Map<String, Object> modelMap = new HashMap<String, Object>(8);
        modelMap.put("data", result);

        return modelMap;
    }
    
    /** 
     * @Title: getHeatmapData 
     * @Description: 获取热力图数据 
     * @param mapId
     * @return 
     */
    @RequestMapping(value = "/getData", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> getHeatmapData(@RequestParam("mapId") String mapId){
        List<LocationModel> result = service.getLocationData(mapId);
        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        modelMap.put("data", result);

        return modelMap;
    }
    
    /** 
     * @Title: getMapInfoByStation 
     * @Description: 获取指定站点的所有地图信息 
     * @param stationId
     * @return 
     */
    @RequestMapping(value = "/getMapInfoByStation", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> getMapInfoByStation(@RequestParam("stationId") String stationId)
    {
        List<MapsModel> result = service.getMapInfoByStation(stationId);
        Map<String, Object> modelMap = new HashMap<String, Object>(8);
        modelMap.put("data", result);

        return modelMap;
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
