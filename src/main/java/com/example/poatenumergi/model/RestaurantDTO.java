package com.example.poatenumergi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@AllArgsConstructor
public class RestaurantDTO {


    private String name;
    private String location;
    Set<String> deliveryZones;
    private final String restaurantAdministratorUsername;


}
