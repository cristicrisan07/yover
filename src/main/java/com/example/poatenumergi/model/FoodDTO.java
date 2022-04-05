package com.example.poatenumergi.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
public class FoodDTO {

    private final String name;
    private final String category;
    private final String restaurantName;


}
