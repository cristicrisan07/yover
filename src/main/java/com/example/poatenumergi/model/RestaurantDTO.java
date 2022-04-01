package com.example.poatenumergi.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
public class RestaurantDTO {


    private String name;
    private String location;
    Set<String> deliveryZones;

}
