package cn.itsource.basic.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableWebSecurity(debug = false)//开启安全验证并不打印日志
@EnableResourceServer//开启资源服务器
@EnableGlobalMethodSecurity(prePostEnabled = true)//开启安全验证的方法注解
public class MyResourceServerConfigurerAdapter extends ResourceServerConfigurerAdapter {
    @Value("${hrm.resource.id}")//放入公共资源管理权限配置 从yml里获取值
    public String RESOURCE_ID;


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
    /**秘钥*/
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
     * 远程资源访问服务
     * @return
     */
   /* @Bean
    public ResourceServerTokenServices resourceServerTokenServices(){
        RemoteTokenServices services = new RemoteTokenServices();//创建远程服务
        services.setCheckTokenEndpointUrl("http://localhost:3000/oauth/check_token");//远程操作的url
        services.setClientId("webapp");//客户端id
        services.setClientSecret("secret");//秘钥
        return services;
    }*/
    /**
     * 远程校验安全设置
     * @param resources
     * @throws Exception
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        //验证资源服务
        resources.resourceId(RESOURCE_ID)//资源id
                //.tokenServices(resourceServerTokenServices());//服务校验
        .tokenStore(tokenStore());
    }

    /**
     * 路径设置
     * @param http
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/**").access("#oauth2.hasScope('all')")//所有资源都需要校验oauth2方式
        .and().csrf().disable()//关闭跨域伪造检查
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);//会话方式 使用了token不再做数据的存储
    }
}
