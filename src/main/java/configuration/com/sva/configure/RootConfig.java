/**   
 * @Title: RootConfig.java 
 * @Package com.sva.configure 
 * @Description: ContextLoaderListener应用上下文
 * @author labelCS   
 * @date 2018年1月4日 下午3:50:57 
 * @version V1.0   
 */
package com.sva.configure;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/** 
 * @ClassName: RootConfig 
 * @Description: ContextLoaderListener应用上下文
 * @author labelCS 
 * @date 2018年1月4日 下午3:50:57 
 *  
 */
@Configuration
@Import(DataAccessConfig.class)
public class RootConfig
{

}
