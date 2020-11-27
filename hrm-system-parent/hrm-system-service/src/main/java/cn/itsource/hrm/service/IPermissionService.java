package cn.itsource.hrm.service;

import cn.itsource.hrm.domain.Permission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cora
 * @since 2020-11-18
 */
public interface IPermissionService extends IService<Permission> {
    /**
     * 根据用户id获取用户权限
     * @param loginId
     * @return
     */
    List<Permission> selectPermissionsByUserId(Long loginId);
}
