package cn.itsource.hrm.service;

import cn.itsource.hrm.domain.User;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class IUserServiceTest {

    @Autowired
    private IUserService userService;

    @Test
    public void testX(){
        Page<User> page = userService.page(new Page<User>(1, 2));
        System.out.println(page.getTotal());
        System.out.println(page.getRecords());
    }

}