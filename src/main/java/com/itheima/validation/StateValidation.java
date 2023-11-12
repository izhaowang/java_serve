package com.itheima.validation;

import com.itheima.anno.State;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

// public class StateValidation implements ConstraintValidator <"将来用在哪个注解上", "使用注解得数据类型"> {
// }

public class StateValidation  implements  ConstraintValidator <State, String>{
    /**
     *
     * @param s 表示将来要检验得数据
     * @param constraintValidatorContext
     * @return 返回false 表示检验失败， true表示校验成功
     */
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        // 提供校验规则
        if (s == null) {
            return false;
        }
        if(s.equals("已发布") || s.equals("草稿")) {
            return  true;
        }
        return false;
    }


}
