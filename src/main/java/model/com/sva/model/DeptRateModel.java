/**   
 * @Title: DeptRateModel.java 
 * @Package com.sva.model 
 * @Description: 各部门获奖概率model 
 * @author labelCS   
 * @date 2018年1月16日 下午7:27:13 
 * @version V1.0   
 */
package com.sva.model;

/** 
 * @ClassName: DeptRateModel 
 * @Description: 各部门获奖概率model 
 * @author labelCS 
 * @date 2018年1月16日 下午7:27:13 
 *  
 */
public class DeptRateModel
{
    private int id;
    
    private String dept;
    
    private int prizeCode;
    
    private int rate;

    /**
     * @return the id
     */
    public int getId()
    {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id)
    {
        this.id = id;
    }

    /**
     * @return the dept
     */
    public String getDept()
    {
        return dept;
    }

    /**
     * @param dept the dept to set
     */
    public void setDept(String dept)
    {
        this.dept = dept;
    }

    /**
     * @return the prizeCode
     */
    public int getPrizeCode()
    {
        return prizeCode;
    }

    /**
     * @param prizeCode the prizeCode to set
     */
    public void setPrizeCode(int prizeCode)
    {
        this.prizeCode = prizeCode;
    }

    /**
     * @return the rate
     */
    public int getRate()
    {
        return rate;
    }

    /**
     * @param rate the rate to set
     */
    public void setRate(int rate)
    {
        this.rate = rate;
    }
    
    
}
