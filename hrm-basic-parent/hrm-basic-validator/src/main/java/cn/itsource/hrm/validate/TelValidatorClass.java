package cn.itsource.hrm.validate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TelValidatorClass implements ConstraintValidator<TelValidator,String> {

    @Override
    public void initialize(TelValidator telValidator) {

    }

    /**
     * 手机号码的校验
     * @param value 用户输入的值，如从前端传入的某个值
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        String regExp = "^((13[0-9])|(14[5,7,9])|(15[0-3,5-9])|(166)|(17[3,5,6,7,8])" +
                "|(18[0-9])|(19[8,9]))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(value);
        return m.matches();
    }
}