package com.tencent.wxcloudrun.validation;

import com.tencent.wxcloudrun.validation.Imp.DateTimeValidator;
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = DateTimeValidator.class)
public @interface DateTimeStr {

    String message() default "{javax.validation.constraints.DateTimeStr.message}";

    String format() default "yyyy-MM-dd HH:mm:ss";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}