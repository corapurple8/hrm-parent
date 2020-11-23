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
 * 收货地址
 * </p>
 *
 * @author cora
 * @since 2020-11-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_vip_address")
@ApiModel(value="VipAddress对象", description="收货地址")
public class VipAddress implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long createTime;

    private Long updateTime;

    @ApiModelProperty(value = "登录用户")
    private Long userId;

    @ApiModelProperty(value = "收货人")
    private String reciver;

    @ApiModelProperty(value = "区域")
    private String areaCode;

    @ApiModelProperty(value = "详细地址")
    private String address;

    @ApiModelProperty(value = "全地址")
    private String fullAddress;

    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ApiModelProperty(value = "备用手机号")
    private String phoneBack;

    @ApiModelProperty(value = "固定电话")
    private String tel;

    @ApiModelProperty(value = "邮编")
    private String postCode;

    @ApiModelProperty(value = "电子邮件")
    private String email;

    @ApiModelProperty(value = "是否默认")
    private Integer defaultAddress;


}
