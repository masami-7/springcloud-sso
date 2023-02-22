package com.yl.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * 构建令牌配置对象, 在微服务架构中, 登陆成功后, 可以将用户信息进行存储, 常用存储方式:
 * 1. 产生一个随机的字符串(token) 然后基于此字符串将用户信息存储到关系数据库(例如MySQL)
 * 2. 产生一个随机的字符串(token) 然后基于此字符串将用户信息存储到内存数据库(Redis)
 * 3. 基于jwt创建令牌(token), 在此令牌中存储用户信息, 这个令牌不需要写在数据库, 在客户端储存即可
 * <p>
 * 基于如上设计方案, oauth2协议中给出了具体的api实现对象, 例如:
 * 配置令牌的存储策略,对于oauth2规范中提供了这样的几种策略
 * 1)JdbcTokenStore(这里是要将token存储到关系型数据库) (用的比较少)
 * 2)RedisTokenStore(这是要将token存储到redis数据库-key/value)
 * 3)JwtTokenStore(这里是将产生的token信息存储客户端，并且token中可以以自包含的形式存储一些用户信息)(对性能比较高的分布式架构)
 */
@Configuration
public class TokenConfig {

    //这里的签名key将来可以写到配置中心
    private static final String SIGNING_KEY = "auth";

    /**
     * 定义令牌存储方案, 本次选择基于jwt令牌方式存储用户状态
     *
     * @return
     */
    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    /**
     * 配置jwt 令牌创建和解析对象
     *
     * @return
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter Converter = new JwtAccessTokenConverter();
        Converter.setSigningKey("auth");
        return Converter;
    }

}