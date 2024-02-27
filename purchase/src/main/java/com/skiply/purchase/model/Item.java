package com.skiply.purchase.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class Item {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;
    private String description;
    private int count;
    private double amount;
}
