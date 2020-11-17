package com.example.bio.common.constraint;

import cn.hutool.core.util.StrUtil;
import org.springframework.beans.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

/**
 * @author zhangfuqi
 * @date 2020/11/17
 */
public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {

    private String firstFieldName;
    private String secondFieldName;

    @Override
    public void initialize(FieldMatch constraintAnnotation) {
        firstFieldName = constraintAnnotation.first();
        secondFieldName = constraintAnnotation.second();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        try {
            final String first = Objects.requireNonNull(BeanUtils.getPropertyDescriptor(value.getClass(), firstFieldName)).getReadMethod().invoke(value).toString();
            final String second = Objects.requireNonNull(BeanUtils.getPropertyDescriptor(value.getClass(), secondFieldName)).getReadMethod().invoke(value).toString();
            return StrUtil.isBlank(first) && StrUtil.isBlank(second) || StrUtil.isNotBlank(first) && first.equals(second);
        } catch (final Exception ignore) {
        }
        return true;
    }
}
