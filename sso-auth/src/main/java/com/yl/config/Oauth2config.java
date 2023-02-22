package com.yl.config;

import com.yl.service.impl.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.Arrays;

@AllArgsConstructor  // 全参构造
@EnableAuthorizationServer  // 启动认证和授权
@Configuration
public class Oauth2config extends AuthorizationServerConfigurerAdapter {

    //    @Autowired
    private AuthenticationManager authenticationManager;
    //    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    //    @Autowired
    private TokenStore tokenStore;
    //    @Autowired
    private PasswordEncoder passwordEncoder;
    //    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;


//    @Autowired
//    public Oauth2config(AuthenticationManager authenticationManager, UserDetailsServiceImpl userDetailsService, TokenStore tokenStore, PasswordEncoder passwordEncoder, JwtAccessTokenConverter jwtAccessTokenConverter) {
//        this.authenticationManager = authenticationManager;
//        this.userDetailsService = userDetailsService;
//        this.tokenStore = tokenStore;
//        this.passwordEncoder = passwordEncoder;
//        this.jwtAccessTokenConverter = jwtAccessTokenConverter;
//    }

    /**
     * 配置认证规则
     *
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        //super.configure(endpoints);
        endpoints
                //配置由谁完成认证(认证管理器)
                .authenticationManager(authenticationManager)
                //配置谁负责访问数据库(认证时需要两部分信息：一部分来自客户端，一部分来自数据库)
                .userDetailsService(userDetailsService)
                //配置进行认证的请求方式（默认支持post方式）
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
                //配置认证成功以后令牌生成和存储策略(默认令牌生成UUID.randomUUID(),存储方式为内存)
                .tokenServices(tokenService());

    }

    /**
     * 系统底层在完成认证以后会调用TokenService对象的相关方法
     * 获取TokenStore，基于tokenStore获取token对象
     *
     * @return
     */
    @Bean
    public AuthorizationServerTokenServices tokenService() {

        //1.构建TokenService对象(此对象提供了创建，获取，刷新token的方法)
        DefaultTokenServices tokenServices = new DefaultTokenServices();

        //2.设置令牌生成和存储策略(tokenStore)
        tokenServices.setTokenStore(tokenStore);

        //3.设置是否支持令牌刷新(访问令牌过期了，是否支持通过令牌刷新机制，延长令牌有效期)
        tokenServices.setSupportRefreshToken(true);

        //4.设置令牌增强(默认令牌会比较简单，使用的就是UUID, 没有业务数据，
        //就是简单随机字符串，但现在希望使用jwt方式)(不写会使用默认生成UUID)
        TokenEnhancerChain tokenEnhancer = new TokenEnhancerChain();
        tokenEnhancer.setTokenEnhancers(Arrays.asList(jwtAccessTokenConverter));
        tokenServices.setTokenEnhancer(tokenEnhancer);

        //5.设置访问令牌有效期
        tokenServices.setAccessTokenValiditySeconds(3600);//1小时

        //6.设置是否支持刷新令牌(是否支持使用刷新令牌再生成新令牌)
        tokenServices.setSupportRefreshToken(true);
        //7.设置刷新令牌有效期
        tokenServices.setRefreshTokenValiditySeconds(3600 * 72);//3天

        return tokenServices;
    }

    /**
     * 假如我们要做认证，我们输入了用户名和密码，然后点提交
     * 提交到哪里(url-去哪认证)，这个路径是否需要认证？还有令牌过期了，
     * 我们要重新生成一个令牌，哪个路径可以帮我们重新生成？
     * 如下这个方法就可以提供这个配置
     *
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                //1.定义(公开)要认证的url(permitAll()是官方定义好的)
                //公开oauth/token_key端点
                .tokenKeyAccess("permitAll()") //return this

                //2.定义(公开)令牌检查的url
                //公开oauth/check_token端点
                .checkTokenAccess("permitAll()")

                //3.允许客户端直接通过表单方式提交认证
                .allowFormAuthenticationForClients();
    }

    /**
     * 认证中心是否要给所有的客户端发令牌呢？假如不是，那要给哪些客户端
     * 发令牌，是否在服务端有一些规则的定义呢？
     * 例如：老赖不能做飞机，不能做高铁
     *
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //super.configure(clients);
        clients.inMemory()

                //定义客户端的id(客户端提交用户信息进行认证时需要这个id)
                .withClient("gateway-client")

                //定义客户端密钥(客户端提交用户信息时需要携带这个密钥)
                .secret(passwordEncoder.encode("123456"))

                //定义作用范围(所有符合规则的客户端)
                .scopes("all")

                //允许客户端基于密码方式，刷新令牌方式实现认证
                .authorizedGrantTypes("password", "refresh_token");
    }

}