package cn.itsource.hrm.controller;

import cn.itsource.basic.util.AjaxResult;
import cn.itsource.hrm.doc.CourseDoc;
import cn.itsource.hrm.repository.CourseDocRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/courseDoc")
public class CourseDocController {

    @Autowired
    private CourseDocRepository repository;

    /**
     * es批量上线
     * @param courseDocList
     * @return
     */
    @PostMapping("/saveBatch")
    public AjaxResult saveBatch(@RequestBody List<CourseDoc> courseDocList){
        repository.saveAll(courseDocList);
        return AjaxResult.me();
    }

    /**
     * 批量es下线删除
     * @param courseDocList
     * @return
     */
    @PostMapping("/deleteBatch")
    public AjaxResult delBatch(@RequestBody List<CourseDoc> courseDocList){
        repository.deleteAll(courseDocList);
        return AjaxResult.me();
    }


}
