package com.furenqiang.business.valid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.Set;

/**
 * 自定义校验器，要实现 implements  ConstraintValidator<ListValue,Integer>
 */
public class ListValueConstraintValidator implements  ConstraintValidator<ListValue,Integer> {

    private Set<Integer> set=new HashSet<>();

    @Override
    public void initialize(ListValue constraintAnnotation) {
        //获取注解上的指定值，遍历放入set
        for (int val : constraintAnnotation.vals()) {
            set.add(val);
        }
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext constraintValidatorContext) {
        //判断set中的指定值是否包含前端传来的参数value
        return set.contains(value);
    }
}
