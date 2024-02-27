package com.skiply.student.exception;


import lombok.AllArgsConstructor;

@AllArgsConstructor
public class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException(Long id) {
        super("Could not find student with ID: " + id);
    }

}
