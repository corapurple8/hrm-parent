package cn.itsource.hrm.controller.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
@Data
public class CourseDto {
    @NotNull(message = "请选择课程类型")
    private Long courseTypeId;
    @NotBlank(message = "课程名称不能为空")
    private String name;
    @NotBlank(message = "使用人群不能为空")
    private String forUser;
    @NotNull(message = "请选择课程等级")
    private Long gradeId;
    @NotBlank(message = "请选择课程等级")
    private String gradeName;
    @NotBlank(message = "请上传图片")
    private String pic;
    @NotNull(message = "请选择开课时间")
    private LocalDate startTime;
    @NotNull(message = "请选择结课时间")
    private LocalDate endTime;
    @NotBlank(message = "请输入课程简介")
    private String description;
    @NotBlank(message = "请输入课程详情")
    private String intro;
    @NotNull(message = "请选择是否收费")
    private Integer charge;
    @NotBlank(message = "qq不能为空")
    private String qq;
    @NotNull(message = "请输入课程价格")
    private Float price;
    @NotNull(message = "请输入课程原价")
    private Float  priceOld;
    @NotNull(message = "请选择截止时间")
    private LocalDate expires;
}
