package com.skiply.purchase.service;

import com.skiply.purchase.dto.ItemRequest;
import com.skiply.purchase.dto.PurchaseRequest;
import com.skiply.purchase.dto.PurchaseResponse;
import com.skiply.purchase.exception.PurchaseNotFoundException;
import com.skiply.purchase.model.Item;
import com.skiply.purchase.model.Purchase;
import com.skiply.purchase.repository.PurchaseRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PurchaseServiceTest {
    @Mock
    private PurchaseRepository purchaseRepository;

    @InjectMocks
    private PurchaseService purchaseService;

    @Test
    public void testCreatePurchase() {

        PurchaseRequest request = createMockPurchaseRequest();

        Purchase savedPurchase = createMockPurchase(request);

        // Mock behavior
        when(purchaseRepository.save(any())).thenReturn(savedPurchase);

        // Test
        PurchaseResponse response = purchaseService.createPurchase(request);

        // Assertions
        assertNotNull(response);
        assertEquals(request.getStudentId(), response.getStudentId());
        assertEquals(request.getCardNumber(), response.getCardNumber());
        assertEquals(request.getCardHolderName(), response.getCardHolderName());
        assertEquals(request.getCardType(), response.getCardType());
        assertNotNull(response.getTimestamp());
        assertNotNull(response.getReferenceNumber());
        assertEquals(request.getItems().size(), response.getItems().size());
    }

    @Test
    public void testGetPurchase_existingPurchase() {
        // Mock data
        Purchase purchase = createMockPurchase(createMockPurchaseRequest());

        when(purchaseRepository.findById(purchase.getReferenceNumber())).thenReturn(Optional.of(purchase));

        // Perform getPurchase
        PurchaseResponse response = purchaseService.getPurchase(purchase.getReferenceNumber());

        // Verify repository method call
        verify(purchaseRepository, times(1)).findById(purchase.getReferenceNumber());

        // Verify correctness of returned PurchaseResponse
        Assertions.assertEquals(purchase.getReferenceNumber(), response.getReferenceNumber());
    }

    @Test
    public void testGetPurchase_nonExistingPurchase() {
        // Mock data
        String referenceNumber = UUID.randomUUID().toString();

        when(purchaseRepository.findById(referenceNumber)).thenReturn(Optional.empty());

        // Perform getPurchase and expect PurchaseNotFoundException
        Assertions.assertThrows(PurchaseNotFoundException.class, () -> {
            purchaseService.getPurchase(referenceNumber);
        });

        // Verify repository method call
        verify(purchaseRepository, times(1)).findById(referenceNumber);
    }

    @Test
    public void testUpdatePurchase_existingPurchase() {
        // Mock data
        PurchaseRequest purchaseRequest = createMockPurchaseRequest();

        Purchase existingPurchase = createMockPurchase(purchaseRequest);

        when(purchaseRepository.findById(existingPurchase.getReferenceNumber())).thenReturn(Optional.of(existingPurchase));

        when(purchaseRepository.save(any())).thenReturn(existingPurchase);

        // Perform updatePurchase
        PurchaseResponse response = purchaseService.updatePurchase(purchaseRequest, existingPurchase.getReferenceNumber());

        // Verify repository method call
        verify(purchaseRepository, times(1)).findById(existingPurchase.getReferenceNumber());
        verify(purchaseRepository, times(1)).save(any());

        // Verify correctness of returned PurchaseResponse
        Assertions.assertEquals(existingPurchase.getReferenceNumber(), response.getReferenceNumber());
    }

    @Test
    public void testUpdatePurchase_nonExistingPurchase() {
        // Mock data
        PurchaseRequest purchaseRequest = createMockPurchaseRequest();
        String referenceNumber = UUID.randomUUID().toString();
        when(purchaseRepository.findById(referenceNumber)).thenReturn(Optional.empty());

        // Perform updatePurchase and expect PurchaseNotFoundException
        Assertions.assertThrows(PurchaseNotFoundException.class, () -> {
            purchaseService.updatePurchase(purchaseRequest, referenceNumber);
        });

        // Verify repository method call
        verify(purchaseRepository, times(1)).findById(referenceNumber);
    }

    // Helper method to create a mock Purchase object
    private Purchase createMockPurchase(PurchaseRequest request) {
        // Create and return a mock Purchase object
        return Purchase.builder()
                .studentId(request.getStudentId())
                .cardNumber(request.getCardNumber())
                .cardHolderName(request.getCardHolderName())
                .cardType(request.getCardType())
                .timestamp(String.valueOf(LocalDateTime.now()))
                .referenceNumber(String.valueOf(UUID.randomUUID()))
                .items(createItems())
                .build();
    }

    // Helper method to create a mock PurchaseRequest object
    private PurchaseRequest createMockPurchaseRequest() {
        // Create and return a mock PurchaseRequest object
        return PurchaseRequest.builder()
                .studentId(1L)
                .cardNumber("1234567890123456")
                .cardType("Visa")
                .cardHolderName("John Doe")
                .items(createItemsRequest())
                .build();
    }

    private List<Item> createItems() {
        List<Item> items = new ArrayList<>();
        items.add(Item.builder().description("Item 1").count(1).amount(100).build());
        items.add(Item.builder().description("Item 2").count(2).amount(200).build());
        return items;
    }

    private List<ItemRequest> createItemsRequest() {
        List<ItemRequest> items = new ArrayList<>();
        items.add(ItemRequest.builder().description("Item 1").count(1).amount(100).build());
        items.add(ItemRequest.builder().description("Item 2").count(2).amount(200).build());
        return items;
    }
}
