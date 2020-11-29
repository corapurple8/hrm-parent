package cn.itsource.hrm.client;

import cn.itsource.hrm.client.fallback.CourseMarketFallbackFacktory;
import cn.itsource.hrm.domain.CourseMarket;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "hrm-course",path = "/courseMarket",fallbackFactory = CourseMarketFallbackFacktory.class)
public interface CourseMarketClient {
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    CourseMarket get(@PathVariable("id")Long id);
}
