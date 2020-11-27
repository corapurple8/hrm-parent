package cn.itsource.hrm.service.impl;

import cn.itsource.hrm.domain.Login;
import cn.itsource.hrm.mapper.LoginMapper;
import cn.itsource.hrm.service.ILoginService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cora
 * @since 2020-11-27
 */
@Service
public class LoginServiceImpl extends ServiceImpl<LoginMapper, Login> implements ILoginService {

}
