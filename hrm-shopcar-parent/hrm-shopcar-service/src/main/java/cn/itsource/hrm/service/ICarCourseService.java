package cn.itsource.hrm.service;

import cn.itsource.hrm.controller.dto.CourseDto;
import cn.itsource.hrm.domain.CarCourse;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cora
 * @since 2020-11-28
 */
public interface ICarCourseService extends IService<CarCourse> {
    /**
     * 添加一个课程进购物车
     * @param carCourse
     */
    void add(CarCourse carCourse);

    /**
     * 删除一个在购物车里的课程
     * @param carCourse
     */
    void delete(CourseDto carCourse);

    /**
     * 查询购物车里所有商品
     * @param loginId
     * @return
     */
    List<CourseDto> goodsList(Long loginId);

    /**
     * 购买一个购物车里的课程对象
     * @param courseDto
     */
    void buy(CourseDto courseDto);
}
