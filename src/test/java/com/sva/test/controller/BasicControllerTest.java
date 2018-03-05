/**   
 * @Title: BasicControllerTest.java 
 * @Package com.sva.test.controller 
 * @Description: Controller测试基类
 * @author labelCS   
 * @date 2017年3月20日 上午10:24:13 
 * @version V1.0   
 */
package com.sva.test.controller;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
/** 
 * @ClassName: BasicControllerTest 
 * @Description: Controller测试基类
 * @author labelCS 
 * @date 2017年3月20日 上午10:24:13 
 *  
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration(value = "src/main/webapp")
@ContextConfiguration({
    "file:src/main/resources/sva-servlet.xml",
    "file:src/main/resources/spring-context.xml"
    })
public class BasicControllerTest
{
    @Autowired
    private WebApplicationContext wac;

    protected MockMvc mockMvc;

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(this.wac).build();
    }
}