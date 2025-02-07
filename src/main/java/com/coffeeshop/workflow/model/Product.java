package com.coffeeshop.workflow.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class Product {
    @JsonProperty("drink_name")
    private String drinkName;
    @JsonProperty("prices")
    private Map<String, Double> prices;

    public Map<String, Double> getPrices() {
        return prices;
    }

    public void setPrices(Map<String, Double> prices) {
        this.prices = prices;
    }

    public String getDrinkName() {
        return drinkName;
    }

    public void setDrinkName(String drinkName) {
        this.drinkName = drinkName;
    }


}
