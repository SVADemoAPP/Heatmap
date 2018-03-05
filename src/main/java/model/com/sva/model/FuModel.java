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
 * 
 * @ClassName: FuModel
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author gyr
 * @date 2018年1月24日 上午12:47:52
 *
 */
public class FuModel {

    private int id;
    private String name;
    private int totalCount;
    private int remainCount;
    private int weight;

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the totalCount
     */
    public int getTotalCount() {
        return totalCount;
    }

    /**
     * @param totalCount
     *            the totalCount to set
     */
    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    /**
     * @return the remainCount
     */
    public int getRemainCount() {
        return remainCount;
    }

    /**
     * @param remainCount
     *            the remainCount to set
     */
    public void setRemainCount(int remainCount) {
        this.remainCount = remainCount;
    }

    /**
     * @return the weight
     */
    public int getWeight() {
        return weight;
    }

    /**
     * @param weight
     *            the weight to set
     */
    public void setWeight(int weight) {
        this.weight = weight;
    }

}
