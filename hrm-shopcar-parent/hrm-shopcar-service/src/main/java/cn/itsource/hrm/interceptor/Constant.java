package cn.itsource.hrm.interceptor;

/**
 * 订单相关的常量类
 */
public class Constant {
    /**登录用户id请求头*/
    public static final String LOGINID_KEY="Login-id";
    /**购物车中的课程id 前缀：login_id:course_id 存储课程辅助详情*/
    public static final String COURSE_PRE="chart:";
    /**购物车中的愿望清单 前缀：login_id 存储字符串形式的wantIds*/
    public static final String SHOPCAR_PRE="shopcar:";
    /**在redis中存储的购物车对象信息时间*/
    public static final Integer EXPIRE_TIME=30*60;


    /**已支付订单*/
    public static final Integer DISABLED = -1;
    /** 正常订单*/
    public static final Integer NORMAL = 0;
    /**待支付*/
    public static final Integer AUDIT = 1;
    /**已取消订单*/
    public static final Integer REJECT = 2;
}
