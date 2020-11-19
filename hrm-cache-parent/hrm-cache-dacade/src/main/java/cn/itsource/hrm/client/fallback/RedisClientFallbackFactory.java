package cn.itsource.hrm.client.fallback;

import cn.itsource.basic.util.AjaxResult;
import cn.itsource.hrm.client.RedisClient;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class RedisClientFallbackFactory implements FallbackFactory<RedisClient> {

    @Override
    public RedisClient create(Throwable throwable) {
        return new RedisClient() {
            @Override
            public AjaxResult set(String key, String value, Integer expire) {
                throwable.printStackTrace();
                return AjaxResult.me().setSuccess(false).setMessage("redis接口不能调用set");
            }

            @Override
            public AjaxResult get(String key) {
                throwable.printStackTrace();
                return AjaxResult.me().setSuccess(false).setMessage("redis接口不能调用get");
            }

            @Override
            public AjaxResult del(String key) {
                throwable.printStackTrace();
                return AjaxResult.me().setSuccess(false).setMessage("redis接口不能调用del");
            }
        };
    }
}
