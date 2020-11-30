package cn.itsource.hrm.controller.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;

/**
 * 辅助进行课程展示及查询
 */
@Data
public class CourseDto {
    private Long id;
    private Long loginId;
    private Long carCourseId;
    private Long courseTypeId;
    private String name;
    private String forUser;
    private Long gradeId;
    private String gradeName;
    private String pic;
    private LocalDate startTime;
    private LocalDate endTime;
    private String description;
    private String intro;
    private Integer charge;
    private String qq;
    private Float price;
    private Float  priceOld;
    private LocalDate expires;
    private Long tenantId;
    private String tenantName;

    //地址类
    private String contacts;
    private String address;
    private String phone;

}
