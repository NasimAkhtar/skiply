package com.skiply.receipt.service;

import com.skiply.receipt.dto.PurchaseResponse;
import com.skiply.receipt.dto.ReceiptResponse;
import com.skiply.receipt.dto.StudentResponse;
import com.skiply.receipt.exception.PurchaseNotFoundException;
import com.skiply.receipt.exception.StudentNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReceiptService {
    private final WebClient.Builder webClientBuilder;

    public ReceiptResponse receipt(String transactionReferenceNumber) {
        log.info("Making Receipt for transaction reference number {}" , transactionReferenceNumber);

        PurchaseResponse purchase = purchase(transactionReferenceNumber);
        log.info("Purchase Details: {}" , purchase);

        StudentResponse student = student(purchase.getStudentId());
        log.info("Student Details: {}" , student);

        return buildReceiptResponse(purchase, student);
    }

    private PurchaseResponse purchase(String transactionReferenceNumber) {
        return this.webClientBuilder
                .build()
                .get()
                .uri("http://purchase/api/v1/purchase/{transactionReferenceNumber}",
                        transactionReferenceNumber)
                .retrieve()
                .bodyToMono(PurchaseResponse.class)
                .onErrorResume(throwable -> Mono.error(new PurchaseNotFoundException(transactionReferenceNumber)))
                .block();
    }

    private StudentResponse student(Long id) {
        return this.webClientBuilder
                .build()
                .get()
                .uri("http://student/api/v1/students/{id}", id)
                .retrieve()
                .bodyToMono(StudentResponse.class)
                .onErrorResume(throwable -> Mono.error(new StudentNotFoundException(id)))
                .block();
    }

    private static ReceiptResponse buildReceiptResponse(PurchaseResponse purchase, StudentResponse student) {
        return ReceiptResponse.builder()
                .timestamp(purchase.getTimestamp())
                .studentId(student.getId())
                .studentName(student.getName())
                .schoolName(student.getSchool())
                .grade(student.getGrade())
                .transactionReferenceNumber(purchase.getReferenceNumber())
                .cardType(purchase.getCardType())
                .cardNumber(purchase.getCardNumber())
                .cardHolderName(purchase.getCardHolderName())
                .items(purchase.getItems())
                .total(purchase.getItems().stream().mapToDouble(
                        item -> item.getCount() * item.getAmount()
                ).sum())
                .build();
    }
}
