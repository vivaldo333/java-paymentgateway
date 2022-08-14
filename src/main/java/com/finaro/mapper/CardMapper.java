package com.finaro.mapper;

import com.finaro.dto.transaction.CardDto;
import com.finaro.persistence.entity.CardEntity;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValueCheckStrategy;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public abstract class CardMapper extends AbstractMaskMapper {
    private static final int TEXT_CARD_EXPIRE_DATE_LENGTH = 4;
    private static final DateTimeFormatter EXPIRE_CARD_DATE_FORMATTER = DateTimeFormatter.ofPattern("MMyy");

    @Named("cardEntityToCardDto")
    @Mapping(target = "pan", expression = "java(com.finaro.utils.Utils.encode(dto.getPan()))")
    @Mapping(target = "expiry", expression = "java(com.finaro.utils.Utils.encode(dto.getExpiry()))")
    public abstract CardEntity convert(CardDto dto);

    @Mapping(target = "pan", expression = "java(getMaskedDecodedText(\"pan\", entity.getPan()))")
    @Mapping(target = "expiry", expression = "java(getMaskedDecodedText(\"expiry\", entity.getExpiry()))")
    public abstract CardDto convert(CardEntity entity);

    public Optional<LocalDate> convert(String textDate) {
        if (isNotEmpty(textDate) && StringUtils.length(textDate) == TEXT_CARD_EXPIRE_DATE_LENGTH) {
            var yearMonth = YearMonth.parse(textDate, EXPIRE_CARD_DATE_FORMATTER);
            return Optional.of(yearMonth.atEndOfMonth());
        }
        return Optional.empty();
    }
}
