package com.coffeeshop.workflow.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Order {
    @JsonProperty("user")
    private String user;
    @JsonProperty("drink")
    private String drink;
    @JsonProperty("size")
    private String size;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDrink() {
        return drink;
    }

    public void setDrink(String drink) {
        this.drink = drink;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
