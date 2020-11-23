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
 * 会员实名资料
 * </p>
 *
 * @author cora
 * @since 2020-11-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_vip_real_info")
@ApiModel(value="VipRealInfo对象", description="会员实名资料")
public class VipRealInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long createTime;

    private Long updateTime;

    @ApiModelProperty(value = "登录用户")
    private Long userId;

    @ApiModelProperty(value = "真实姓名")
    private String realName;

    @ApiModelProperty(value = "身份证号")
    private String idCardNo;

    @ApiModelProperty(value = "身份证正面")
    private String idCardFront;

    @ApiModelProperty(value = "身份证反面")
    private String idCardBack;

    @ApiModelProperty(value = "手持身份证")
    private String idCardHand;

    @ApiModelProperty(value = "审核状态")
    private Integer state;

    @ApiModelProperty(value = "提交时间")
    private Long applyTime;

    @ApiModelProperty(value = "审核时间")
    private Long auditTime;

    @ApiModelProperty(value = "审核人")
    private String auditUser;

    @ApiModelProperty(value = "审核备注")
    private String remark;


}
