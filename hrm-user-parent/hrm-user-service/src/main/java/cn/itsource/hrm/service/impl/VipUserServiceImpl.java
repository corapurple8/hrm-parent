package cn.itsource.hrm.service.impl;

import cn.itsource.hrm.domain.VipUser;
import cn.itsource.hrm.mapper.VipUserMapper;
import cn.itsource.hrm.service.IVipUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员登录账号 服务实现类
 * </p>
 *
 * @author cora
 * @since 2020-11-23
 */
@Service
public class VipUserServiceImpl extends ServiceImpl<VipUserMapper, VipUser> implements IVipUserService {

}
