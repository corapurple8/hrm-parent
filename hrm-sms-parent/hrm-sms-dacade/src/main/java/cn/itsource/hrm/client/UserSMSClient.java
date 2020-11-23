package cn.itsource.hrm.client;

import cn.itsource.basic.util.AjaxResult;
import cn.itsource.hrm.client.fallback.UserSMSClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
@FeignClient(value = "sms-server",path = "sms",fallbackFactory = UserSMSClientFallbackFactory.class)
public interface UserSMSClient {
    /**
     * 发送手机注册验证码
     * @param phone
     * @param code
     * @return
     */
    @GetMapping("/sendRegCode")
    AjaxResult sendRegCode(@RequestParam("phone")String phone, @RequestParam("code")String code);
}
