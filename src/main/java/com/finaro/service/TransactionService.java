package com.finaro.service;

import com.finaro.dto.transaction.TransactionDto;

public interface TransactionService {
    TransactionDto save(TransactionDto payment);

    TransactionDto get(Long invoice);
}
