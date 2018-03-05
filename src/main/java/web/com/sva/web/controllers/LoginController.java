/**   
 * @Title: LoginController.java 
 * @Package com.sva.web.controllers 
 * @Description: 登录页面controller
 * @author labelCS   
 * @date 2018年1月12日 下午3:48:37 
 * @version V1.0   
 */
package com.sva.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/** 
 * @ClassName: LoginController 
 * @Description: 登录页面controller
 * @author labelCS 
 * @date 2018年1月12日 下午3:48:37 
 *  
 */
@Controller
public class LoginController
{
    @RequestMapping(value = "/login")
    public String login(){
        return "account/login";
    }
}
