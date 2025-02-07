package com.coffeeshop.workflow.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Payment {
    @JsonProperty("user")
    private String user;
    @JsonProperty("amount")
    private double amount;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
