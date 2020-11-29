package cn.itsource.hrm.security;

import cn.itsource.hrm.client.LoginSystemClient;
import cn.itsource.hrm.domain.Login;
import cn.itsource.hrm.domain.Permission;
import cn.itsource.hrm.mapper.LoginMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

//@Component
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private LoginMapper loginMapper;

    @Autowired
    private LoginSystemClient loginSystemClient;

    /**
     * 加载数据库中的认证的用户的信息：用户名，密码，用户的权限列表
     * @param username: 该方法把username传入进来，
        我们通过username查询用户的信息(密码，权限列表等)
        然后封装成 UserDetails进行返回 ，交给security 。
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //User是security内部的对象，UserDetails的实现类 ，
        //用来封装用户的基本信息（用户名，密码，权限列表）
        //public User(String username, String password, Collection<? extends GrantedAuthority> authorities)

        
        Login userFromDB = loginMapper.selectOne(new QueryWrapper<Login>().eq("username",username));
        if(null == userFromDB){
            throw new UsernameNotFoundException("无效的用户");
        }

        //存储在数据库的用户的密码：123
        String password = userFromDB.getPassword();
        Collection<SimpleGrantedAuthority> authorities = Collections.emptyList();
        //如果是系统用户
        if (userFromDB.getType()==1){
            //查询用户的权限
            List<Permission> permissions = loginSystemClient.getPermissionsByUserId(userFromDB.getId());

            //用户的权限列表,暂时为空
            authorities = permissions.stream().map(permission -> {
                //获取权限列表
                return new SimpleGrantedAuthority(permission.getSn());
            }).collect(Collectors.toList());
        }

        org.springframework.security.core.userdetails.User  user = new org.springframework.security.core.userdetails.User (username,password, authorities);

        return user;
    }
}