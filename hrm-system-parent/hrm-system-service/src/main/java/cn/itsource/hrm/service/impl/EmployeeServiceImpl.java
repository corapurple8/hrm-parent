package cn.itsource.hrm.service.impl;

import cn.itsource.hrm.domain.Employee;
import cn.itsource.hrm.mapper.EmployeeMapper;
import cn.itsource.hrm.service.IEmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cora
 * @since 2020-11-18
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {

}
