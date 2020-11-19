package cn.itsource.hrm.controller.dto;

import cn.itsource.hrm.validate.EmailValidator;
import cn.itsource.hrm.validate.TelValidator;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class TenantEnteringDto {
    @NotBlank(message = "请输入公司名")
    private String companyName;
    @NotBlank(message = "请输入公司执照")
    private String companyNum;
    @NotBlank(message = "请输入公司地址")
    private String address;
    @NotBlank(message = "请上传公司标识")
    private String logo;
    @NotNull(message = "请选择公司类型")
    private Long tenantTypeId;
    @NotBlank(message = "请输入用户名")
    private String username;
    @TelValidator
    private String tel;
    @EmailValidator
    //@Pattern(regexp = "邮箱的正则表达式",message = "请输入正确的邮箱")
    private String email;

    @NotBlank(message = "请输入密码")
    private String password;
    @NotNull(message = "请选择套餐")
    private Long mealId;
}
