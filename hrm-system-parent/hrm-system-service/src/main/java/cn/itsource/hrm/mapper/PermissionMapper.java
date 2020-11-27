package cn.itsource.hrm.mapper;

import cn.itsource.hrm.domain.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author cora
 * @since 2020-11-18
 */
public interface PermissionMapper extends BaseMapper<Permission> {
    /**
     * 根据用户id获取用户权限
     * @param loginId
     * @return
     */
    List<Permission> selectPermissionsByUserId(Long loginId);
}
