package cn.itsource.hrm.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
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
@TableName("t_tenant_meal")
@ApiModel(value="TenantMeal对象", description="")
public class TenantMeal implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long mealId;

    private Long tenantId;

    @ApiModelProperty(value = "该机构的该套餐到期时间")
    private LocalDateTime expireDate;

    @ApiModelProperty(value = "状态,是否过期 0 未支付，1已经购买，2过期")
    private Integer state;


}
