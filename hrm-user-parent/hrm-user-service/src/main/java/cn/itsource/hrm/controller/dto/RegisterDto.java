package cn.itsource.hrm.controller.dto;

import cn.itsource.hrm.validate.TelValidator;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RegisterDto {
    @TelValidator
    private String mobile;
    @NotBlank
    private String password;
    @NotBlank
    private String imageCode;
    @NotBlank
    private String smsCode;
}
