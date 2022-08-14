package com.finaro.mapper;

import com.finaro.dto.payment.BasePaymentResponseDto;
import com.finaro.dto.payment.ErrorPaymentResponseDto;
import org.apache.commons.collections4.CollectionUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import javax.validation.ConstraintViolation;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface PaymentResponseMapper {

    BasePaymentResponseDto convert(Boolean approved);

    @Mapping(source = "approved", target = "approved")
    @Mapping(target = "errors", expression = "java(toErrors(errors))")
    ErrorPaymentResponseDto convert(Boolean approved, BindingResult errors);

    @Mapping(source = "approved", target = "approved")
    @Mapping(target = "errors", expression = "java(toErrors(violations))")
    ErrorPaymentResponseDto convert(Boolean approved, Set<ConstraintViolation<?>> violations);

    default Map<String, String> toErrors(BindingResult errors) {
        if (errors.hasFieldErrors()) {
            return errors.getFieldErrors().stream()
                    .collect(Collectors.toMap(
                            violation -> "%s.%s".formatted(violation.getObjectName(), violation.getField()),
                            DefaultMessageSourceResolvable::getDefaultMessage,
                            (o, n) -> n,
                            HashMap::new
                    ));
        } else if (errors.hasGlobalErrors()) {
            return errors.getAllErrors().stream()
                    .collect(Collectors.toMap(
                            ObjectError::getObjectName,
                            DefaultMessageSourceResolvable::getDefaultMessage,
                            (o, n) -> n
                    ));
        }
        return Collections.emptyMap();
    }

    default Map<String, String> toErrors(Set<ConstraintViolation<?>> violations) {
        if (CollectionUtils.isNotEmpty(violations)) {
            return violations.stream()
                    .collect(Collectors.toMap(
                            violation -> violation.getRootBeanClass().getName(),
                            ConstraintViolation::getMessage,
                            (o, n) -> n
                    ));
        }
        return Collections.emptyMap();
    }
}
