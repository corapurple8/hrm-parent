package cn.itsource.hrm.mapper;

import cn.itsource.hrm.domain.User;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void testX(){
        userMapper.selectList(null).forEach(System.out::println);
    }

    @Test
    public void testX1(){
        User user = new User();
        user.setUsername("添加");
        user.setPassword("添加");

        userMapper.insert(user);
        System.out.println(user);
    }

    @Test
    public void testX2(){
        QueryWrapper<User> wrapper = new QueryWrapper<User>()
                .ge("age",30).le("age",40);
        userMapper.selectList(wrapper).forEach(System.out::println);
    }

}