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
 * 
 * </p>
 *
 * @author cora
 * @since 2020-11-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_order_course")
@ApiModel(value="OrderCourse对象", description="")
public class OrderCourse implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "摘要")
    private String digest;

    @ApiModelProperty(value = "待支付0 待消费1 待确认2 完成3 取消-1")
    private Integer state;

    private BigDecimal price;

    @TableField("orderSn")
    private String ordersn;

    @TableField("paySn")
    private String paysn;

    @TableField("lastPayTime")
    private Date lastpaytime;

    @TableField("lastConfirmTime")
    private Date lastconfirmtime;

    private Long loginId;

    private Long tenantId;

    @ApiModelProperty(value = "支付方式：1表示银联 2表示微信 3表示支付宝")
    private Integer paytype;
    /**订单地址*/
    private Long orderAddressId;
    /**课程id*/
    private Long courseId;
}
