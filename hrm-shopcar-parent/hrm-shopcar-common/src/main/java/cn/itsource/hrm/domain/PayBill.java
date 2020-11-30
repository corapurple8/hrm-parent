package cn.itsource.hrm.domain;

import java.math.BigDecimal;
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
 * 支付单
 * </p>
 *
 * @author cora
 * @since 2020-11-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_pay_bill")
@ApiModel(value="PayBill对象", description="支付单")
public class PayBill implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "交易摘要")
    private String digest;

    @ApiModelProperty(value = "金额")
    private BigDecimal money;

    @ApiModelProperty(value = "支付状态 待支付 已支付 取消")
    private Integer state;

    @TableField("lastPayTime")
    private Date lastpaytime;

    @ApiModelProperty(value = "支付方式 余额 三方支付")
    @TableField("payChannel")
    private Integer paychannel;//支付宝

    @TableField("createTime")
    private Date createtime=new Date();

    @TableField("updateTime")
    private Date updatetime=new Date();

    private Long loginId;

    @ApiModelProperty(value = "统一支付单号")
    @TableField("unionPaySn")
    private String unionpaysn;

    private Long tenantId;

    @ApiModelProperty(value = "订单编号")
    @TableField("orderSn")
    private String ordersn;


}
