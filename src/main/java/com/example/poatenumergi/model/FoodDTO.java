package com.example.poatenumergi.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
public class FoodDTO {

    private String name;
    private String category;
    private String restaurantName;


}
