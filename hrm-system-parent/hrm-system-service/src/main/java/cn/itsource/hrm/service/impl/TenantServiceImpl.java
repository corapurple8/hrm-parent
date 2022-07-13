package cn.itsource.hrm.service.impl;

import cn.itsource.basic.util.StrUtils;
import cn.itsource.basic.util.encrypt.MD5;
import cn.itsource.hrm.controller.dto.TenantEnteringDto;
import cn.itsource.hrm.domain.Employee;
import cn.itsource.hrm.domain.Tenant;
import cn.itsource.hrm.domain.TenantMeal;
import cn.itsource.hrm.mapper.EmployeeMapper;
import cn.itsource.hrm.mapper.TenantMapper;
import cn.itsource.hrm.mapper.TenantMealMapper;
import cn.itsource.hrm.service.ITenantService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cora
 * @since 2020-11-18
 */
@Service
public class TenantServiceImpl extends ServiceImpl<TenantMapper, Tenant> implements ITenantService {

    @Autowired//注入员工mapper
    private EmployeeMapper employeeMapper;

    @Autowired//注入套餐租户中间表mapper
    private TenantMealMapper tenantMealMapper;
    /**
     * 机构入驻
     * @param dto
     */
    @Override
    @Transactional//事务管理
    public void entering(TenantEnteringDto dto) {
        //租户表
        Tenant tenant = new Tenant();
        BeanUtils.copyProperties(dto,tenant);
        tenant.setState(true);//状态正常
        tenant.setRegisterTime(LocalDate.now());//注册时间
        baseMapper.insert(tenant);//此时的baseMapper就是tenantMapper
        //添加成功并返回了主键

        //员工表
        Employee employee = new Employee();
        BeanUtils.copyProperties(dto,employee);
        employee.setTenantId(tenant.getId());
        String salt = StrUtils.getRandomString(5);//五位盐值
        employee.setSalt(salt);
        String password = MD5.getMD5(dto.getPassword() + salt);
        employee.setPassword(password);
        employee.setState(0);//正常
        employee.setInputTime(LocalDate.now());
        employee.setType(3);//管理员职位
        employeeMapper.insert(employee);

        //租户套餐中间表
        TenantMeal tenantMeal = new TenantMeal();
        tenantMeal.setTenantId(tenant.getId());
        tenantMeal.setMealId(dto.getMealId());
        tenantMealMapper.insert(tenantMeal);
    }
}
