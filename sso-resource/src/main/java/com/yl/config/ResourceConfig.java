package com.yl.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * 思考?对于一个系统而言,它资源的访问权限你是如何进行分类设计的
 * 1)不需要登录就可以访问(例如12306查票)
 * 2)登录以后才能访问(例如12306的购票)
 * 3)登录以后没有权限也不能访问(例如会员等级不够不让执行一些相关操作)
 */
@Configuration
@EnableResourceServer
//启动方法上的权限控制,需要授权才可访问的方法上添加@PreAuthorize等相关注解
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        //super.configure(http);
        //1.关闭跨域攻击
        http.csrf().disable();
        //2.放行相关请求
        http.authorizeRequests()
                .antMatchers("/resource/**")
                .authenticated()
                .anyRequest().permitAll();
    }

}
