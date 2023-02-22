package com.yl.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * 在此配置类中配置令牌的生成，存储策略，验签方式(令牌合法性)。
 */
@Configuration
public class TokenConfig {

    /**
     * JWT 令牌签名时使用的密钥(可以理解为盐值加密中的盐)
     * 1)生成的令牌需要这个密钥进行签名
     * 2)获取的令牌需要使用这个密钥进行验签(校验令牌合法性，是否被篡改过)
     */
    private static final String SIGNING_KEY = "auth";

    /**
     * 配置令牌的存储策略,对于oauth2规范中提供了这样的几种策略
     * 1)JdbcTokenStore(这里是要将token存储到关系型数据库)
     * 2)RedisTokenStore(这是要将token存储到redis数据库-key/value)
     * 3)JwtTokenStore(这里是将产生的token信息存储客户端，并且token
     * 中可以以自包含的形式存储一些用户信息)
     * 4)....
     */
    @Bean
    public TokenStore tokenStore() {
        //这里采用JWT方式生成和存储令牌信息
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    /**
     * 配置令牌的创建及验签方式
     * 基于此对象创建的令牌信息会封装到OAuth2AccessToken类型的对象中
     * 然后再存储到TokenStore对象，外界需要时，会从tokenStore进行获取。
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter =
                new JwtAccessTokenConverter();
        //JWT令牌构成：header(签名算法，令牌类型),payload(数据部分),Signing(签名)
        //这里的签名可以简单理解为加密，加密时会使用header中算法以及我们自己提供的密钥，
        //这里加密的目的是为了防止令牌被篡改。（这里密钥要保管好，要存储在服务端）
        jwtAccessTokenConverter.setSigningKey(SIGNING_KEY);//设置密钥
        return jwtAccessTokenConverter;
    }

}
