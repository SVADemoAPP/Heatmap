/**   
 * @Title: LotteryService.java 
 * @Package com.sva.service 
 * @Description: 抽奖业务逻辑
 * @author labelCS   
 * @date 2018年1月15日 上午11:24:01 
 * @version V1.0   
 */
package com.sva.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.sva.common.weixin.utils.WeixinUtil;
import com.sva.dao.AccountDao;
import com.sva.dao.WinningRecordDao;
import com.sva.model.AccountModel;
import com.sva.model.DeptRateModel;
import com.sva.model.PrizeModel;
import com.sva.model.WinningRecordModel;

/** 
 * @ClassName: LotteryService 
 * @Description: 抽奖业务逻辑
 * @author labelCS 
 * @date 2018年1月15日 上午11:24:01 
 *  
 */
@Service
public class LotteryService
{
    @Autowired
    AccountDao daoAccount;
    
    @Autowired
    WinningRecordDao daoWinning;
    
    /** 
     * @Fields fullRate : 概率和 
     */ 
    @Value("${rate.fullRate}")
    int fullRate;
    
    /** 
     * @Fields percent : 中奖一次后，概率下降值 
     */ 
    @Value("${rate.percent}")
    int percent;
    
    /** 
     * @Fields serverUrl : 外网可以访问的服务器地址 
     */ 
    @Value("${server.url}")
    private String serverUrl;
    
    /** 
     * @Title: getWinningEmployee 
     * @Description: 获取中奖人信息 
     * @param prizeLevel
     * @return 
     */
    public AccountModel getWinningEmployee(String prizeLevel){
        // 各部门当前获奖概率
        List<DeptRateModel> deptRate = daoWinning.getDeptRate(prizeLevel);
        String dept;
        AccountModel winningPerson;
        //防止出现某个部门已经没有符合条件的中奖者候选人的情况出现
        do {
        	// 根据各部门获奖概率，抽取获奖部门
        	dept = getWinningDept(deptRate);
            // 抽取获奖部门的幸运工号
        	winningPerson = getWinningPerson(prizeLevel, dept);
		} while (winningPerson == null);
        // 计算各部门新的获奖概率
        calcNewDeptRate(deptRate, dept);
        // 返回工号
        return winningPerson;
    }
    
    /** 
     * @Title: getRemainPrizeCount 
     * @Description: 获取指定奖品剩余的额度 
     * @param prizeLevel
     * @return 
     */
    public Integer getRemainPrizeCount(String prizeLevel){
        // 获取该等级奖品总数
        int all = daoWinning.getPrizeCountByCode(prizeLevel);
        // 获取该等级已领走奖品数
        int received = daoWinning.getReceivedPrizeCountByCode(prizeLevel);
        // 返回剩余奖品数
        return all - received;
    }
    
    /** 
     * @Title: getPrizeDetail 
     * @Description: 获取奖品详情 
     * @param prizeCode
     * @return 
     */
    public PrizeModel getPrizeDetail(String prizeCode){
        return daoWinning.getPrizeDetail(prizeCode);
    }
    
    /**
     * @Title: updatePrizeDetail 
     * @Description: 更新领取信息 
     * @return 
     */
    public void updatePrizeDetail(String id,int confirm){
    	daoWinning.updatePrizeDetail(id,confirm);
    }
    
    /** 
     * @Title: getAllPrizeDetail 
     * @Description: 获取所有的奖品详情
     * @return 
     */
    public List<PrizeModel> getAllPrizeDetail(){
        return daoWinning.getAllPrizeDetail();
    }
    
    
    
    /** 
     * @Title: saveWinningRecord 
     * @Description: 记录中奖信息 
     * @param model 
     */
    public void saveWinningRecord(WinningRecordModel model){
        // 记录中奖信息
        daoWinning.saveWinningRecord(model);
        // 刷新静态变量
        WeixinUtil.winnerId = "";
        WeixinUtil.winnerTime = 0;
        WeixinUtil.winningCode = "";
        // 领取成功的情况，推送信息
        if(model.getReceived() == 1){
            // 获取中奖人信息
            AccountModel winner = daoAccount.getPersionById(model.getAccountId());
            // 获取奖品信息
            PrizeModel pm = daoWinning.getPrizeDetail(model.getPrizeCode()+"");
            model.setName(pm.getName());
            // 推送微信
            WeixinUtil.pushNews(
                    winner.getOpenid(), 
                    "中奖信息", 
                    "恭喜您中了"+model.getName()+"!", 
                    "http://"+serverUrl+"/sva/weixin/skipPrize?openid="+winner.getOpenid(), 
                    "http://"+serverUrl+"/sva/images/prize_"+model.getPrizeCode()+".png"
            );
        }
    }
    
    /** 
     * @Title: getAllWinRecord 
     * @Description: 获取所有中奖记录
     * @return 
     */
    public List<WinningRecordModel> getAllWinRecord(){
        return daoWinning.getAllWinRecord();
    }
    
    /** 
     * @Title: getWinInfoByAccount 
     * @Description: 获取指定账户的中奖信息 
     * @param openid
     * @return 
     */
    public List<WinningRecordModel> getWinInfoByAccount(String openid){
        return daoWinning.getWinInfoByAccount(openid);
    }
    
    /** 
     * @Title: getLuckyCandidate 
     * @Description: 获取幸运奖的中奖候选者 
     * @return 
     */
    public List<String> getLuckyCandidate(){
        return daoAccount.getLuckyCandidate();
    }
    
    /** 
     * @Title: getLuckyWinners 
     * @Description: 抽取20个幸运奖中奖者 
     * @return 
     */
    public List<AccountModel> getLuckyWinners(){
        // 中奖名单
        List<AccountModel> list = new ArrayList<AccountModel>();
        // 候选名单
        List<AccountModel> candidates = daoAccount.getLuckyCandidateDetail();
        // 抽20个中奖者
        for(int i=0; i<20; i++){
            // 若未到20个，候选者已抽完，则停止
            if(candidates.isEmpty()) break;
            // 获取随机数
            Random random = new Random();
            int ranNumber = random.nextInt(candidates.size());
            list.add(candidates.get(ranNumber));
            candidates.remove(ranNumber);
        }
        // 中奖名单入库
        for(AccountModel am:list){
            WinningRecordModel wrm = new WinningRecordModel();
            wrm.setAccountId(Integer.parseInt(am.getId()));
            wrm.setPrizeCode(6);
            wrm.setTime(new Date());
            wrm.setReceived(1);
            daoWinning.saveWinningRecord(wrm);
        }
        return list;
    }
    
    /** 
     * @Title: getWinningDept 
     * @Description: 根据各部门获奖概率，抽取获奖部门
     * @param deptRate
     * @return 
     */
    private String getWinningDept(List<DeptRateModel> deptRate){
        // 返回值
        String deptName = "";
        // 创建各部门随机数区间
        List<Integer> rateList = new ArrayList<Integer>();
        for(DeptRateModel d:deptRate){
            Integer r = d.getRate();
            if(rateList.size()>0){
                rateList.add(rateList.get(rateList.size() - 1) + r);
            }else{
                rateList.add(r);
            }
        }
        // 获取一个随机数
        Random random = new Random();
        int ranNumber = random.nextInt(fullRate) + 1;
        // 判断随机数属于哪一个部门
        for(int i=0; i<rateList.size(); i++){
            if(ranNumber <= rateList.get(i)){
                deptName = deptRate.get(i).getDept();
                break;
            }
        }
        
        return deptName;
    }
    
    /** 
     * @Title: getWinningPerson 
     * @Description: 抽取获奖部门的幸运工号
     * @param prizeLevel
     * @param winningDept
     * @return 
     */
    private AccountModel getWinningPerson(String prizeLevel, String winningDept){
        // 抽奖候选人名单
        List<Integer> personList = daoAccount.getCandidate(winningDept, prizeLevel);
        if(personList.isEmpty()){
        	return null;
        }
        // 从符合条件的候选人名单中筛选出集齐卡片的人员名单
        List<Integer> personCardList = daoAccount.getCandidateByCard(winningDept, prizeLevel);
        List<Integer> pondList = new ArrayList<>(personList.size() * 11);
        // 将符合中奖条件的候选人重复放入中奖池子十次
        for(int i = 1; i <= 10; i++){
        	pondList.addAll(personList);
        }
        // 将集齐卡片的中奖候选人额外多放入池子一次
        pondList.addAll(personCardList);
        // 获取随机数
        Random random = new Random();
        int ranNumber = random.nextInt(pondList.size());
        // 返回获奖者
        return daoAccount.getPersionById(pondList.get(ranNumber));
    }
    
    /** 
     * @Title: calcNewDeptRate 
     * @Description: 计算各部门新的获奖概率,并更新数据库 
     * @param deptRate
     * @param prizeLevel
     */
    private void calcNewDeptRate(List<DeptRateModel> deptRate, String winningDept){
        // 找到获奖部门，确定获奖部门新的获奖概率下降数量
        int downRate = percent;
        int i = 0;
        for(; i<deptRate.size(); i++){
            DeptRateModel d = deptRate.get(i);
            if(d.getDept().equals(winningDept)){
                int rateOld = d.getRate();
                if(rateOld < percent){
                    downRate = rateOld;
                }
                break;
            }
        }
        // 获奖部门下降的概率补给其他部门，并更新数据库
        int avgAdd = downRate/(deptRate.size()-1);
        boolean flag = true;
        for(int j=0; j<deptRate.size(); j++){
            DeptRateModel temp = deptRate.get(j);
            if(j == i){
                temp.setRate(temp.getRate() - downRate);
            }else if(flag){
                // 确保所有部门概率之和等于fullRate
                int offset = downRate - avgAdd * (deptRate.size()-1);
                temp.setRate(temp.getRate() + avgAdd + offset);
                flag = false;
            }else{
                temp.setRate(temp.getRate() + avgAdd);
            }
            // 更新数据库
            daoWinning.updateDeptRate(temp);
        }
    }
}
