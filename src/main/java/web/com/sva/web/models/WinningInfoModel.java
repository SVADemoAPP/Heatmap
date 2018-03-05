/**   
 * @Title: WinningInfoModel.java 
 * @Package com.sva.web.models 
 * @Description: 中奖信息 
 * @author labelCS   
 * @date 2018年1月18日 下午3:42:50 
 * @version V1.0   
 */
package com.sva.web.models;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/** 
 * @ClassName: WinningInfoModel 
 * @Description: 中奖信息 
 * @author labelCS 
 * @date 2018年1月18日 下午3:42:50 
 *  
 */
public class WinningInfoModel
{
    
    /** 
     * @Fields accountId : 获奖账户 
     */ 
    @NotNull(message="{NotNull.WinningInfoModel.accountId}")
    private int accountId;
    
    /** 
     * @Fields prizeCode : 奖品代码 
     */ 
    @NotNull(message="{NotNull.WinningInfoModel.prizeCode}")
    private int prizeCode;
    
    /** 
     * @Fields received : 是否领取(0:未领取;1:领取) 
     */ 
    @NotNull(message="{NotNull.WinningInfoModel.received}")
    @Min(value=0,message="{Min.WinningInfoModel.received}")  
    @Max(value=1,message="{Max.WinningInfoModel.received}") 
    private int received;

    /**
     * @return the accountId
     */
    public int getAccountId()
    {
        return accountId;
    }

    /**
     * @param accountId the accountId to set
     */
    public void setAccountId(int accountId)
    {
        this.accountId = accountId;
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
     * @return the received
     */
    public int getReceived()
    {
        return received;
    }

    /**
     * @param received the received to set
     */
    public void setReceived(int received)
    {
        this.received = received;
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
        return "WinningInfo [prizeCode=" + prizeCode + ",accountId=" + accountId + ",received=" + received + "]";
    }
}
