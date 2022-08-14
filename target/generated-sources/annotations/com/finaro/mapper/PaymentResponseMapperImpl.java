package com.finaro.mapper;

import com.finaro.dto.payment.BasePaymentResponseDto;
import com.finaro.dto.payment.ErrorPaymentResponseDto;
import java.util.Set;
import javax.annotation.processing.Generated;
import javax.validation.ConstraintViolation;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-15T01:11:44+0300",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.1 (Oracle Corporation)"
)
@Component
public class PaymentResponseMapperImpl implements PaymentResponseMapper {

    @Override
    public BasePaymentResponseDto convert(Boolean approved) {
        if ( approved == null ) {
            return null;
        }

        BasePaymentResponseDto.BasePaymentResponseDtoBuilder<?, ?> basePaymentResponseDto = BasePaymentResponseDto.builder();

        if ( approved != null ) {
            basePaymentResponseDto.approved( approved );
        }

        return basePaymentResponseDto.build();
    }

    @Override
    public ErrorPaymentResponseDto convert(Boolean approved, BindingResult errors) {
        if ( approved == null && errors == null ) {
            return null;
        }

        ErrorPaymentResponseDto.ErrorPaymentResponseDtoBuilder<?, ?> errorPaymentResponseDto = ErrorPaymentResponseDto.builder();

        if ( approved != null ) {
            errorPaymentResponseDto.approved( approved );
        }
        errorPaymentResponseDto.errors( toErrors(errors) );

        return errorPaymentResponseDto.build();
    }

    @Override
    public ErrorPaymentResponseDto convert(Boolean approved, Set<ConstraintViolation<?>> violations) {
        if ( approved == null && violations == null ) {
            return null;
        }

        ErrorPaymentResponseDto.ErrorPaymentResponseDtoBuilder<?, ?> errorPaymentResponseDto = ErrorPaymentResponseDto.builder();

        if ( approved != null ) {
            errorPaymentResponseDto.approved( approved );
        }
        errorPaymentResponseDto.errors( toErrors(violations) );

        return errorPaymentResponseDto.build();
    }
}
