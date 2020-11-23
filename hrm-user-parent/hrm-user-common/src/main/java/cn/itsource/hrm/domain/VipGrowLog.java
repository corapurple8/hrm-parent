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
 * 成长值记录
 * </p>
 *
 * @author cora
 * @since 2020-11-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_vip_grow_log")
@ApiModel(value="VipGrowLog对象", description="成长值记录")
public class VipGrowLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long createTime;

    @ApiModelProperty(value = "登录用户")
    private Long userId;

    @ApiModelProperty(value = "来源")
    private String fromReason;

    @ApiModelProperty(value = "成长值")
    private Integer score;

    @ApiModelProperty(value = "备注")
    private String remark;


}
