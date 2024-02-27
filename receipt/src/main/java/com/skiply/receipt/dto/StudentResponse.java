package com.skiply.receipt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class StudentResponse {
    private Long id;
    private String name;
    private String grade;
    private String school;
    private String contact;
}
