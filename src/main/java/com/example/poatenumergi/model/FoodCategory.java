package com.example.poatenumergi.model;

public enum FoodCategory {
    Beverage("Beverage"), Lunch("Lunch"), Desert("Desert"),Breakfast("Breakfast");

    private String code;

    private FoodCategory(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}