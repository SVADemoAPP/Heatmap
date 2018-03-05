/**   
 * @Title: AccountDao.java 
 * @Package com.sva.dao 
 * @Description: 账户DAO
 * @author labelCS   
 * @date 2018年1月11日 下午3:47:22 
 * @version V1.0   
 */
package com.sva.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.sva.model.AccountModel;

/** 
 * @ClassName: AccountDao 
 * @Description: 账户DAO
 * @author labelCS 
 * @date 2018年1月11日 下午3:47:22 
 *  
 */
public interface AccountDao
{
    /** 
     * @Title: getAccountByName 
     * @Description: 查询账户名对应的账户信息
     * @param username
     * @return 
     */
    public AccountModel getAccountByName(String username);
    
    /** 
     * @Title: getPersionById 
     * @Description: 根据id查找对应的账户信息
     * @param id
     * @return 
     */
    public AccountModel getPersionById(int id);
    
    /** 
     * @Title: getPersionByUsername 
     * @Description: 根据用户名查找对应的账户信息 
     * @param username
     * @return 
     */
    public AccountModel getPersionByUsername(String username);
    
    /** 
     * @Title: getCandidate 
     * @Description: 查找符合抽奖条件的候选人
     * @param dept
     * @param prizeCode
     * @return 
     */
    public List<Integer> getCandidate(@Param("dept")String dept, @Param("prizeCode")String prizeCode);
    
    /** 
     * @Title: getCandidate 
     * @Description: 查找符合抽奖条件并已集齐卡片的候选人
     * @param dept
     * @param prizeCode
     * @return 
     */
    public List<Integer> getCandidateByCard(@Param("dept")String dept, @Param("prizeCode")String prizeCode);
    
    /** 
     * @Title: getLuckyCandidate 
     * @Description: 获取幸运奖的中奖候选者姓名列表
     * @return 
     */
    public List<String> getLuckyCandidate();
    
    /** 
     * @Title: getLuckyCandidateDetail 
     * @Description: 获取幸运奖的中奖候选者详细 
     * @return 
     */
    public List<AccountModel> getLuckyCandidateDetail();
    
    /** 
     * @Title: updateOnlineTime 
     * @Description: 更新在线时长 
     * @param model 
     */
    public void updateOnlineTime(AccountModel model);
    
    /** 
     * @Title: getOnlineInfo 
     * @Description: 获取在线时长信息 
     * @return 
     */
    public List<AccountModel> getOnlineInfo(int length);
    
    /** 
     * @Title: refreshAccount 
     * @Description: 更新账户信息  
     */
    public void refreshAccount();
    
    /** 
     * @Title: getAdReceiver 
     * @Description: 获取推送对象 
     * @return 
     */
    public List<AccountModel> getAdReceiver();
}
