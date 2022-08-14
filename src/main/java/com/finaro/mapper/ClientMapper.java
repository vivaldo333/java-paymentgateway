package com.finaro.mapper;

import com.finaro.dto.transaction.ClientDto;
import com.finaro.persistence.entity.ClientEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public abstract class ClientMapper extends AbstractMaskMapper {

    @Mapping(target = "name", expression = "java(com.finaro.utils.Utils.encode(dto.getName()))")
    public abstract ClientEntity convert(ClientDto dto);

    @Mapping(target = "name", expression = "java(getMaskedDecodedText(\"name\", entity.getName()))")
    public abstract ClientDto convert(ClientEntity entity);
}
