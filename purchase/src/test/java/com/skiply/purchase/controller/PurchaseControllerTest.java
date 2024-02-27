package com.skiply.purchase.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skiply.purchase.dto.ItemRequest;
import com.skiply.purchase.dto.PurchaseRequest;
import com.skiply.purchase.dto.PurchaseResponse;
import com.skiply.purchase.exception.PurchaseNotFoundException;
import com.skiply.purchase.model.Item;
import com.skiply.purchase.service.PurchaseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PurchaseController.class)
public class PurchaseControllerTest {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PurchaseService purchaseService;

    // Positive test case for create method
    @Test
    public void testCreatePurchase() throws Exception {
        PurchaseRequest request = createMockPurchaseRequest();

        PurchaseResponse response = new PurchaseResponse();

        when(purchaseService.createPurchase(any())).thenReturn(response);

        mockMvc.perform(post("/api/v1/purchase")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    // Negative test case for create method
    @Test
    public void testCreatePurchaseFailure() throws Exception {
        PurchaseRequest request = createMockPurchaseRequest();

        // Mock PurchaseService to throw an exception when createPurchase is called
        when(purchaseService.createPurchase(any())).thenThrow(new RuntimeException("Failed to create purchase"));

        assertThrows(Exception.class,
                () -> {
                    mockMvc.perform(post("/api/v1/purchase")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(request)))
                            .andReturn();
                });
    }

    // Positive test case for update method
    @Test
    public void testUpdatePurchase() throws Exception {
        PurchaseRequest request = createMockPurchaseRequest();

        // Mock PurchaseService to return a valid response when updatePurchase is called
        when(purchaseService.updatePurchase(any(), any())).thenReturn(PurchaseResponse.builder().build());

        mockMvc.perform(put("/api/v1/purchase/{referenceNumber}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    // Negative test case for update method
    @Test
    public void testUpdatePurchaseFailure() throws Exception {
        PurchaseRequest request = createMockPurchaseRequest();

        // Mock PurchaseService to throw an exception when updatePurchase is called
        when(purchaseService.updatePurchase(any(), any()))
                .thenThrow(new RuntimeException("Failed to update purchase"));

        assertThrows(Exception.class,
                () -> {
                    mockMvc.perform(put("/api/v1/purchase/{referenceNumber}", 1)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(request)))
                            .andReturn();
                });
    }

    // Positive test case for get method
    @Test
    public void testGetPurchase() throws Exception {
        // Mock PurchaseService to return a valid response when getPurchase is called
        when(purchaseService.getPurchase(any())).thenReturn(PurchaseResponse.builder().build());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/purchase/{referenceNumber}", 1))
                .andExpect(status().isOk());
    }

    // Negative test case for get method
    @Test
    public void testGetPurchaseFailure() throws Exception {
        // Mock PurchaseService to throw an exception when getPurchase is called with the invalid reference number
        when(purchaseService.getPurchase("1"))
                .thenThrow(new PurchaseNotFoundException("1"));


        assertThrows(Exception.class,
                () -> {
                    mockMvc.perform(MockMvcRequestBuilders
                                    .get("/api/v1/purchase/{referenceNumber}",
                                            "1"))
                            .andExpect(status().isNotFound());
                });
    }

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