package com.skiply.purchase.controller;

import com.skiply.purchase.dto.PurchaseRequest;
import com.skiply.purchase.dto.PurchaseResponse;
import com.skiply.purchase.service.PurchaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/purchase")
@Slf4j
public class PurchaseController {
    @Autowired
    private PurchaseService purchaseService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PurchaseResponse create(@RequestBody PurchaseRequest purchaseRequest) {
        log.info("Received create purchase request {}", purchaseRequest);
        return purchaseService.createPurchase(purchaseRequest);
    }

    @PutMapping("/{referenceNumber}")
    public PurchaseResponse update(@RequestBody PurchaseRequest purchaseRequest,
                                   @PathVariable String referenceNumber) {
        log.info("Received update purchase request {} for reference number {}",
                purchaseRequest,
                referenceNumber);
        return purchaseService.updatePurchase(purchaseRequest, referenceNumber);
    }

    @GetMapping("/{referenceNumber}")
    public PurchaseResponse get(@PathVariable String referenceNumber) {
        log.info("Fetch purchase request for reference number {}", referenceNumber);
        return purchaseService.getPurchase(referenceNumber);
    }
}
