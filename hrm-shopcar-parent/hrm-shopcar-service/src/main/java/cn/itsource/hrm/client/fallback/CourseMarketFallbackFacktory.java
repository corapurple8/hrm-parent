package cn.itsource.hrm.client.fallback;

import cn.itsource.hrm.client.CourseMarketClient;
import cn.itsource.hrm.domain.CourseMarket;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class CourseMarketFallbackFacktory implements FallbackFactory<CourseMarketClient> {
    @Override
    public CourseMarketClient create(Throwable throwable) {
        return new CourseMarketClient() {
            @Override
            public CourseMarket get(Long id) {
                throwable.printStackTrace();
                return null;
            }
        };
    }
}
