package cn.itsource.hrm.service.impl;

import cn.itsource.hrm.domain.Systemdictionaryitem;
import cn.itsource.hrm.mapper.SystemdictionaryitemMapper;
import cn.itsource.hrm.service.ISystemdictionaryitemService;
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
public class SystemdictionaryitemServiceImpl extends ServiceImpl<SystemdictionaryitemMapper, Systemdictionaryitem> implements ISystemdictionaryitemService {

    @Override
    public List<Systemdictionaryitem> listBySn(String sn) {

        return baseMapper.selectBySn(sn);
    }
}
