package cn.itsource.hrm.client.fallback;

import cn.itsource.basic.util.AjaxResult;
import cn.itsource.hrm.client.UserSMSClient;
import feign.hystrix.FallbackFactory;

public class UserSMSClientFallbackFactory implements FallbackFactory<UserSMSClient> {
    @Override
    public UserSMSClient create(Throwable throwable) {
        return new UserSMSClient() {
            @Override
            public AjaxResult sendRegCode(String phone, String code) {
                throwable.printStackTrace();
                return AjaxResult.me().setSuccess(false).setMessage("sms接口调用失败");
            }
        };
    }
}
