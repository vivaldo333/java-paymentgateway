package com.finaro.mapper;

import com.finaro.dto.transaction.TransactionDto;
import com.finaro.persistence.entity.TransactionEntity;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-15T01:11:44+0300",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.1 (Oracle Corporation)"
)
@Component
public class TransactionMapperImpl implements TransactionMapper {

    @Autowired
    private CardMapper cardMapper;
    @Autowired
    private ClientMapper clientMapper;

    @Override
    public TransactionEntity convert(TransactionDto payment) {
        if ( payment == null ) {
            return null;
        }

        TransactionEntity.TransactionEntityBuilder<?, ?> transactionEntity = TransactionEntity.builder();

        if ( payment.getInvoice() != null ) {
            transactionEntity.invoice( payment.getInvoice() );
        }
        if ( payment.getAmount() != null ) {
            transactionEntity.amount( payment.getAmount() );
        }
        if ( payment.getCurrency() != null ) {
            transactionEntity.currency( payment.getCurrency() );
        }
        if ( payment.getCardholder() != null ) {
            transactionEntity.cardholder( clientMapper.convert( payment.getCardholder() ) );
        }
        if ( payment.getCard() != null ) {
            transactionEntity.card( cardMapper.convert( payment.getCard() ) );
        }

        return transactionEntity.build();
    }

    @Override
    public TransactionDto convert(TransactionEntity entity) {
        if ( entity == null ) {
            return null;
        }

        TransactionDto.TransactionDtoBuilder transactionDto = TransactionDto.builder();

        if ( entity.getInvoice() != null ) {
            transactionDto.invoice( entity.getInvoice() );
        }
        if ( entity.getAmount() != null ) {
            transactionDto.amount( entity.getAmount() );
        }
        if ( entity.getCurrency() != null ) {
            transactionDto.currency( entity.getCurrency() );
        }
        if ( entity.getCardholder() != null ) {
            transactionDto.cardholder( clientMapper.convert( entity.getCardholder() ) );
        }
        if ( entity.getCard() != null ) {
            transactionDto.card( cardMapper.convert( entity.getCard() ) );
        }

        return transactionDto.build();
    }
}
