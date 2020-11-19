package cn.itsource.hrm.service;

import cn.itsource.hrm.domain.CourseType;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程目录 服务类
 * </p>
 *
 * @author cora
 * @since 2020-11-19
 */
public interface ICourseTypeService extends IService<CourseType> {
    /**
     * 加载课程类型树
     * @return
     */
    List<CourseType> treeData();
}
