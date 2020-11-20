package cn.itsource.hrm.client;

import cn.itsource.basic.util.AjaxResult;
import cn.itsource.hrm.client.fallback.CourseDocClientFallbackFactory;
import cn.itsource.hrm.doc.CourseDoc;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "es-server",path = "/courseDoc",fallbackFactory = CourseDocClientFallbackFactory.class)
public interface CourseDocClient {
    /**
     * es批量上线
     * @param courseDocList
     * @return
     */
    @PostMapping("/saveBatch")
    AjaxResult saveBatch(@RequestBody List<CourseDoc> courseDocList);

    /**
     * 批量es下线删除
     * @param courseDocList
     * @return
     */
    @PostMapping("/deleteBatch")
    AjaxResult delBatch(@RequestBody List<CourseDoc> courseDocList);
}
