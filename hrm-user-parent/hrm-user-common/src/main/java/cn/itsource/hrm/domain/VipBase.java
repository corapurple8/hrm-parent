package cn.itsource.hrm.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 会员基本信息
 * </p>
 *
 * @author cora
 * @since 2020-11-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_vip_base")
@ApiModel(value="VipBase对象", description="会员基本信息")
public class VipBase implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long createTime;

    private Long updateTime;

    @ApiModelProperty(value = "登录账号")
    private Long userId;

    @ApiModelProperty(value = "注册渠道")
    private Integer regChannel;

    @ApiModelProperty(value = "注册时间")
    private Long regTime;

    @ApiModelProperty(value = "QQ")
    private String qq;

    @ApiModelProperty(value = "用户等级")
    private Integer level;

    @ApiModelProperty(value = "成长值")
    private Integer growScore;

    @ApiModelProperty(value = "推荐人")
    private Long referId;

    @ApiModelProperty(value = "性别")
    private Integer sex;

    @ApiModelProperty(value = "生日")
    private LocalDate birthday;

    @ApiModelProperty(value = "居住地区域")
    private Integer areaCode;

    @ApiModelProperty(value = "详细地址")
    private String address;


}
