package com.finaro.service.impl;

import com.finaro.dto.transaction.TransactionDto;
import com.finaro.exception.NotFoundException;
import com.finaro.mapper.TransactionMapper;
import com.finaro.persistence.repository.TransactionRepository;
import com.finaro.service.TransactionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    TransactionRepository transactionRepository;
    TransactionMapper transactionMapper;
    MaskingService maskingService;


    @Override
    public TransactionDto save(TransactionDto payment) {
        var paymentForStore = transactionMapper.convertToDbTransaction(payment);
        var dbPayment = transactionRepository.save(paymentForStore);
        log.info("stored payment: {}", maskingService.toSecureJson(dbPayment));
        return transactionMapper.convert(dbPayment);
    }

    @Override
    public TransactionDto get(Long invoice) {
        var dbTransaction = transactionRepository.findById(invoice)
                .orElseThrow(() -> new NotFoundException("Not found", "Transaction with invoice %s was not found".formatted(invoice)));
        return transactionMapper.convert(dbTransaction);
    }
}
