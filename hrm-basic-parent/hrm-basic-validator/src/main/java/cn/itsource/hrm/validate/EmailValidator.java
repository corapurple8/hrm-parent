package cn.itsource.hrm.validate;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER,ElementType.FIELD})
@Constraint(validatedBy = EmailValidatorClass.class)
public @interface EmailValidator {

    // tel无效时的提示内容
    String message() default "请输入正确的邮箱";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}		