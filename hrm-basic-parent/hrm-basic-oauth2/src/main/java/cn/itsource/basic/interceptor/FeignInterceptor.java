package cn.itsource.basic.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 各资源服务之间添加feign请求头的配置类
 */
@Configuration//直接设置 包括component
public class FeignInterceptor implements RequestInterceptor {
    public static final String ACCESS_TOKEN_KEY="Authorization";
    @Override
    public void apply(RequestTemplate requestTemplate) {
        //获取所有请求属性
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //获取认证授权的请求头 token
        String token = request.getHeader(ACCESS_TOKEN_KEY);
        //设置进feign的请求头
        requestTemplate.header(ACCESS_TOKEN_KEY,token);
    }
}
