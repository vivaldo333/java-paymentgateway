package com.finaro.mapper;

import com.finaro.dto.transaction.TransactionDto;
import com.finaro.persistence.entity.TransactionEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {CardMapper.class, ClientMapper.class})
public interface TransactionMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "invoice", target = "invoice")
    @Mapping(source = "amount", target = "amount")
    @Mapping(source = "currency", target = "currency")
    @Mapping(source = "cardholder", target = "cardholder")
    @Mapping(source = "card", target = "card", qualifiedByName = "cardEntityToCardDto")
    TransactionEntity convert(TransactionDto payment);

    default TransactionEntity convertToDbTransaction(TransactionDto payment) {
        var dbTransaction = convert(payment);
        dbTransaction.getCard().addTransaction(dbTransaction);
        dbTransaction.getCardholder().addTransaction(dbTransaction);
        return dbTransaction;
    }

    TransactionDto convert(TransactionEntity entity);
}
