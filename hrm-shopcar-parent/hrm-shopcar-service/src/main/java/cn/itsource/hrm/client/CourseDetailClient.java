package cn.itsource.hrm.client;

import cn.itsource.hrm.client.fallback.CourseDetailFallbackFacktory;
import cn.itsource.hrm.domain.CourseDetail;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
@FeignClient(value = "hrm-course",path = "/courseDetail",fallbackFactory = CourseDetailFallbackFacktory.class)
public interface CourseDetailClient {
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    CourseDetail get(@PathVariable("id")Long id);
}
