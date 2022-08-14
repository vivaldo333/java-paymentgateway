package com.finaro.mapper;

import com.finaro.dto.payment.BasePaymentResponseDto;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-15T01:11:44+0300",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.1 (Oracle Corporation)"
)
@Component
public class BasePaymentResponseMapperImpl implements BasePaymentResponseMapper {

    @Override
    public BasePaymentResponseDto convert(Boolean approved) {
        if ( approved == null ) {
            return null;
        }

        BasePaymentResponseDto.BasePaymentResponseDtoBuilder<?, ?> basePaymentResponseDto = BasePaymentResponseDto.builder();

        basePaymentResponseDto.approved( approved );

        return basePaymentResponseDto.build();
    }
}
