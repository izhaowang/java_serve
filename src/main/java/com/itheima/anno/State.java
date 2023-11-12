package com.itheima.anno;

import com.itheima.validation.StateValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)

@Constraint(
        validatedBy = {StateValidation.class} // 提供校验规则类
)
public @interface State {
    // 提供校验失败后得校验信息
    String message() default "state只能是已发布或者草稿";

    Class<?>[] groups() default {};
    // 负载
    Class<? extends Payload>[] payload() default {};
}
