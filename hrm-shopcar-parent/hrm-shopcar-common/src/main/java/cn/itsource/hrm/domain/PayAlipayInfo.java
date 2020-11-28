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
 * @since 2020-11-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_pay_alipay_info")
@ApiModel(value="PayAlipayInfo对象", description="")
public class PayAlipayInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String merchantPrivateKey;

    private String appid;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String alipayPublicKey;


}
