/**   
 * @Title: PrizeModel.java 
 * @Package com.sva.model 
 * @Description: 奖品信息model
 * @author labelCS   
 * @date 2018年1月16日 下午4:41:32 
 * @version V1.0   
 */
package com.sva.model;

/** 
 * @ClassName: PrizeModel 
 * @Description: 奖品信息model
 * @author labelCS 
 * @date 2018年1月16日 下午4:41:32 
 *  
 */
public class PrizeModel
{
    /** 
     * @Fields id : 数据库id
     */ 
    private String id;
    
    /** 
     * @Fields name : 奖品等级
     */ 
    private String name;
    
    /** 
     * @Fields desc : 奖品描述
     */ 
    private String desc;
    
    /** 
     * @Fields count : 奖品总数
     */ 
    private int count;
    
    /** 
     * @Fields code : 奖品代码
     */ 
    private int code;

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

    /**
     * @return the desc
     */
    public String getDesc()
    {
        return desc;
    }

    /**
     * @param desc the desc to set
     */
    public void setDesc(String desc)
    {
        this.desc = desc;
    }

    /**
     * @return the count
     */
    public int getCount()
    {
        return count;
    }

    /**
     * @param count the count to set
     */
    public void setCount(int count)
    {
        this.count = count;
    }

    /**
     * @return the code
     */
    public int getCode()
    {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(int code)
    {
        this.code = code;
    }
    
}
