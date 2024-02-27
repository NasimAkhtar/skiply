package com.skiply.receipt.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ReceiptAdvice {
    @ExceptionHandler(StudentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public String handeStudentNotFoundException(StudentNotFoundException exception) {
        return exception.getMessage();
    }


    @ExceptionHandler(PurchaseNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public String handePurchaseNotFoundException(PurchaseNotFoundException exception) {
        return exception.getMessage();
    }

}
