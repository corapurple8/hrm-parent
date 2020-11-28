package cn.itsource.hrm.security;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 鉴权中心调用system接口查询权限时需要加的请求头 模拟客户端template 临时客户端
 */
@Component
public class Oauth2FeignRequestInterceptor implements RequestInterceptor {
    public static final String ACCESS_TOKEN_KEY="Authorization";
    @Override
    public void apply(RequestTemplate requestTemplate) {
        System.out.println("进来了");
        //模拟发送http请求
        String url = "http://localhost:1200/oauth/token";
        //标准模式或者json格式的参数
        String body = "client_id=temp&client_secret=123&grant_type=client_credentials";
        String result = HttpUtil.post(url, body);
        //从结果里获取token
        JSONObject jsonObject = JSONObject.parseObject(result);
        String token = jsonObject.getString("access_token");
        System.out.println(token);

        //设置进feign的请求头  注意不要忘了默认的token前缀
        requestTemplate.header(ACCESS_TOKEN_KEY,"Bearer "+token);
    }

    public static void main(String[] args) {
        String url = "http://localhost:1200/oauth/token";
        //标准模式或者json格式的参数
        String body = "client_id=temp&client_secret=123&grant_type=client_credentials";
        String result = HttpUtil.post(url, body);
        //从结果里获取token
        JSONObject jsonObject = JSONObject.parseObject(result);
        String token = jsonObject.getString("access_token");
        System.out.println(token);
    }
}
