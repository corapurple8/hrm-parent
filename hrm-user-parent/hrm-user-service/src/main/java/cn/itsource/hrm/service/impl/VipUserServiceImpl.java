package cn.itsource.hrm.service.impl;
import java.time.LocalDate;

import cn.itsource.basic.util.StrUtils;
import cn.itsource.basic.util.encrypt.MD5;
import cn.itsource.hrm.controller.dto.RegisterDto;
import cn.itsource.hrm.domain.Sso;
import cn.itsource.hrm.domain.VipBase;
import cn.itsource.hrm.domain.VipUser;
import cn.itsource.hrm.mapper.VipBaseMapper;
import cn.itsource.hrm.mapper.VipUserMapper;
import cn.itsource.hrm.service.IVipUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

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
    @Autowired
    private VipBaseMapper vipBaseMapper;

    @Override
    @Transactional
    public void register(RegisterDto dto) {

        VipUser vipUser = new VipUser();
        vipUser.setCreateTime(System.currentTimeMillis());
        vipUser.setUpdateTime(System.currentTimeMillis());
        vipUser.setThirdUid("");
        vipUser.setPhone(dto.getMobile());
        vipUser.setEmail("");
        String salt = StrUtils.getComplexRandomString(new Random().nextInt(6));
        vipUser.setSalt(salt);
        vipUser.setPassword(MD5.getMD5(dto.getPassword()+salt));
        vipUser.setAvatar("https://www.woyaogexing.com/touxiang/nv/2020/1077133.html");
        vipUser.setNickName("cora");
        vipUser.addBitState(Sso.BIT_STATE_PHONE);
        vipUser.setSecLevel(0);

        //存进数据库
        baseMapper.insert(vipUser);

        VipBase vipBase = new VipBase();
        vipBase.setCreateTime(System.currentTimeMillis());
        vipBase.setUpdateTime(System.currentTimeMillis());
        vipBase.setUserId(vipUser.getId());
        vipBase.setRegChannel(0);
        vipBase.setRegTime(System.currentTimeMillis());
        vipBase.setQq("");
        vipBase.setLevel(0);
        vipBase.setGrowScore(0);
        vipBase.setReferId(0L);
        vipBase.setSex(0);
        vipBase.setBirthday(LocalDate.now());
        vipBase.setAreaCode(0);
        vipBase.setAddress("重庆");

        //存进数据库
        vipBaseMapper.insert(vipBase);
    }
}
