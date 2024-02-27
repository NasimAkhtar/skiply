package com.skiply.purchase.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Purchase {
    @Id
    private String referenceNumber;
    private Long studentId;
    private String cardNumber;
    private String cardHolderName;
    private String cardType;
    private String timestamp;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Item> items;
}
