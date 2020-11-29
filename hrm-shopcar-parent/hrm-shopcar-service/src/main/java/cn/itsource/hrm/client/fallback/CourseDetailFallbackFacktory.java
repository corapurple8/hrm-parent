package cn.itsource.hrm.client.fallback;

import cn.itsource.hrm.client.CourseDetailClient;
import cn.itsource.hrm.domain.CourseDetail;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class CourseDetailFallbackFacktory implements FallbackFactory<CourseDetailClient> {
    @Override
    public CourseDetailClient create(Throwable throwable) {
        return new CourseDetailClient() {
            @Override
            public CourseDetail get(Long id) {
                throwable.printStackTrace();
                return null;
            }
        };
    }
}
