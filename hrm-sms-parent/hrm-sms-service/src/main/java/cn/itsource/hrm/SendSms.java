package cn.itsource.hrm;

import cn.itsource.hrm.util.AliyunSMSUtil;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

import java.util.HashMap;
import java.util.Map;

public class SendSms {

    public static void main(String[] args) {

        String phone="";
        String signName="HRM商城";
        String template="SMS_205612576";
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("code","123456");
        //参数化为String
        String param = JSONObject.toJSONString(paramMap);
        AliyunSMSUtil.INSTANCE.sendSMS(phone,signName,template,param);

    }

}
