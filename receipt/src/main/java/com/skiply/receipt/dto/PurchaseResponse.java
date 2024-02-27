package com.skiply.receipt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PurchaseResponse {
    private Long studentId;
    private String cardNumber;
    private String cardHolderName;
    private String cardType;
    private String timestamp;
    private String referenceNumber;
    private List<ItemResponse> items;
}
