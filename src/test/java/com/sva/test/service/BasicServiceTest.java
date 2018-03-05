/**   
 * @Title: BasicSeviceTest.java 
 * @Package com.sva.test.service 
 * @Description: Service测试基类
 * @author labelCS   
 * @date 2017年3月20日 下午4:10:30 
 * @version V1.0   
 */
package com.sva.test.service;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/** 
 * @ClassName: BasicSeviceTest 
 * @Description: Service测试基类
 * @author labelCS 
 * @date 2017年3月20日 下午4:10:30 
 *  
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-context.xml","classpath:sva-servlet.xml"})
public class BasicServiceTest
{

}