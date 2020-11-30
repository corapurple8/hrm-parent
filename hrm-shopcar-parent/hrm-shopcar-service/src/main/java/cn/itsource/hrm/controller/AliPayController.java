package cn.itsource.hrm.controller;

import cn.itsource.hrm.client.UserSMSClient;
import cn.itsource.hrm.config.AlipayConfig;
import cn.itsource.hrm.domain.OrderAddress;
import cn.itsource.hrm.domain.OrderCourse;
import cn.itsource.hrm.domain.PayAlipayInfo;
import cn.itsource.hrm.domain.PayBill;
import cn.itsource.hrm.interceptor.Constant;
import cn.itsource.hrm.service.IOrderAddressService;
import cn.itsource.hrm.service.IOrderCourseService;
import cn.itsource.hrm.service.IPayAlipayInfoService;
import cn.itsource.hrm.service.IPayBillService;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 二次验签控制类
 */
@RestController
@RequestMapping("/alipay")
public class AliPayController {
    @Autowired//成功后修改支付单状态
    private IPayBillService payBillService;
    @Autowired//成功后修改订单状态
    private IOrderCourseService orderCourseService;
    @Autowired//阿里支付的各种公钥秘钥
    private IPayAlipayInfoService alipayInfoService;

    @Autowired
    private UserSMSClient userSMSClient;

    @Autowired
    private IOrderAddressService orderAddressService;


    @RequestMapping("/notify")//异步验证支付宝跳转接口
    public String notify(HttpServletRequest request) {
        System.out.println("进来了这个接口");
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用 现在不需要解决 springboot已经帮我们解决了
            params.put(name, valueStr);
        }
        String orderSn = request.getParameter("out_trade_no");
        //根据订单号查询商铺再查询到阿里支付信息
        PayAlipayInfo alipayInfo = alipayInfoService.getById(1L);
        try {
            boolean signVerified = AlipaySignature.rsaCheckV1(params, alipayInfo.getAlipayPublicKey(), AlipayConfig.charset, AlipayConfig.sign_type); //调用SDK验证签名
            System.out.println(signVerified);

            //——请在这里编写您的程序（以下代码仅作参考）——

	/* 实际验证过程建议商户务必添加以下校验：
	1、需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
	2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
	3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
	4、验证app_id是否为该商户本身。
	*/

            if (signVerified) {//验证成功
                System.out.println("验证成功");
                // 商户订单号
                String out_trade_no = orderSn;
                //支付宝交易号
                String trade_no = request.getParameter("trade_no");
                System.out.println(trade_no);
                // 交易状态
                String trade_status = request.getParameter("trade_status");
                System.out.println(trade_status);
                if (trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")) {
                    //执行该订单的后续业务流程：比如支付成功后要修改订单状态
                    //判断该笔订单是否在商户网站中已经做过处理
                    //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                    //如果有做过处理，不执行商户的业务程序
                    //验证成功后做处理

                    //修改支付单状态 根据订单sn查找支付单
                    PayBill payBill = payBillService.findByOrderSn(orderSn);
                    payBill.setState(Constant.DISABLED);//成功支付
                    payBill.setUnionpaysn(trade_no);
                    //修改时间设置当前
                    payBill.setUpdatetime(new Date());
                    //做状态修改
                    payBillService.update(payBill,null);
                    //根据支付单查询到订单
                    OrderCourse orderCourse = orderCourseService.getOne(new QueryWrapper<OrderCourse>().eq("orderSn", orderSn));
                    //支付单号
                    orderCourse.setPaysn(trade_no);
                    //成功支付
                    orderCourse.setState(Constant.DISABLED);
                    //发送站内信
                    String message = orderCourse.getDigest()+"成功,支付单编号为："+trade_no;
                    Long orderAddressId = orderCourse.getOrderAddressId();
                    OrderAddress orderAddress = orderAddressService.getById(orderAddressId);
                    userSMSClient.sendRegCode(orderAddress.getPhone(),message);
                }
                return "success";
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return "failure";
    }
}