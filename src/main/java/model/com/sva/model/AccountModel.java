/**   
 * @Title: AccountModel.java 
 * @Package com.sva.model 
 * @Description: 账户信息model
 * @author labelCS   
 * @date 2018年1月11日 下午5:08:36 
 * @version V1.0   
 */
package com.sva.model;

import java.util.Date;

/**
 * @ClassName: AccountModel
 * @Description: 账户信息model
 * @author labelCS
 * @date 2018年1月11日 下午5:08:36
 * 
 */
public class AccountModel {
    /**
     * @Fields username : 账户名
     */
    private String username;

    /**
     * @Fields password : 账户密码
     */
    private String password;

    /**
     * @Fields realname : 用户姓名
     */
    private String realname;

    /**
     * @Fields lastLoginTime : 最近一次登录时间
     */
    private String lastLoginTime;

    /**
     * @Fields loginState : 是否登录状态
     */
    private String loginState;

    /**
     * @Fields id : 数据库id
     */
    private String id;
    
    /** 
     * @Fields gotFu : 是否集齐福卡 
     */ 
    private int gotFu;

    private String openid;
    
    private int fu1;
    private int fu2;
    private int fu3;
    private int fu4;
    private int fu5;
    
    private int remainRandomCount;
    
    private Date nextRandomTime;
    
    private String district;

    public int getRemainRandomCount() {
        return remainRandomCount;
    }

    public void setRemainRandomCount(int remainRandomCount) {
        this.remainRandomCount = remainRandomCount;
    }

    public Date getNextRandomTime() {
        return nextRandomTime;
    }

    public void setNextRandomTime(Date nextRandomTime) {
        this.nextRandomTime = nextRandomTime;
    }

    /**
     * @return the fu1
     */
    public int getFu1() {
        return fu1;
    }

    /**
     * @param fu1 the fu1 to set
     */
    public void setFu1(int fu1) {
        this.fu1 = fu1;
    }

    /**
     * @return the fu2
     */
    public int getFu2() {
        return fu2;
    }

    /**
     * @param fu2 the fu2 to set
     */
    public void setFu2(int fu2) {
        this.fu2 = fu2;
    }

    /**
     * @return the fu3
     */
    public int getFu3() {
        return fu3;
    }

    /**
     * @param fu3 the fu3 to set
     */
    public void setFu3(int fu3) {
        this.fu3 = fu3;
    }

    /**
     * @return the fu4
     */
    public int getFu4() {
        return fu4;
    }

    /**
     * @param fu4 the fu4 to set
     */
    public void setFu4(int fu4) {
        this.fu4 = fu4;
    }

    /**
     * @return the fu5
     */
    public int getFu5() {
        return fu5;
    }

    /**
     * @param fu5 the fu5 to set
     */
    public void setFu5(int fu5) {
        this.fu5 = fu5;
    }

    /**
     * @Fields phoneNo : 电话号码
     */
    private String phoneNo;

    /**
     * @Fields dept : 所属部门
     */
    private String dept;

    /**
     * @Fields lastHeartbeat : 最后一次心跳时间
     */
    private Date lastHeartbeat;

    /**
     * @Fields onLineTime : 累计在线时长
     */
    private int onLineTime;

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username
     *            the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     *            the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the realname
     */
    public String getRealname() {
        return realname;
    }

    /**
     * @param realname
     *            the realname to set
     */
    public void setRealname(String realname) {
        this.realname = realname;
    }

    /**
     * @return the lastLoginTime
     */
    public String getLastLoginTime() {
        return lastLoginTime;
    }

    /**
     * @param lastLoginTime
     *            the lastLoginTime to set
     */
    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    /**
     * @return the loginState
     */
    public String getLoginState() {
        return loginState;
    }

    /**
     * @param loginState
     *            the loginState to set
     */
    public void setLoginState(String loginState) {
        this.loginState = loginState;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the phoneNo
     */
    public String getPhoneNo() {
        return phoneNo;
    }

    /**
     * @param phoneNo
     *            the phoneNo to set
     */
    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    /**
     * @return the dept
     */
    public String getDept() {
        return dept;
    }

    /**
     * @param dept
     *            the dept to set
     */
    public void setDept(String dept) {
        this.dept = dept;
    }

    /**
     * @return the lastHeartbeat
     */
    public Date getLastHeartbeat() {
        return lastHeartbeat;
    }

    /**
     * @param lastHeartbeat
     *            the lastHeartbeat to set
     */
    public void setLastHeartbeat(Date lastHeartbeat) {
        this.lastHeartbeat = lastHeartbeat;
    }

    /**
     * @return the onLineTime
     */
    public int getOnLineTime() {
        return onLineTime;
    }

    /**
     * @param onLineTime
     *            the onLineTime to set
     */
    public void setOnLineTime(int onLineTime) {
        this.onLineTime = onLineTime;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    /**
     * @return the gotFu
     */
    public int getGotFu()
    {
        return gotFu;
    }

    /**
     * @param gotFu the gotFu to set
     */
    public void setGotFu(int gotFu)
    {
        this.gotFu = gotFu;
    }

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}
    
}
