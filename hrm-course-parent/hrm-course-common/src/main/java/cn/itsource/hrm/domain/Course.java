package cn.itsource.hrm.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author cora
 * @since 2020-11-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_course")
@ApiModel(value="Course对象", description="")
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "课程名称")
    private String name;

    @ApiModelProperty(value = "适用人群")
    private String forUser;

    @ApiModelProperty(value = "课程分类")
    private Long courseTypeId;

    private String gradeName;

    @ApiModelProperty(value = "课程等级")
    private Long gradeId;

    @ApiModelProperty(value = "课程状态，下线：0 ， 上线：1")
    private Integer status;

    @ApiModelProperty(value = "教育机构")
    private Long tenantId;

    private String tenantName;

    @ApiModelProperty(value = "添加课程的后台用户的ID")
    private Long userId;

    @ApiModelProperty(value = "添加课程的后台用户")
    private String userName;

    @ApiModelProperty(value = "课程的开课时间")
    private LocalDate startTime;

    @ApiModelProperty(value = "课程的节课时间")
    private LocalDate endTime;

    @ApiModelProperty(value = "封面")
    private String pic;

    private Integer saleCount;

    private Integer viewCount;

    @ApiModelProperty(value = "评论数")
    private Integer commentCount;


}
