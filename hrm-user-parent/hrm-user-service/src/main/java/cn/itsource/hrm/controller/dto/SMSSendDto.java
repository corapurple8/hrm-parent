package cn.itsource.hrm.controller.dto;

import cn.itsource.hrm.validate.EmailValidator;
import cn.itsource.hrm.validate.TelValidator;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SMSSendDto {
    @TelValidator
    private String phone;
    @NotBlank
    private String uuid;
    @NotBlank
    private String imageCode;
}
