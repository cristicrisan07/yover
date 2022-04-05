package com.example.poatenumergi.service;

import com.example.poatenumergi.model.*;
import com.example.poatenumergi.repository.RADatabaseOperations;
import com.example.poatenumergi.repository.RestaurantDatabaseOperations;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RestaurantRelatedObjectsMapper {

    private final RADatabaseOperations raDatabaseOperations;
    private final RestaurantDatabaseOperations restaurantDatabaseOperations;

    private Set<DeliveryZone> deliveryZonesFromStrings(Set<String> strings){
           Set<DeliveryZone> deliveryZoneSet=new HashSet<>();
                    for(String deliveryZone: strings){
                        deliveryZoneSet.add(raDatabaseOperations.findDeliveryZoneByname(deliveryZone));
                    }
                    return deliveryZoneSet;
    }
    public Restaurant fromRestaurantDTO(RestaurantDTO restaurantDTO){
      return new Restaurant(restaurantDTO.getName(), restaurantDTO.getLocation(), deliveryZonesFromStrings(restaurantDTO.getDeliveryZones()));
    }
    public Food fromFoodDTO(FoodDTO foodDTO){
       Optional<Restaurant> restaurant=restaurantDatabaseOperations.findByName(foodDTO.getRestaurantName());
        return restaurant.map(value -> new Food(foodDTO.getName(), FoodCategory.valueOf(foodDTO.getCategory()), value)).orElse(null);
    }
    public FoodDTO toFoodDTO(Food food){
        return new FoodDTO(food.getName(),food.getCategory().getCode(),food.getRestaurant().getName());
    }


}
