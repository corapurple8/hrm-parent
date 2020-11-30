package cn.itsource.hrm.service;

import cn.itsource.hrm.domain.OrderCourse;

public interface IPayService {
    /**
     * 跳转支付界面的接口
     * @param orderCourse
     * @return
     */
    String jumpToPay(OrderCourse orderCourse);
}
