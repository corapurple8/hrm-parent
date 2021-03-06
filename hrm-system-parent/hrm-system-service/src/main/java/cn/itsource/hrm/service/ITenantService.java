package cn.itsource.hrm.service;

import cn.itsource.hrm.controller.dto.TenantEnteringDto;
import cn.itsource.hrm.domain.Tenant;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cora
 * @since 2020-11-18
 */
public interface ITenantService extends IService<Tenant> {
    /**
     * 机构入驻
     * @param dto
     */
    void entering(TenantEnteringDto dto);
}
