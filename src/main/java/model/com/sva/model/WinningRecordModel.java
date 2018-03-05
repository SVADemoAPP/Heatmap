/**   
 * @Title: WinningRecordModel.java 
 * @Package com.sva.model 
 * @Description: 中奖信息model 
 * @author labelCS   
 * @date 2018年1月18日 下午3:43:53 
 * @version V1.0   
 */
package com.sva.model;

import java.util.Date;

/** 
 * @ClassName: WinningRecordModel 
 * @Description: 中奖信息model  
 * @author labelCS 
 * @date 2018年1月18日 下午3:43:53 
 *  
 */
public class WinningRecordModel
{
    /** 
     * @Fields id : 数据库id 
     */ 
    private int id;
    
    /** 
     * @Fields accountId : 获奖账户 
     */ 
    private int accountId;
    
    /** 
     * @Fields prizeCode : 奖品代码 
     */ 
    private int prizeCode;
    
    /** 
     * @Fields received : 是否确认(0:未确认;1:确认) 
     */ 
    private int received;
    
    /** 
     * @Fields time : 领取时间 
     */ 
    private Date time;
    
    /** 
     * @Fields username : 工号
     */ 
    private String username;
    
    /** 
     * @Fields realname : 真实姓名
     */ 
    private String realname;
    
    /** 
     * @Fields phone : 电话号码
     */ 
    private String phoneNo;
    
    /** 
     * @Fields name : 奖品等级 
     */ 
    private String name;
    
    /** 
     * @Fields desc : 奖品描述 
     */ 
    private String desc;
    
    /**
     * @Fields confirm : 是否领取(0:未领取;1:领取) 
     */
    private int confirm;

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

    /**
     * @return the time
     */
    public Date getTime()
    {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(Date time)
    {
        this.time = time;
    }

    /**
     * @return the username
     */
    public String getUsername()
    {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username)
    {
        this.username = username;
    }

    /**
     * @return the realname
     */
    public String getRealname()
    {
        return realname;
    }

    /**
     * @param realname the realname to set
     */
    public void setRealname(String realname)
    {
        this.realname = realname;
    }

    /**
     * @return the phoneNo
     */
    public String getPhoneNo()
    {
        return phoneNo;
    }

    /**
     * @param phoneNo the phoneNo to set
     */
    public void setPhoneNo(String phoneNo)
    {
        this.phoneNo = phoneNo;
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

	public int getConfirm() {
		return confirm;
	}

	public void setConfirm(int confirm) {
		this.confirm = confirm;
	}
    
}
