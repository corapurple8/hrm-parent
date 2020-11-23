package cn.itsource.hrm.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ShearCaptcha;
import cn.itsource.basic.util.AjaxResult;
import cn.itsource.basic.util.StrUtils;
import cn.itsource.hrm.client.RedisClient;
import cn.itsource.hrm.client.UserSMSClient;
import cn.itsource.hrm.controller.dto.SMSSendDto;
import cn.itsource.hrm.domain.VipUser;
import cn.itsource.hrm.service.IVipUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jdk.internal.dynalink.beans.StaticClass;
import net.sf.jsqlparser.expression.LongValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

/**
 * 验证码控制器
 */
@RestController
@RequestMapping("/code")
public class CodeController {

    public static final String IMAGE_CODE_KEY_PRE="imagecode:";
    public static final String SMS_CODE_KEY_PRE="sms:register:";
    @Autowired
    private RedisClient redisClient;
    @Autowired
    private IVipUserService vipUserService;
    @Autowired
    private UserSMSClient userSMSClient;


    @GetMapping("/genImageCode/{uuid}")
    public AjaxResult genImageCode(@PathVariable("uuid") String uuid){
//        //随机四位验证码
//        String code = StrUtils.getComplexRandomString(4);
        //生成图形验证码
        //定义图形验证码的长、宽、验证码字符数、干扰线宽度
        ShearCaptcha captcha = CaptchaUtil.createShearCaptcha(150, 100, 4, 4);
        /*//图形验证码写出，可以写出到文件，也可以写出到流
        captcha.write("d:/shear.png");
        //验证图形验证码的有效性，返回boolean值
        captcha.verify("1234");*/
        //根据uuid保存在redis中 调用接口
        //获取四位验证码
        String code = captcha.getCode();
        //获取图形输出字符串
        String imageBase64 = captcha.getImageBase64();
        //获取当前时间
        AjaxResult result = redisClient.set(IMAGE_CODE_KEY_PRE + uuid, code, 60 * 10);
        Assert.isTrue(result.isSuccess(),result.getMessage());

        //响应图片
        return AjaxResult.me().setResultObj(imageBase64);
    }

    /**
     * 发送短信验证码
     * @param dto
     * @return
     */
    @PostMapping("/sendSMSCode")
    public AjaxResult sendSMSCode(@RequestBody SMSSendDto dto){
        //手机号码是否存在
        VipUser user = vipUserService.getOne(new LambdaQueryWrapper<VipUser>().eq(VipUser::getPhone, dto.getPhone()));
        //手机号存在则响应已注册
        if (user!=null){
            return AjaxResult.me().setSuccess(false).setMessage("该手机号已注册");
        }
        //图形验证码是否存在
        AjaxResult result = redisClient.get(IMAGE_CODE_KEY_PRE + dto.getUuid());
        Assert.isTrue(result.isSuccess(),result.getMessage());

        //不存在则失败//验证失败则失败
        if (result.getResultObj()==null||!dto.getImageCode().equals(result.getResultObj())){
            return AjaxResult.me().setSuccess(false).setMessage("图形验证码验证失败");
        }
        //存在则去验证
        //验证图形验证码成功
        //再判断是否已经发送过短信验证码
        long currentTime = System.currentTimeMillis();
        AjaxResult resultSMS = redisClient.get(SMS_CODE_KEY_PRE + dto.getPhone());
        Assert.isTrue(resultSMS.isSuccess(),resultSMS.getMessage());
        //已过期 未发送
        //再次/初次发送验证码并储存在redis中
        String code = StrUtils.getComplexRandomString(6);
        String value = code+","+currentTime;
        //已经发送过 判断时间是否过期
        if (resultSMS.getResultObj()!=null){
            String valueOld = (String) resultSMS.getResultObj();
            if (valueOld!=null){//已经发送过
                //验证码
                String codeOld = valueOld.split(":")[0];
                //上次保存的时间
                Long lastTime = Long.valueOf(value.split(",")[1]);
                if ((currentTime-lastTime)<=60000){//未过期 请自行查看
                    return AjaxResult.me().setSuccess(false).setResultObj("请勿重复获取");
                }
                //过期了 则更新时间重新发送
                value = codeOld+","+currentTime;
                code = codeOld;
            }
        }
        //发送验证码并储存在redis中
        AjaxResult setResult = redisClient.set(SMS_CODE_KEY_PRE + dto.getUuid(), value, 300);
        Assert.isTrue(setResult.isSuccess(),setResult.getMessage());
        //掉用短信接口
        AjaxResult sendRegCode = userSMSClient.sendRegCode(dto.getPhone(), code);
        Assert.isTrue(sendRegCode.isSuccess(),sendRegCode.getMessage());
        return AjaxResult.me();
    }
}
