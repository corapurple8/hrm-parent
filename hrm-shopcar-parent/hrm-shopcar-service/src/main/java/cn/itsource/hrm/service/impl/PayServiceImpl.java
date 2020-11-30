package cn.itsource.hrm.service.impl;

import cn.itsource.hrm.config.AlipayConfig;
import cn.itsource.hrm.domain.OrderCourse;
import cn.itsource.hrm.domain.PayAlipayInfo;
import cn.itsource.hrm.service.IPayAlipayInfoService;
import cn.itsource.hrm.service.IPayService;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PayServiceImpl implements IPayService {
    @Autowired
    private IPayAlipayInfoService payAlipayInfoService;
    @Override
    public String jumpToPay(OrderCourse orderCourse) {
        //先查询出商铺所需的所有alipay信息
        PayAlipayInfo alipayInfo = payAlipayInfoService.getById(1L);
        System.out.println(alipayInfo);
        //以下是支付宝自己的接口 将关键信息替换
        AlipayClient alipayClient =  new DefaultAlipayClient(AlipayConfig.gatewayUrl, alipayInfo.getAppid(), alipayInfo.getMerchantPrivateKey(), AlipayConfig.fomat, AlipayConfig.charset, alipayInfo.getAlipayPublicKey(), AlipayConfig.sign_type);  //获得初始化的AlipayClient
        AlipayTradePagePayRequest alipayRequest =  new  AlipayTradePagePayRequest(); //创建API对应的request
        alipayRequest.setReturnUrl( AlipayConfig.return_url );
        alipayRequest.setNotifyUrl( AlipayConfig.notify_url); //在公共参数中设置回跳和通知地址
        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = orderCourse.getOrdersn();
        //付款金额，必填
        String total_amount = orderCourse.getPrice().toString();
        //订单名称，必填
        String subject = orderCourse.getDigest();
        //商品描述，可空
        String body = "";

        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"body\":\""+ body +"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        //若想给BizContent增加其他可选请求参数，以增加自定义超时时间参数timeout_express来举例说明
        //alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
        //		+ "\"total_amount\":\""+ total_amount +"\","
        //		+ "\"subject\":\""+ subject +"\","
        //		+ "\"body\":\""+ body +"\","
        //		+ "\"timeout_express\":\"10m\","
        //		+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        //请求参数可查阅【电脑网站支付的API文档-alipay.trade.page.pay-请求参数】章节

        //请求
        String formHtml = null;
        try {
            formHtml = alipayClient.pageExecute(alipayRequest).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return formHtml;
    }
}
