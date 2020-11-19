package cn.itsource.hrm.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 课程目录
 * </p>
 *
 * @author cora
 * @since 2020-11-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_course_type")
@ApiModel(value="CourseType对象", description="课程目录")
public class CourseType implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long createTime;

    private Long updateTime;

    @ApiModelProperty(value = "类型名")
    private String name;

    @ApiModelProperty(value = "父ID")
    private Long pid;

    @ApiModelProperty(value = "图标")
    private String logo;

    @ApiModelProperty(value = "描述")
    private String description;

    private Integer sortIndex;

    @ApiModelProperty(value = "路径")
    private String path;

    @ApiModelProperty(value = "课程数量")
    private Integer totalCount;
    /**
     * 加载课程类型树所需要的字段
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "子级课程类型")
    private List<CourseType> children = new ArrayList<CourseType>();


}
