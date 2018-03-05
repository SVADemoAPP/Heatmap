/**   
 * @Title: AccountService.java 
 * @Package com.sva.service 
 * @Description: 账户处理
 * @author labelCS   
 * @date 2018年1月11日 下午3:37:19 
 * @version V1.0   
 */
package com.sva.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.sva.dao.AccountDao;
import com.sva.dao.RoleDao;
import com.sva.model.AccountModel;
import com.sva.model.RoleModel;
import com.sva.web.models.UserModel;

/** 
 * @ClassName: AccountService 
 * @Description: 账户处理
 * @author labelCS 
 * @date 2018年1月11日 下午3:37:19 
 *  
 */
public class AccountService implements UserDetailsService
{
    private AccountDao daoAccount;
    
    private RoleDao daoRole;
    
    public AccountService(AccountDao daoAccount, RoleDao daoRole){
        this.daoAccount = daoAccount;
        this.daoRole = daoRole;
    }

    /* (非 Javadoc) 
     * <p>Title: loadUserByUsername</p> 
     * <p>Description: </p> 
     * @param arg0
     * @return
     * @throws UsernameNotFoundException 
     * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String) 
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        //查询用户信息
        AccountModel user = daoAccount.getAccountByName(username);
        if (user != null){
            //查询用户的角色
            List<RoleModel> roleList = daoRole.getRoleByAccountId(user.getId());
            List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
            // 构建权限
            for (RoleModel role: roleList){
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
            }
            return new UserModel(user.getUsername(), user.getPassword(), true, authorities);
        }
        throw new UsernameNotFoundException("用户名 '" + username + "'没有找到！");
    }
}
