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
 * @since 2020-11-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_system_dictionary")
@ApiModel(value = "数据字典对象",description = "数据字典对象")
public class SystemDictionary implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "主键",name = "id",example = "1")
    private Long id;

    @ApiModelProperty(value = "标识",name = "sn",example = "JOB")
    private String name;

    private String sn;

    private String intro;

    private Integer state;


}
