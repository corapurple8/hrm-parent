package cn.itsource.hrm.service.impl;

import cn.itsource.hrm.domain.OrderAddress;
import cn.itsource.hrm.mapper.OrderAddressMapper;
import cn.itsource.hrm.service.IOrderAddressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单地址 服务实现类
 * </p>
 *
 * @author cora
 * @since 2020-11-28
 */
@Service
public class OrderAddressServiceImpl extends ServiceImpl<OrderAddressMapper, OrderAddress> implements IOrderAddressService {

}
