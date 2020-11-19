package cn.itsource.hrm.validate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidatorClass implements ConstraintValidator<EmailValidator,String> {

    @Override
    public void initialize(EmailValidator emailValidator) {

    }

    /**
     * 邮箱的校验
     * @param value 用户输入的值，如从前端传入的某个值
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        String regExp = "^(\\w+([-.][A-Za-z0-9]+)*){3,18}@\\w+([-.][A-Za-z0-9]+)*\\.\\w+([-.][A-Za-z0-9]+)*$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(value);
        return m.matches();
    }
}