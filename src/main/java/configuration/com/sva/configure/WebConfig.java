/**   
 * @Title: WebConfig.java 
 * @Package com.sva.configure 
 * @Description: SpringMVC配置类
 * @author labelCS   
 * @date 2018年1月4日 下午3:07:13 
 * @version V1.0   
 */
package com.sva.configure;

import org.hibernate.validator.HibernateValidator;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/** 
 * @ClassName: WebConfig 
 * @Description: SpringMVC配置类
 * @author labelCS 
 * @date 2018年1月4日 下午3:07:13 
 *  
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages={"com.sva"})
@ImportResource("classpath:quartz-config.xml")
public class WebConfig extends WebMvcConfigurerAdapter
{
    /** 
     * @Title: viewResolver 
     * @Description: 配置JSP视图解析器
     * @return 
     */
    @Bean
    public ViewResolver viewResolver(){
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/jsp/");
        resolver.setSuffix(".jsp");
        resolver.setExposeContextBeansAsAttributes(true);
        // 解析jstl视图，JSTL能够获取locale对象以及spring中配置的信息资源
        // resolver.setViewClass(org.springframework.web.servlet.view.JstlView.class);
        return resolver;
    }
    
    /** 
     * @Title: messageSource 
     * @Description: 配置消息源
     * @return 
     */
    @Bean
    public MessageSource messageSource(){
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages");
        messageSource.setUseCodeAsDefaultMessage(false);
        return messageSource;
    }

    /** 
     * @Title: multipartResolver 
     * @Description: 配置多媒体文件上传
     * @return 
     */
    @Bean
    public MultipartResolver multipartResolver(){
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(20971520);
        multipartResolver.setDefaultEncoding("UTF-8");
        multipartResolver.setResolveLazily(true);
        return multipartResolver;
    }
    
    /** 
     * @Title: validator 
     * @Description: 使用hibernate validator校验
     * @return 
     */
    @Bean
    public LocalValidatorFactoryBean validator()
    {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());
        bean.setProviderClass(HibernateValidator.class);
        return bean;
    }
    
    /** 
     * @Title: localeResolver 
     * @Description: 基于session的本地化资源处理器
     * @return 
     */
    @Bean
    public LocaleResolver localeResolver(){
        return new SessionLocaleResolver();
    }
    
    /** 
     * @Title: localeChangeInterceptor 
     * @Description: 本地化拦截器
     * @return 
     */
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor(){
        return new LocaleChangeInterceptor();
    }
    
    /** 
     * @Title: propertySourcesPlaceholderConfigurer 
     * @Description: 全局属性文件配置
     * @return 
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer(){
        return new PropertySourcesPlaceholderConfigurer();
    }
    
    /* (非 Javadoc) 
     * <p>Title: getValidator</p> 
     * <p>Description: 配置校验</p> 
     * @return 
     * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter#getValidator() 
     */
    @Override
    public Validator getValidator()
    {
        return validator();
    }
    
    /* (非 Javadoc) 
     * <p>Title: addResourceHandlers</p> 
     * <p>Description: 设置静态资源映射</p> 
     * @param registry 
     * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter#addResourceHandlers(org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry) 
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry)
    {
        registry.addResourceHandler("/js/**").addResourceLocations("/WEB-INF/js/");
        registry.addResourceHandler("/css/**").addResourceLocations("/WEB-INF/css/");
        registry.addResourceHandler("/fonts/**").addResourceLocations("/WEB-INF/fonts/");
        registry.addResourceHandler("/plugins/**").addResourceLocations("/WEB-INF/plugins/");
        registry.addResourceHandler("/images/**").addResourceLocations("/WEB-INF/images/");
        registry.addResourceHandler("/upload/**").addResourceLocations("/WEB-INF/upload/");
        registry.addResourceHandler("/weixin/**").addResourceLocations("/WEB-INF/weixin/");
    }
    /* (非 Javadoc) 
     * <p>Title: addInterceptors</p> 
     * <p>Description: 注册拦截器</p> 
     * @param registry 
     * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter#addInterceptors(org.springframework.web.servlet.config.annotation.InterceptorRegistry) 
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        // 注册拦截器
        registry.addInterceptor(localeChangeInterceptor());
        super.addInterceptors(registry);
    }
}
