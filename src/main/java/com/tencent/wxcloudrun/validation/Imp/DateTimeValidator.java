package com.tencent.wxcloudrun.validation.Imp;

import com.tencent.wxcloudrun.validation.DateTimeStr;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;
import java.text.SimpleDateFormat;

public class DateTimeValidator implements ConstraintValidator<DateTimeStr,String>{

    private  DateTimeStr  dateTimeStr;

    @Override
    public void initialize(DateTimeStr dateTimeStr) {
        this.dateTimeStr=dateTimeStr;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // 如果 value 为空则不进行格式验证，为空验证可以使用 @NotBlank @NotNull @NotEmpty 等注解来进行控制，职责分离
        if (value == null) {
            return true;
        }
        String format = dateTimeStr.format();

        if (value.length() != format.length()) {
            return false;
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);

        try {
            simpleDateFormat.parse(value);
        } catch (Exception e){
            return false;
        }


        return true;
    }
}
