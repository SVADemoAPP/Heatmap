/**   
 * @Title: AppWideExcptionHandler.java 
 * @Package com.sva.web.controllers 
 * @Description: 全局控制器位置异常处理
 * @author labelCS   
 * @date 2017年12月27日 下午4:15:03 
 * @version V1.0   
 */
package com.sva.web.controllers;

import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/** 
 * @ClassName: AppWideExcptionHandler 
 * @Description: 全局控制器位置异常处理 
 * @author labelCS 
 * @date 2017年12月27日 下午4:15:03 
 *  
 */
@ControllerAdvice
public class AppWideExcptionHandler
{
    /** 
     * @Title: handleException 
     * @Description: 未知异常处理方法
     * @param ex
     * @return 
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public Map<String, Object> handleException(RuntimeException ex)
    {
        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        String info = "未知错误: " + ex.getMessage();
        modelMap.put("error", info);
        return modelMap;

    }
}
