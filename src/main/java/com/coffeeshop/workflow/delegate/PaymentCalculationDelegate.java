package com.coffeeshop.workflow.delegate;

import com.coffeeshop.workflow.service.PaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Named;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.Map;
import java.util.logging.Logger;

@Named
public class PaymentCalculationDelegate implements JavaDelegate {

    private static final Logger logger = Logger.getLogger(PaymentCalculationDelegate.class.getName());
    private final PaymentService paymentService;

    public PaymentCalculationDelegate(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        logger.info("PaymentCalculationDelegate execution started");
        try {
            Map<String, Double> totalPaidPerUser = paymentService.getUserPayments();
            Map<String, Double> totalAmountOwesPerUser = paymentService.getAmountUserOwes();
            Map<String, Double> balances = paymentService.getTotalBalance();

            ObjectMapper objectMapper = new ObjectMapper();
            delegateExecution.setVariable("totalPaidPerUser", objectMapper.writeValueAsString(totalPaidPerUser));
            delegateExecution.setVariable("totalAmountOwesPerUser", objectMapper.writeValueAsString(totalAmountOwesPerUser));
            delegateExecution.setVariable("balancesPerUser", objectMapper.writeValueAsString(balances));
            logger.info("PaymentCalculationDelegate execution completed");
        }catch(Exception e){
            logger.severe("Error during PaymentCalculationDelegate execution: " + e.getMessage());
            throw e;
        }
    }
}
