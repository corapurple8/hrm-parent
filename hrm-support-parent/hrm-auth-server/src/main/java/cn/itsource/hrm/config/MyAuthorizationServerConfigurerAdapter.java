package cn.itsource.hrm.config;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import javax.sql.DataSource;

/**
 * 鉴权中心设置类
 */
//@Configuration
//@EnableAuthorizationServer//开启认证服务器管理
public class MyAuthorizationServerConfigurerAdapter extends AuthorizationServerConfigurerAdapter {


    @Autowired//注入数据库
    private DataSource dataSource;

    @Autowired//注入加密器 在安全配置类有
    private PasswordEncoder passwordEncoder;

    @Autowired//注入认证管理 在安全配置类有
    private AuthenticationManager authenticationManager;


    /**
     * 令牌端点 安全路径设置
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        //将token校验获取设置为通行
        security.tokenKeyAccess("permitAll()")
                .checkTokenAccess("permitAll()")
                .allowFormAuthenticationForClients();//允许客户端使用表单验证

    }

    /**
     * 客户详情服务
     * @return
     */
    @Bean
    public ClientDetailsService clientDetailsService(){
        //客户端存储
        JdbcClientDetailsService jdbcClientDetailsService = new JdbcClientDetailsService(dataSource);
        //密码加密后再返回
        jdbcClientDetailsService.setPasswordEncoder(passwordEncoder);
        return jdbcClientDetailsService;
    }
    /**
     * 客户端管理
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetailsService());
    }

    /**
     * 密码授权模式
     * @return
     */
    @Bean
    public AuthorizationCodeServices authorizationCodeServices(){
        return new JdbcAuthorizationCodeServices(dataSource);
    }

    /**
     * token内存仓库  存储方式改为jwt 则不需要远端验证
     * 生成需要增强器
     * @return
     */
    @Bean
    public TokenStore tokenStore(){
        //return new InMemoryTokenStore();
        return new JwtTokenStore(jwtAccessTokenConverter());
    }
    public static final String SIGN_KEY = "123";

    /**
     * 为了生成jwt仓库存储的增强器 需与tokenservice 共享
     * @return
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter(){
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setSigningKey(SIGN_KEY);
        return jwtAccessTokenConverter;
    }

    /**
     * token 相关存储 创建 刷新 获取 设置
     * @return
     */
    @Bean
    public AuthorizationServerTokenServices tokenServices(){
        DefaultTokenServices tokenServices = new DefaultTokenServices();//唯一子类
        tokenServices.setClientDetailsService(clientDetailsService());//客户端详情
        tokenServices.setSupportRefreshToken(true);//允许刷新
        tokenServices.setTokenStore(tokenStore());//token存储在内存中//存储在jwt仓库中
        tokenServices.setAccessTokenValiditySeconds(7200);//创建token
        tokenServices.setRefreshTokenValiditySeconds(7200);//刷新token时间
        //设置token服务的增强器
        tokenServices.setTokenEnhancer(jwtAccessTokenConverter());
        return tokenServices;
    }
    /**
     * 管理授权码和令牌
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)//认证管理者
                .authorizationCodeServices(authorizationCodeServices())//授权码验证模式 密码jdbc
                .tokenServices(tokenServices())//token服务
                .allowedTokenEndpointRequestMethods(HttpMethod.POST);//使用post方法获取token
    }
}
