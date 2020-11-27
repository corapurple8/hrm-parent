package cn.itsource.hrm.service.impl;

import cn.itsource.hrm.domain.Permission;
import cn.itsource.hrm.mapper.PermissionMapper;
import cn.itsource.hrm.service.IPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cora
 * @since 2020-11-18
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {
    /**
     * 根据用户id获取用户权限
     * @param loginId
     * @return
     */
    @Override
    public List<Permission> selectPermissionsByUserId(Long loginId) {
        return baseMapper.selectPermissionsByUserId(loginId);
    }
}
