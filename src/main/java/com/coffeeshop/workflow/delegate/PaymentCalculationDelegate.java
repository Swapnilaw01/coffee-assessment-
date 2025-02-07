package com.coffeeshop.workflow.delegate;

import com.coffeeshop.workflow.service.PaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Named;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.HashMap;
import java.util.Map;

@Named
public class PaymentCalculationDelegate implements JavaDelegate {

    private final PaymentService paymentService;

    public PaymentCalculationDelegate(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        delegateExecution.setVariable("var1", "Delegate Called");

        Map<String, Double> totalPaidPerUser = paymentService.getUserPayments();
        Map<String, Double> totalAmountOwesPerUser = paymentService.getAmountUserOwes();
        Map<String, Double> balances = paymentService.getTotalBalance();

        ObjectMapper objectMapper = new ObjectMapper();
        delegateExecution.setVariable("totalPaidPerUser", objectMapper.writeValueAsString(totalPaidPerUser));
        delegateExecution.setVariable("totalAmountOwesPerUser", objectMapper.writeValueAsString(totalAmountOwesPerUser));
        delegateExecution.setVariable("balancesPerUser", objectMapper.writeValueAsString(balances));

    }
}
