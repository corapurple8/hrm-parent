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
 * 登录记录
 * </p>
 *
 * @author cora
 * @since 2020-11-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_vip_login_log")
@ApiModel(value="VipLoginLog对象", description="登录记录")
public class VipLoginLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long createTime;

    private Long userId;

    @ApiModelProperty(value = "IP")
    private String ip;

    @ApiModelProperty(value = "客户端")
    private String clientInfo;

    @ApiModelProperty(value = "登录方式")
    private Integer loginType;

    @ApiModelProperty(value = "登录是否成功")
    private Integer success;

    @ApiModelProperty(value = "结果说明")
    private String remark;


}
