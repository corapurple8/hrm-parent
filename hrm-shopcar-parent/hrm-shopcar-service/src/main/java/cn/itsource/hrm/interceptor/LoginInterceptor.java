package cn.itsource.hrm.interceptor;

import cn.itsource.basic.util.AjaxResult;
import cn.itsource.hrm.client.RedisClient;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 登录拦截器
 */
//@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisClient redisClient;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //放行询问是否能跨域的请求
        if("OPTIONS".equals(request.getMethod().toUpperCase())) {
            return true;
        }
        //先判断用户是否登录
        //获取请求头
        String loginId = request.getHeader(Constant.LOGINID_KEY);
        //进行判断
        if (!StringUtils.isNotBlank(loginId)){//为空
            //没有请求头
            //调用没有用户的方法
            noUserWrite(request,response);
            return false;
        }/*else {
            //存在该消息头 从数据库中查询是否存在该令牌 令牌是唯一的 令牌结构 前缀：username
            String loginIdRedis = redisClient.get(Constant.LOGINID_PRE+)
            //再进行判断
            if (!StringUtils.isNotBlank(loginIdRedis)){//存在redis中的用户已经过期
                //调用没有用户的方法
                noUserWrite(request,response);
                return false;
            }
            //获取到了用户
            //重新设置redis中的时间
            redisClient.set(loginId,loginIdRedis,Constant.EXPIRE_TIME);
            return true;
        }*/
        return true;

    }

    public void noUserWrite(HttpServletRequest request,HttpServletResponse response){
        //返回结果
        AjaxResult ajaxResult = AjaxResult.me().setSuccess(false).setMessage("noUser");
        //转换为json对象前端才能识别
        String jsonResult = JSON.toJSONString(ajaxResult);
        PrintWriter writer = null;
        try {
            //发出错误信息 写出页面
            response.setContentType("application/json;charset=utf-8");
            //获取写出流后写出
            writer = response.getWriter();
            writer.write(jsonResult);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (writer!=null){
                //关流
                writer.close();
            }
        }
    }
}