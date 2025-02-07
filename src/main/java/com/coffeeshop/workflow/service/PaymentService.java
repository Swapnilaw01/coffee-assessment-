package com.coffeeshop.workflow.service;

import com.coffeeshop.workflow.model.Order;
import com.coffeeshop.workflow.model.Payment;
import com.coffeeshop.workflow.model.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PaymentService {
    private final Map<String, Double> totalPaidPerUser = new HashMap<>();
    private final Map<String, Double> totalAmountOwesPerUser = new HashMap<>();
    private final Map<String, Double> balances = new HashMap<>();

    public PaymentService() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            try {
                List<Product> products = Arrays.asList(objectMapper.readValue(new File("src/main/resources/data/products.json"), Product[].class));
                List<Order> orders = Arrays.asList(objectMapper.readValue(new File("src/main/resources/data/orders.json"), Order[].class));
                List<Payment> payments = Arrays.asList(objectMapper.readValue(new File("src/main/resources/data/payments.json"), Payment[].class));

                // Compute total amount owed/DuePerUser
                for (Order order : orders) {
                    String user = order.getUser();
                    String drink = order.getDrink();
                    String size = order.getSize();

                    products.stream()
                            .filter(product -> product.getDrinkName().equals(drink))
                            .findFirst()
                            .ifPresent(product -> {
                                Map<String, Double> prices = product.getPrices();
                                totalAmountOwesPerUser.put(user, totalAmountOwesPerUser.getOrDefault(user, 0.0) + prices.get(size));
                            });
                }

                // Compute total amount paid
                for (Payment payment : payments) {
                    String user = payment.getUser();
                    Double amount = ((Number) payment.getAmount()).doubleValue();
                    totalPaidPerUser.put(user, totalPaidPerUser.getOrDefault(user, 0.0) + amount);
                }

                // Calculate balances
                for (String user : totalAmountOwesPerUser.keySet()) {
                    double due = totalAmountOwesPerUser.get(user);
                    double paid = totalPaidPerUser.getOrDefault(user, 0.0);
                    balances.put(user, paid - due);
                }


            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public Map<String, Double> getUserPayments() {
        return totalPaidPerUser;
    }

    public Map<String, Double> getAmountUserOwes() {
        return totalAmountOwesPerUser;
    }

    public Map<String, Double> getTotalBalance(){
        return balances;
    }
}
