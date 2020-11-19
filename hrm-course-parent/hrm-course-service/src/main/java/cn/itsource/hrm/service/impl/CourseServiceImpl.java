package cn.itsource.hrm.service.impl;

import cn.itsource.hrm.controller.dto.CourseDto;
import cn.itsource.hrm.domain.Course;
import cn.itsource.hrm.domain.CourseDetail;
import cn.itsource.hrm.domain.CourseMarket;
import cn.itsource.hrm.mapper.CourseDetailMapper;
import cn.itsource.hrm.mapper.CourseMapper;
import cn.itsource.hrm.mapper.CourseMarketMapper;
import cn.itsource.hrm.service.ICourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cora
 * @since 2020-11-19
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements ICourseService {
    @Autowired
    private CourseDetailMapper courseDetailMapper;
    @Autowired
    private CourseMarketMapper courseMarketMapper;

    @Override
    public void add(CourseDto dto) {

        //课程信息表
        Course course = new Course();
        BeanUtils.copyProperties(dto,course);
        course.setStatus(0);
        course.setViewCount(0);
        course.setCommentCount(0);
        course.setSaleCount(0);
        baseMapper.insert(course);
        
        //课程详情表
        CourseDetail courseDetail = new CourseDetail();
        courseDetail.setDescription(dto.getDescription());
        courseDetail.setIntro(dto.getIntro());
        courseDetail.setId(course.getId());
        courseDetailMapper.insert(courseDetail);

        //市场信息表
        CourseMarket courseMarket = new CourseMarket();
        BeanUtils.copyProperties(dto,courseMarket);
        courseMarket.setId(course.getId());
        courseMarketMapper.insert(courseMarket);

    }
}
