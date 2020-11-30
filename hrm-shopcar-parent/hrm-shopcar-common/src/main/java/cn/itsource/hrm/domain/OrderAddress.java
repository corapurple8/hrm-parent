package cn.itsource.hrm.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 订单地址
 * </p>
 *
 * @author cora
 * @since 2020-11-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_order_address")
@ApiModel(value="OrderAddress对象", description="订单地址")
public class OrderAddress implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("createTime")
    private Date createtime=new Date();

    @TableField("updateTime")
    private Date updatetime;

    @TableField("orderSn")
    private String ordersn;

    @ApiModelProperty(value = "收货人")
    private String contacts;

    @ApiModelProperty(value = "详细地址")
    private String address;

    @ApiModelProperty(value = "手机号码")
    private String phone;

    private Long loginId;


}
