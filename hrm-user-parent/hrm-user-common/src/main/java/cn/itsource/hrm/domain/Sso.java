package cn.itsource.hrm.domain;

import com.baomidou.mybatisplus.extension.activerecord.Model;

public class Sso extends Model<Sso> {

    //手机号的状态
    public static final long BIT_STATE_PHONE = 1;
    //邮箱状态
    public static final long BIT_STATE_EMAIL = 2;
    //实名认证
    public static final long BIT_STATE_REAL_AUTH = 4;
    //支付密码
    public static final long BIT_STATE_PAY_PASSWORD = 8;
}