package cn.itsource.hrm.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2020-11-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_department")
@ApiModel(value="Department对象", description="")
public class Department implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "部门编号")
    private String sn;

    @ApiModelProperty(value = "部门名称")
    private String name;

    @ApiModelProperty(value = "部门的上级分类层级id")
    private String dirPath;

    @ApiModelProperty(value = "部门状态，0正常，1禁用")
    private Integer state;

    @ApiModelProperty(value = "部门管理员，关联Employee表id")
    private Long managerId;

    @ApiModelProperty(value = "上级部门")
    private Long parentId;

    @ApiModelProperty(value = "部门所属机构(租户)")
    private Long tenantId;


}
