package cn.itsource.hrm.controller;

import cn.itsource.basic.util.AjaxResult;
import cn.itsource.hrm.util.AliyunSMSUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/sms")
public class UserSMSController {
    @GetMapping("/sendRegCode")
    public AjaxResult sendRegCode(@RequestParam("phone")String phone,@RequestParam("code")String code){
        String signName="HRM商城";
        String template="SMS_205612576";
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("code",code);
        //参数化为String
        String param = JSONObject.toJSONString(paramMap);
        AliyunSMSUtil.INSTANCE.sendSMS(phone,signName,template,param);
        return AjaxResult.me();
    }
}
