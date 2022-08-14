package com.finaro.mapper;

import com.finaro.dto.payment.BasePaymentResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BasePaymentResponseMapper {
    BasePaymentResponseDto convert(Boolean approved);
}
