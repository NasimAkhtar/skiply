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
public class ReceiptResponse {
    private String timestamp;
    private Long studentId;
    private String studentName;
    private String schoolName;
    private String grade;
    private String transactionReferenceNumber;
    private String cardNumber;
    private String cardHolderName;
    private String cardType;
    private List<ItemResponse> items;
    private Double total;
}
