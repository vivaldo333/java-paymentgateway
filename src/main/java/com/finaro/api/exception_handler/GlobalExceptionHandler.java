package com.finaro.api.exception_handler;

import com.finaro.dto.payment.ErrorPaymentResponseDto;
import com.finaro.mapper.PaymentResponseMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.ThrowableProblem;
import org.zalando.problem.spring.web.advice.general.GeneralAdviceTrait;
import org.zalando.problem.spring.web.advice.http.HttpAdviceTrait;
import org.zalando.problem.spring.web.advice.io.IOAdviceTrait;
import org.zalando.problem.spring.web.advice.network.NetworkAdviceTrait;
import org.zalando.problem.spring.web.advice.routing.RoutingAdviceTrait;
import org.zalando.problem.spring.web.advice.validation.BindAdviceTrait;

import javax.validation.ConstraintViolationException;

import static java.lang.String.valueOf;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestControllerAdvice
@ConditionalOnProperty(value = "app.exception.global-handler.enabled", havingValue = "true")
@Order(Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
public class GlobalExceptionHandler implements GeneralAdviceTrait, HttpAdviceTrait, IOAdviceTrait, NetworkAdviceTrait, RoutingAdviceTrait, BindAdviceTrait /*ProblemHandling ValidationAdviceTrait*/ {

    @Autowired
    PaymentResponseMapper paymentResponseMapper;

    @ExceptionHandler(AbstractThrowableProblem.class)
    public ResponseEntity<ThrowableProblem> handleBusinessException(
            final AbstractThrowableProblem exception) {
        if (log.isErrorEnabled()) {
            log.error("Throwable problem: {}", valueOf(exception));
        }
        return ResponseEntity.status(exception.getStatus().getStatusCode())
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(exception);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorPaymentResponseDto> handle(
            final MethodArgumentNotValidException e, final NativeWebRequest request) {
        log.error("MethodArgumentNotValidException: ", e);
        var responseBody = paymentResponseMapper.convert(Boolean.FALSE, e.getBindingResult());
        return ResponseEntity.badRequest()
                .body(responseBody);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorPaymentResponseDto> handle(
            final ConstraintViolationException e, final NativeWebRequest request) {
        log.error("ConstraintViolationException: ", e);
        var responseBody = paymentResponseMapper.convert(Boolean.FALSE, e.getConstraintViolations());
        return ResponseEntity.badRequest()
                .body(responseBody);
    }
}
