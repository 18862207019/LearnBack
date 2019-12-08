package util.validator;

import com.google.common.collect.Iterables;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import java.util.Set;

/**
 * @description: 对象验证器
 */
public class BeanValidator {

    /**
     * 验证某个bean的参数
     *
     * @param object 被校验的参数
     * @throws ValidationException 如果参数校验不成功则抛出此异常
     */
    public static <T> void validate(T object) {
        //获得验证器
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        //执行验证
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(object);
        //如果有验证信息，则将第一个取出来包装成异常返回
        ConstraintViolation<T> constraintViolation = Iterables.getFirst(constraintViolations, null);
        if (constraintViolation != null) {
            throw new ValidationException(constraintViolation.getMessage());
        }
    }
}
