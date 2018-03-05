/**   
 * @Title: DemoWebAppInitializer.java 
 * @Package com.sva.configure 
 * @Description: 配置DispatcherServlet
 * @author labelCS   
 * @date 2018年1月4日 下午3:44:17 
 * @version V1.0   
 */
package com.sva.configure;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/** 
 * @ClassName: DemoWebAppInitializer 
 * @Description: 配置DispatcherServlet
 * @author labelCS 
 * @date 2018年1月4日 下午3:44:17 
 *  
 */
public class DemoWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer
{

    /* (非 Javadoc) 
     * <p>Title: getRootConfigClasses</p> 
     * <p>Description: ContextLoaderListener应用上下文</p> 
     * @return 
     * @see org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer#getRootConfigClasses() 
     */
    @Override
    protected Class<?>[] getRootConfigClasses()
    {
        return new Class<?>[]{RootConfig.class, SecurityConfig.class};
    }

    /* (非 Javadoc) 
     * <p>Title: getServletConfigClasses</p> 
     * <p>Description: spring 应用上下文</p> 
     * @return 
     * @see org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer#getServletConfigClasses() 
     */
    @Override
    protected Class<?>[] getServletConfigClasses()
    {
        return new Class<?>[]{WebConfig.class};
    }

    /* (非 Javadoc) 
     * <p>Title: getServletMappings</p> 
     * <p>Description: 映射DispatcherServlet</p> 
     * @return 
     * @see org.springframework.web.servlet.support.AbstractDispatcherServletInitializer#getServletMappings() 
     */
    @Override
    protected String[] getServletMappings()
    {
        return new String[]{"/"};
    }
}
