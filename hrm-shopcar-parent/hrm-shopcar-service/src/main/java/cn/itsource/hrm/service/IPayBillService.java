package cn.itsource.hrm.service;

import cn.itsource.hrm.domain.PayBill;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 支付单 服务类
 * </p>
 *
 * @author cora
 * @since 2020-11-28
 */
public interface IPayBillService extends IService<PayBill> {
    /**
     * 根据 订单号搜索支付单
     * @param orderSn
     * @return
     */
    PayBill findByOrderSn(String orderSn);
}
