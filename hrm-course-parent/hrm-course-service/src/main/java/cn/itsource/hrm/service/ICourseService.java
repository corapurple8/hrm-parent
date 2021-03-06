package cn.itsource.hrm.service;

import cn.itsource.hrm.controller.dto.CourseDto;
import cn.itsource.hrm.domain.Course;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cora
 * @since 2020-11-19
 */
public interface ICourseService extends IService<Course> {
    /**
     * 新增课程
     * @param dto
     */
    void add(CourseDto dto);

    /**
     * 批量上线课程
     * @param ids
     */
    void onlineCourse(List<Long> ids);

    /**
     * 批量下线
     * @param ids
     */
    void offLineCourse(List<Long> ids);
}
