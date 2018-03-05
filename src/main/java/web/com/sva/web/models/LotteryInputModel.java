/**   
 * @Title: LotteryInputModel.java 
 * @Package com.sva.web.models 
 * @Description: 抽奖参数model
 * @author labelCS   
 * @date 2018年1月17日 下午3:20:10 
 * @version V1.0   
 */
package com.sva.web.models;

import org.hibernate.validator.constraints.NotEmpty;

/** 
 * @ClassName: LotteryInputModel 
 * @Description: 抽奖参数model
 * @author labelCS 
 * @date 2018年1月17日 下午3:20:10 
 *  
 */
public class LotteryInputModel
{
    @NotEmpty(message="{NotEmpty.LotteryInputModel.prizeLevel}")
    private String prizeCode;

    /**
     * @return the prizeCode
     */
    public String getPrizeCode()
    {
        return prizeCode;
    }


    /**
     * @param prizeCode the prizeCode to set
     */
    public void setPrizeCode(String prizeCode)
    {
        this.prizeCode = prizeCode;
    }

    /* (非 Javadoc) 
     * <p>Title: toString</p> 
     * <p>Description: </p> 
     * @return 
     * @see java.lang.Object#toString() 
     */
    @Override
    public String toString()
    {
        return "LotteryInput [prizeCode=" + prizeCode + "]";
    }
}
