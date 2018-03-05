/**   
 * @Title: RoleModel.java 
 * @Package com.sva.model 
 * @Description: 角色model
 * @author labelCS   
 * @date 2018年1月11日 下午5:38:37 
 * @version V1.0   
 */
package com.sva.model;

/** 
 * @ClassName: RoleModel 
 * @Description: 角色model 
 * @author labelCS 
 * @date 2018年1月11日 下午5:38:37 
 *  
 */
public class RoleModel
{
    /** 
     * @Fields id : 数据id
     */ 
    private String id;
    
    /** 
     * @Fields name : 角色名
     */ 
    private String name;
    
    /**
     * @return the id
     */
    public String getId()
    {
        return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(String id)
    {
        this.id = id;
    }
    /**
     * @return the name
     */
    public String getName()
    {
        return name;
    }
    /**
     * @param name the name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }
    
}
