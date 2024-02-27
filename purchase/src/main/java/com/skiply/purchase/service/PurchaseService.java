package com.skiply.purchase.service;

import com.skiply.purchase.dto.ItemResponse;
import com.skiply.purchase.dto.PurchaseRequest;
import com.skiply.purchase.dto.PurchaseResponse;
import com.skiply.purchase.exception.PurchaseNotFoundException;
import com.skiply.purchase.model.Item;
import com.skiply.purchase.model.Purchase;
import com.skiply.purchase.repository.PurchaseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class PurchaseService {
    @Autowired
    private PurchaseRepository purchaseRepository;

    public PurchaseResponse createPurchase(PurchaseRequest purchaseRequest) {
        log.info("Making purchase with {}", purchaseRequest);

        Purchase saved = purchaseRepository.save(buildPurchaseModel(purchaseRequest));

        return buildPurchaseResponse(saved);
    }

    public PurchaseResponse getPurchase(String referenceNumber) {
        log.info("Fetching purchase with reference number {}", referenceNumber);

        Optional<Purchase> purchase = purchaseRepository.findById(referenceNumber);

        if (!purchase.isPresent()) {
            throw new PurchaseNotFoundException(referenceNumber);
        }

        return buildPurchaseResponse(purchase.get());
    }

    public PurchaseResponse updatePurchase(PurchaseRequest purchaseRequest, String referenceNumber) {
        Optional<Purchase> purchase =  purchaseRepository.findById(referenceNumber);

        if (!purchase.isPresent()) throw new PurchaseNotFoundException(referenceNumber);

        Purchase model = buildPurchaseModel(purchaseRequest);

        model.setReferenceNumber(referenceNumber);

        Purchase updated = purchaseRepository.save(model);

        return buildPurchaseResponse(updated);
    }

    private static PurchaseResponse buildPurchaseResponse(Purchase purchase) {
        return PurchaseResponse
                .builder()
                .studentId(purchase.getStudentId())
                .cardNumber(purchase.getCardNumber())
                .cardHolderName(purchase.getCardHolderName())
                .cardType(purchase.getCardType())
                .timestamp(purchase.getTimestamp())
                .referenceNumber(purchase.getReferenceNumber())
                .items(purchase.getItems().stream()
                        .map(item -> ItemResponse.builder()
                                .id(item.getId())
                                .description(item.getDescription())
                                .amount(item.getAmount())
                                .count(item.getCount())
                                .build())
                        .toList())
                .build();
    }

    private Purchase buildPurchaseModel(PurchaseRequest purchaseRequest) {
        return Purchase
                .builder()
                .studentId(purchaseRequest.getStudentId())
                .cardNumber(purchaseRequest.getCardNumber())
                .cardHolderName(purchaseRequest.getCardHolderName())
                .cardType(purchaseRequest.getCardType())
                .timestamp(String.valueOf(LocalDateTime.now()))
                .referenceNumber(String.valueOf(UUID.randomUUID()))
                .items(purchaseRequest.getItems().stream().map(item -> Item.builder()
                        .description(item.getDescription())
                        .amount(item.getAmount())
                        .count(item.getCount())
                        .build()).toList())
                .build();
    }

}
