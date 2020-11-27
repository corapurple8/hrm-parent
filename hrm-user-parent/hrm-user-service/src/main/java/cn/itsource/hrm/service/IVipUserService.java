package cn.itsource.hrm.service;

import cn.itsource.hrm.controller.dto.RegisterDto;
import cn.itsource.hrm.domain.VipUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员登录账号 服务类
 * </p>
 *
 * @author cora
 * @since 2020-11-23
 */
public interface IVipUserService extends IService<VipUser> {
    /**
     * 手机验证码注册
     * @param dto
     */
    void register(RegisterDto dto);
}
