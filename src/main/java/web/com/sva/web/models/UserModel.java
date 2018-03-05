/**   
 * @Title: AccountModel.java 
 * @Package com.sva.web.models 
 * @Description: 用户认证model
 * @author labelCS   
 * @date 2018年1月11日 下午3:56:20 
 * @version V1.0   
 */
package com.sva.web.models;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/** 
 * @ClassName: AccountModel 
 * @Description: 用户认证model
 * @author labelCS 
 * @date 2018年1月11日 下午3:56:20 
 *  
 */
public class UserModel implements UserDetails
{
    /** 
     * @Fields serialVersionUID : 序列ID
     */ 
    private static final long serialVersionUID = -6651234372104119492L;

    /** 
     * @Fields username : 用户名
     */ 
    private String username;
    
    /** 
     * @Fields password : 密码
     */ 
    private String password;
    
    /** 
     * @Fields enabled : 是否启用
     */ 
    private boolean enabled;
    
    /** 
     * @Fields authorities : 权限列表
     */ 
    private Collection<? extends GrantedAuthority> authorities;
    
    /** 
     * <p>Title: </p> 
     * <p>Description: 构造函数</p> 
     * @param username
     * @param password
     * @param enabled
     * @param authorities 
     */
    public UserModel(String username, String password, boolean enabled,
            Collection<? extends GrantedAuthority> authorities){
        super();  
        this.username = username;  
        this.password = password;  
        this.enabled = enabled;  
        this.authorities = authorities;
    }

    /* (非 Javadoc) 
     * <p>Title: getAuthorities</p> 
     * <p>Description: 权限列表</p> 
     * @return 
     * @see org.springframework.security.core.userdetails.UserDetails#getAuthorities() 
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return authorities;
    }

    /* (非 Javadoc) 
     * <p>Title: getPassword</p> 
     * <p>Description: 密码</p> 
     * @return 
     * @see org.springframework.security.core.userdetails.UserDetails#getPassword() 
     */
    @Override
    public String getPassword()
    {
        return password;
    }

    /* (非 Javadoc) 
     * <p>Title: getUsername</p> 
     * <p>Description: 用户名</p> 
     * @return 
     * @see org.springframework.security.core.userdetails.UserDetails#getUsername() 
     */
    @Override
    public String getUsername()
    {
        return username;
    }

    /* (非 Javadoc) 
     * <p>Title: isAccountNonExpired</p> 
     * <p>Description: 账户是否没有过期</p> 
     * @return 
     * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonExpired() 
     */
    @Override
    public boolean isAccountNonExpired()
    {
        return true;
    }

    /* (非 Javadoc) 
     * <p>Title: isAccountNonLocked</p> 
     * <p>Description: 账户是否没有锁定</p> 
     * @return 
     * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonLocked() 
     */
    @Override
    public boolean isAccountNonLocked()
    {
        return true;
    }

    /* (非 Javadoc) 
     * <p>Title: isCredentialsNonExpired</p> 
     * <p>Description: 认证是否没有过期</p> 
     * @return 
     * @see org.springframework.security.core.userdetails.UserDetails#isCredentialsNonExpired() 
     */
    @Override
    public boolean isCredentialsNonExpired()
    {
        return true;
    }

    /* (非 Javadoc) 
     * <p>Title: isEnabled</p> 
     * <p>Description: 账户是否可用</p> 
     * @return 
     * @see org.springframework.security.core.userdetails.UserDetails#isEnabled() 
     */
    @Override
    public boolean isEnabled()
    {
        return enabled;
    }

}
