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
 * 商品收藏
 * </p>
 *
 * @author cora
 * @since 2020-11-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_vip_course_collect")
@ApiModel(value="VipCourseCollect对象", description="商品收藏")
public class VipCourseCollect implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long createTime;

    @ApiModelProperty(value = "登录用户")
    private Long userId;

    @ApiModelProperty(value = "商品ID")
    private Long courseId;


}
