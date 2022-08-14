package com.finaro.mapper;

import com.finaro.dto.transaction.CardDto;
import com.finaro.persistence.entity.CardEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-15T01:11:44+0300",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.1 (Oracle Corporation)"
)
@Component
public class CardMapperImpl extends CardMapper {

    @Override
    public CardEntity convert(CardDto dto) {
        if ( dto == null ) {
            return null;
        }

        CardEntity.CardEntityBuilder<?, ?> cardEntity = CardEntity.builder();

        cardEntity.pan( com.finaro.utils.Utils.encode(dto.getPan()) );
        cardEntity.expiry( com.finaro.utils.Utils.encode(dto.getExpiry()) );

        return cardEntity.build();
    }

    @Override
    public CardDto convert(CardEntity entity) {
        if ( entity == null ) {
            return null;
        }

        CardDto cardDto = new CardDto();

        cardDto.setPan( getMaskedDecodedText("pan", entity.getPan()) );
        cardDto.setExpiry( getMaskedDecodedText("expiry", entity.getExpiry()) );

        return cardDto;
    }
}
