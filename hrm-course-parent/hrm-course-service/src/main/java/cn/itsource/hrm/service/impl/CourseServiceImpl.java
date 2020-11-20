package cn.itsource.hrm.service.impl;

import cn.itsource.basic.util.AjaxResult;
import cn.itsource.hrm.client.CourseDocClient;
import cn.itsource.hrm.controller.dto.CourseDto;
import cn.itsource.hrm.doc.CourseDoc;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    @Autowired
    private CourseDocClient courseDocClient;

    @Override
    @Transactional
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

    @Override
    @Transactional
    public void onlineCourse(List<Long> ids) {
        //验证
        //根据id查询出所有课程
        List<Course> courses = baseMapper.selectBatchIds(ids);
        //用流过滤出已经上线的课程并变回list形式
        List<Course> onlineCourseList = courses.stream().filter(course -> course.getStatus() == 1).collect(Collectors.toList());
        //直接断言 若是已上线列表不为空 或者长度大于0 则抛出异常
        Assert.isTrue(onlineCourseList==null||onlineCourseList.size()<=0,"请不要重复上线课程");

        //直接修改课程状态
        for (Course cours : courses) {
            cours.setStatus(1);
            baseMapper.updateById(cours);
        }
        //将所有上线课程放到es中

        List<CourseDoc> courseDocList = parseCourseDocList(courses);
        AjaxResult result = courseDocClient.saveBatch(courseDocList);
        Assert.isTrue(result.isSuccess(),result.getMessage());
    }

    @Override
    @Transactional
    public void offLineCourse(List<Long> ids) {
        //验证
        //根据id查询出所有课程
        List<Course> courses = baseMapper.selectBatchIds(ids);
        //用流过滤出已经上线的课程并变回list形式
        List<Course> onlineCourseList = courses.stream().filter(course -> course.getStatus() == 0).collect(Collectors.toList());
        //直接断言 若是已上线列表不为空 或者长度大于0 则抛出异常
        Assert.isTrue(onlineCourseList==null||onlineCourseList.size()<=0,"请不要重复下线课程");

        //直接修改课程状态
        for (Course cours : courses) {
            cours.setStatus(0);
            baseMapper.updateById(cours);
        }
        //将所有上线课程放到es中

        List<CourseDoc> courseDocList = parseCourseDocList(courses);
        AjaxResult result = courseDocClient.delBatch(courseDocList);
        Assert.isTrue(result.isSuccess(),result.getMessage());
    }

    /**
     * 将课程转换为CourseDoc
     * @param courses
     * @return
     */
    private List<CourseDoc> parseCourseDocList(List<Course> courses) {
        return courses.stream().map(course -> {
            return  parseCourseDoc(course);
        }).collect(Collectors.toList());
    }

    /**
     * 单个course转换为CourseDoc
     * @param course
     * @return
     */
    private CourseDoc parseCourseDoc(Course course) {
        CourseDoc courseDoc = new CourseDoc();
        BeanUtils.copyProperties(course,courseDoc);
        CourseDetail courseDetail = courseDetailMapper.selectById(course.getId());
        CourseMarket courseMarket = courseMarketMapper.selectById(course.getId());
        String all = course.getName()+" "+courseDetail.getDescription();
        courseDoc.setAll(all);
        courseDoc.setStartTime(course.getStartTime().atTime(LocalTime.now()).toInstant(ZoneOffset.UTC).toEpochMilli());
        courseDoc.setEndTime(course.getEndTime().atTime(LocalTime.now()).toInstant(ZoneOffset.UTC).toEpochMilli());

        BeanUtils.copyProperties(courseMarket,courseDoc);
        courseDoc.setOnlineTime(System.currentTimeMillis());
        courseDoc.setDescription(courseDetail.getDescription());
        return courseDoc;
    }
}
