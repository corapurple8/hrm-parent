package cn.itsource.hrm.controller.vo;

import cn.itsource.hrm.domain.CourseType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Crumb {
    /**当前课程类型*/
    private CourseType currentType;
    /**其他同级课程类型*/
    private List<CourseType> otherTypes;
}
