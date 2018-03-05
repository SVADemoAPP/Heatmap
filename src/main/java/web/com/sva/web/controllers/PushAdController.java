/**   
 * @Title: PushAdController.java 
 * @Package com.sva.web.controllers 
 * @Description: 广告推送Controller 
 * @author labelCS   
 * @date 2018年1月19日 下午2:34:56 
 * @version V1.0   
 */
package com.sva.web.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.sva.common.ConvertUtil;
import com.sva.service.SystemService;

/** 
 * @ClassName: PushAdController 
 * @Description: 广告推送Controller
 * @author labelCS 
 * @date 2018年1月19日 下午2:34:56 
 *  
 */
@Controller
@RequestMapping(value="/pushAd")
public class PushAdController
{
    
    @Autowired
    private SystemService service;
    
    /** 
     * @Title: upload 
     * @Description: 上传广告 
     * @param image
     * @param txt
     * @param request
     * @return
     * @throws SQLException 
     */
    @RequestMapping(value = "/upload", method = {RequestMethod.POST})
    public String uploadAndPush(
            @RequestParam(value = "image", required = false) MultipartFile image,
            HttpServletRequest request, RedirectAttributes model) throws SQLException{
        String result = "";
        String fileName = "";
        if (image != null && StringUtils.isNotEmpty(image.getOriginalFilename())){
            fileName = image.getOriginalFilename();
            String path = request.getSession().getServletContext()
                    .getRealPath("/WEB-INF/upload/ad");
            String ext = fileName.substring(fileName.lastIndexOf('.'));
            fileName = "ad_" + ConvertUtil.dateFormat(new Date(), "yyyyMMddHHmmss") + ext;
            
            File targetFile = new File(path, fileName);
            if (!targetFile.exists())
            {
                targetFile.mkdirs();
            }
            // 修改
            try
            {
                image.transferTo(targetFile);
            }
            catch (Exception e)
            {
                result = e.getMessage();
            }
        }
        
        model.addFlashAttribute("info", result);
        return "redirect:/home/showPushAd";
    }
    
    /**   
     * @Title: getImageList   
     * @Description: 获取广告图片列表   
     * @param request
     * @return：Map<String,Object>       
     * @throws   
     */ 
    @RequestMapping(value = "/getImageList", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> getImageList(HttpServletRequest request){
        Map<String, Object> result = new HashMap<String, Object>();
        // 广告文件夹
        String path = request.getSession().getServletContext()
                .getRealPath("/WEB-INF/upload/ad");
        File file = new File(path);
        String[] filelist = file.list();
        
        result.put("data", filelist);
        return result;
    }
    
    /**   
     * @Title: pushWeixin   
     * @Description: 微信推送广告接口   
     * @param imgName
     * @param txt
     * @return：Map<String,Object>       
     * @throws   
     */ 
    @RequestMapping(value = "/pushWeixin", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> pushWeixin(String imgName,String title, String txt){
        Map<String, Object> result = new HashMap<String, Object>();
        if(StringUtils.isEmpty(txt)){
            result.put("data", "文本不能为空！");
        }else{
            // 推送微信
            service.pushAd(imgName, title, txt);
            result.put("data", "消息已推送");
        }
        
        return result;
    }
}
