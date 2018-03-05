/**    
 * @Title:  BasicDaoTest.java   
 * @Package com.sva.test.dao   
 * @Description: dao测试基类，用于继承   
 * @author: LabelCS    
 * @date:   2016年9月3日 下午9:45:41   
 * @version V1.0     
 */  
package com.sva.test.dao;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

/**   
 * @ClassName:  BasicDaoTest   
 * @Description: dao测试基类，用于继承 
 * @author: LabelCS  
 * @date:   2016年9月3日 下午9:45:41   
 *      
 */
@ContextConfiguration(locations= {"classpath:spring-context.xml","classpath:sva-servlet.xml"})
public class BasicDaoTest extends AbstractTransactionalJUnit4SpringContextTests {

}
