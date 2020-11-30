package cn.itsource.hrm.service.impl;

import cn.itsource.hrm.domain.PayBill;
import cn.itsource.hrm.mapper.PayBillMapper;
import cn.itsource.hrm.service.IPayBillService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 支付单 服务实现类
 * </p>
 *
 * @author cora
 * @since 2020-11-28
 */
@Service
public class PayBillServiceImpl extends ServiceImpl<PayBillMapper, PayBill> implements IPayBillService {

    @Override
    public PayBill findByOrderSn(String orderSn) {
        return  baseMapper.selectOne(new QueryWrapper<PayBill>().eq("orderSn",orderSn));
    }
}
