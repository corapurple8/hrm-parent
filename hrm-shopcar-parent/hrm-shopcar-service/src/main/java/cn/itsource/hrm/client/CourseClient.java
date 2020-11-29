package cn.itsource.hrm.client;

import cn.itsource.hrm.client.fallback.CourseDocClientFallbackFactory;
import cn.itsource.hrm.client.fallback.CourseFallbackFacktory;
import cn.itsource.hrm.domain.Course;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
@FeignClient(value = "hrm-course",path = "/course",fallbackFactory = CourseFallbackFacktory.class)
public interface CourseClient {
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    Course get(@PathVariable("id")Long id);
}
