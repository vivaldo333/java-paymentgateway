package com.finaro.service;

import com.finaro.dto.transaction.TransactionDto;

public interface AuditService {
    void log(TransactionDto maskedTransaction);
}
