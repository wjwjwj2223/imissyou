package com.lin.imissyou.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Constraint(validatedBy = PasswordValidator.class)
public @interface PasswordEqual {

    int max() default 10;
    int min() default 2;

    String message() default "passwords are not equal";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
