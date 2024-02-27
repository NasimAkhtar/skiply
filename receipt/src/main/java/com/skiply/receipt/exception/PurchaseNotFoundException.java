package com.skiply.receipt.exception;

public class PurchaseNotFoundException extends RuntimeException {
    public PurchaseNotFoundException(String referenceNumber) {
        super("Purchase with reference " + referenceNumber +" not found");
    }
}
