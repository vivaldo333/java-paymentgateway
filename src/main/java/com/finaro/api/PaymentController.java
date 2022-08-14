package com.finaro.api;

import com.finaro.dto.payment.BasePaymentResponseDto;
import com.finaro.dto.transaction.TransactionDto;
import com.finaro.mapper.BasePaymentResponseMapper;
import com.finaro.service.AuditService;
import com.finaro.service.TransactionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Slf4j
@RestController
@RequestMapping(path = "transactions")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class PaymentController {

    TransactionService transactionService;
    AuditService auditService;
    BasePaymentResponseMapper basePaymentResponseMapper;

    @PostMapping
    public ResponseEntity<? extends BasePaymentResponseDto> submitPayment(
            @RequestBody @NotNull(message = "Payment is required.") @Valid TransactionDto payment) {
        var maskedTransaction = transactionService.save(payment);
        auditService.log(maskedTransaction);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(basePaymentResponseMapper.convert(Boolean.TRUE));
    }

    @GetMapping(path = "{invoice}")
    public ResponseEntity<TransactionDto> retrieveTransaction(
            @PathVariable @NotBlank(message = "Invoice is required.") Long invoice) {
        var transaction = transactionService.get(invoice);
        log.info("retrieved transaction: {}", transaction);
        return ResponseEntity.ok(transaction);
    }
}
