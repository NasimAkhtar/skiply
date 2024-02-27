package com.skiply.receipt.controller;

import com.skiply.receipt.dto.ReceiptResponse;
import com.skiply.receipt.service.ReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/receipts")
public class ReceiptController {
    @Autowired
    private ReceiptService receiptService;

    @GetMapping("/{transactionReferenceNumber}")
    public ReceiptResponse receipt(@PathVariable String transactionReferenceNumber) {
        return receiptService.receipt(transactionReferenceNumber);
    }
}
