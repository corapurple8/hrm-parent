package cn.itsource.hrm.service.impl;

import cn.itsource.hrm.domain.User;
import cn.itsource.hrm.mapper.UserMapper;
import cn.itsource.hrm.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
}
