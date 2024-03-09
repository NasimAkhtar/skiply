package com.skiply.receipt.controller;

import com.skiply.receipt.dto.ReceiptResponse;
import com.skiply.receipt.exception.PurchaseNotFoundException;
import com.skiply.receipt.exception.StudentNotFoundException;
import com.skiply.receipt.service.ReceiptService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1/receipts")
@Slf4j
public class ReceiptController {
    @Autowired
    private ReceiptService receiptService;

    @GetMapping("/{transactionReferenceNumber}")
    @CircuitBreaker(name = "receipt", fallbackMethod = "fallbackMethod")
    @TimeLimiter(name = "receipt")
    @Retry(name = "receipt")
    public CompletableFuture<ReceiptResponse> receipt(@PathVariable String transactionReferenceNumber) {
        return CompletableFuture.supplyAsync(() -> receiptService.receipt(transactionReferenceNumber));
    }

    public CompletableFuture<ReceiptResponse> fallbackMethod(String transactionReferenceNumber,
                                          RuntimeException exception) {
        if (exception instanceof StudentNotFoundException)
            log.error("Can not fetch student data");

        if (exception instanceof PurchaseNotFoundException)
            log.error("Can not fetch purchase data");

        return CompletableFuture
                .supplyAsync(() -> ReceiptResponse
                        .builder()
                        .build());
    }
}
