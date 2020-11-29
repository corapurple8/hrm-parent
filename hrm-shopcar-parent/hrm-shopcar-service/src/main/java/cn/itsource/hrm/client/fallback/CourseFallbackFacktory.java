package cn.itsource.hrm.client.fallback;

import cn.itsource.hrm.client.CourseClient;
import cn.itsource.hrm.domain.Course;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class CourseFallbackFacktory implements FallbackFactory<CourseClient> {
    @Override
    public CourseClient create(Throwable throwable) {
        return new CourseClient() {
            @Override
            public Course get(Long id) {
                throwable.printStackTrace();
                return null;
            }
        };
    }
}
