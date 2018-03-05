/**   
 * @Title: FilterInitializer.java 
 * @Package com.sva.configure 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author labelCS   
 * @date 2018年2月8日 下午4:15:42 
 * @version V1.0   
 */
package com.sva.configure;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.filter.CharacterEncodingFilter;

/** 
 * @ClassName: FilterInitializer 
 * @Description: 过滤器配置 
 * @author labelCS 
 * @date 2018年2月8日 下午4:15:42 
 *  
 */
public class FilterInitializer implements WebApplicationInitializer
{

    /* (非 Javadoc) 
     * <p>Title: onStartup</p> 
     * <p>Description: </p> 
     * @param servletContext
     * @throws ServletException 
     * @see org.springframework.web.WebApplicationInitializer#onStartup(javax.servlet.ServletContext) 
     */
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException
    {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();  
        characterEncodingFilter.setEncoding("UTF-8");  
        characterEncodingFilter.setForceEncoding(true);  
        FilterRegistration.Dynamic filter = servletContext.addFilter("characterEncodingFileter", characterEncodingFilter);  
        filter.addMappingForUrlPatterns(null, true, "/*");
        
    }

}
