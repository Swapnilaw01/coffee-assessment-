package com.coffeeshop.workflow.controller;

import com.coffeeshop.workflow.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/amountsPaid")
    public Map<String, Double> getAmountsPaid() {
        return paymentService.getUserPayments();
    }

    @GetMapping("/amountsOwed")
    public Map<String, Double> getAmountsOwed() {
        return paymentService.getAmountUserOwes();
    }
}
