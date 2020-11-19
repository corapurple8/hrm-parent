package cn.itsource.hrm.client.impl;

import cn.itsource.basic.util.AjaxResult;
import cn.itsource.hrm.client.RedisClient;
import org.springframework.stereotype.Component;

//@Component
public class RedisClientImpl implements RedisClient {
    @Override
    public AjaxResult set(String key, String value, Integer expire) {
        return AjaxResult.me().setSuccess(false).setMessage("redis接口不能调用set");
    }

    @Override
    public AjaxResult get(String key) {
        return AjaxResult.me().setSuccess(false).setMessage("redis接口不能调用get");
    }

    @Override
    public AjaxResult del(String key) {
        return AjaxResult.me().setSuccess(false).setMessage("redis接口不能调用delete");
    }
}
