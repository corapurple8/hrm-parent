package cn.itsource.hrm.domain;

import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("t_meal_permission")
@ApiModel(value="MealPermission对象", description="")
public class MealPermission implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long mealId;

    private Long permissionId;


}
