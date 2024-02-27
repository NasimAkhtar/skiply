package com.skiply.receipt.controller;

import com.skiply.receipt.dto.ReceiptResponse;
import com.skiply.receipt.service.ReceiptService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.*;

@WebMvcTest(ReceiptController.class)
@AutoConfigureMockMvc
public class ReceiptControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReceiptService receiptService;

    @InjectMocks
    private ReceiptController receiptController;

    @Test
    void testGetReceiptByTransactionReferenceNumber() throws Exception {
        // Given
        when(receiptService.receipt("123")).thenReturn(ReceiptResponse.builder().build());

        // When-Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/receipts/{transactionReferenceNumber}", "123"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(receiptService, times(1)).receipt("123");
    }
}
