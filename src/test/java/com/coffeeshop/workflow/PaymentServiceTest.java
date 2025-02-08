package com.coffeeshop.workflow;

import com.coffeeshop.workflow.service.PaymentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PaymentServiceTest {

    @Autowired
    private PaymentService paymentService;

    @Test
    void testUserPaymentsDataNotNull() {
        Map<String, Double> userPayments = paymentService.getUserPayments();
        assertNotNull(userPayments, "User payments data.");
    }

    @Test
    void testAmountUserOwesDataNotNull() {
        Map<String, Double> amountOwed = paymentService.getAmountUserOwes();
        assertNotNull(amountOwed, "Amounts owed by users Data.");
    }

    @Test
    void testTotalBalancesDataNotNull() {
        Map<String, Double> balances = paymentService.getTotalBalance();
        assertNotNull(balances, "Balances Data.");
    }

    @Test
    void testCorrectBalanceComputation() {
        Map<String, Double> balances = paymentService.getTotalBalance();

        balances.forEach((user, balance) -> {
            Double paid = paymentService.getUserPayments().getOrDefault(user, 0.0);
            Double owed = paymentService.getAmountUserOwes().getOrDefault(user, 0.0);
            assertEquals(balance, paid - owed,
                    "Balance computation for user " + user + " ");
        });
    }

    @Test
    void testPaymentServiceHandlesNonExistentUserGracefully() {
        String nonExistentUser = "TestUser";
        assertEquals(0.0, paymentService.getUserPayments().getOrDefault(nonExistentUser, 0.0),
                "Expected default value for a non-existent user's payment.");
    }
}
