/**   
 * @Title: SecurityConfig.java 
 * @Package com.sva.configure 
 * @Description: 安全配置
 * @author labelCS   
 * @date 2018年1月9日 下午3:35:52 
 * @version V1.0   
 */
package com.sva.configure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import com.sva.dao.AccountDao;
import com.sva.dao.RoleDao;
import com.sva.service.AccountService;

/** 
 * @ClassName: SecurityConfig 
 * @Description: 安全配置
 * @author labelCS 
 * @date 2018年1月9日 下午3:35:52 
 *  
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    @Autowired
    private AccountDao daoAccount;
    
    @Autowired
    private RoleDao daoRole;
    
    /* (非 Javadoc) 
     * <p>Title: configure</p> 
     * <p>Description: 设置认证方式</p> 
     * @param auth
     * @throws Exception 
     * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder) 
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.userDetailsService(new AccountService(daoAccount, daoRole));
    }
    
    /* (非 Javadoc) 
     * <p>Title: configure</p> 
     * <p>Description: 设置拦截规则</p> 
     * @param http
     * @throws Exception 
     * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.web.builders.HttpSecurity) 
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http
        	.sessionManagement().maximumSessions(5).and()
        		.and().authorizeRequests()
	                .antMatchers("/home/**","/lottery/**").hasRole("ADMIN")
	                .antMatchers("/app/**").hasRole("CUSTOMER")
	                .antMatchers("/weixin/**").permitAll()
	                .anyRequest().authenticated()
                .and().formLogin()
	                .loginPage("/login")
	                .loginProcessingUrl("/login/do")
	                //.successHandler(successHandler)设置登录成功后的处理动作即跳转
	                .permitAll()
                .and().csrf().disable();
    }
    
    /**   
     * <p>Title: configure</p>   
     * <p>Description: 忽略静态文件</p>   
     * @param web
     * @throws Exception   
     * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.web.builders.WebSecurity)   
     */  
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/js/**", "/css/**", "/fonts/**", "/plugins/**", "/images/**", "/upload/**");
    }
}
