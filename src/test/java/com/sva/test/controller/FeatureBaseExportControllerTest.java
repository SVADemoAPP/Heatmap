/**   
 * @Title: FeatureBaseExportControllerTest.java 
 * @Package com.sva.test.controller 
 * @Description: FeatureBaseExportController的测试类
 * @author labelCS   
 * @date 2017年3月20日 上午9:54:33 
 * @version V1.0   
 */
package com.sva.test.controller;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

/** 
 * @ClassName: FeatureBaseExportControllerTest 
 * @Description: FeatureBaseExportController的测试类
 * @author labelCS 
 * @date 2017年3月20日 上午9:54:33 
 *  
 */
public class FeatureBaseExportControllerTest extends BasicControllerTest
{
    /** 
     *  
     * @Title：testLogin 
     * @Description: 测试获取特征库数据  
     */  
    @Test  
    public void testGetData() {
        try{
            MvcResult result = mockMvc.perform(get("/featureBase/api/getTableData").param("placeId", "1")
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.data").isArray())
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn();
            assertNotNull(result.getResponse().getOutputStream());
        }catch(Exception e){
            assertNull(e);
        }
          
    }  
}