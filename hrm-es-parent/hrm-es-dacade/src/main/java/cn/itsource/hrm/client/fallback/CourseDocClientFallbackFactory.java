package cn.itsource.hrm.client.fallback;

import cn.itsource.basic.util.AjaxResult;
import cn.itsource.hrm.client.CourseDocClient;
import cn.itsource.hrm.doc.CourseDoc;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CourseDocClientFallbackFactory implements FallbackFactory<CourseDocClient> {

    @Override
    public CourseDocClient create(Throwable throwable) {
        return new CourseDocClient() {

            @Override
            public AjaxResult saveBatch(List<CourseDoc> courseDocList) {
                throwable.printStackTrace();
                return AjaxResult.me().setSuccess(false).setMessage("es接口saveBatch失败");
            }

            @Override
            public AjaxResult delBatch(List<CourseDoc> courseDocList) {
                throwable.printStackTrace();
                return AjaxResult.me().setSuccess(false).setMessage("es接口deleteBatch失败");
            }
        };
    }
}
