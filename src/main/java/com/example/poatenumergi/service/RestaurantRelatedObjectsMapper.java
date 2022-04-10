package com.example.poatenumergi.service;

import com.example.poatenumergi.model.*;
import com.example.poatenumergi.repository.RADatabaseOperations;
import com.example.poatenumergi.repository.RestaurantDatabaseOperations;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

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
        Optional<RestaurantAdministrator> restaurantAdministrator=raDatabaseOperations.findByUsername(restaurantDTO.getRestaurantAdministratorUsername());
        return restaurantAdministrator.map(administrator -> new Restaurant(restaurantDTO.getName(), restaurantDTO.getLocation(), deliveryZonesFromStrings(restaurantDTO.getDeliveryZones()), administrator)).orElse(null);

    }
    public RestaurantDTO fromRestaurantToDTO(Restaurant restaurant){
        return new RestaurantDTO(restaurant.getName(), restaurant.getLocation(),restaurant.getDeliveryZones().stream().map(DeliveryZone::getName).collect(Collectors.toSet()),restaurant.getRestaurantAdministrator().getUsername());
    }
    public Food fromFoodDTO(FoodDTO foodDTO){
       Optional<Restaurant> restaurant=restaurantDatabaseOperations.findByName(foodDTO.getRestaurantName());
        return restaurant.map(value -> new Food(foodDTO.getName(), FoodCategory.valueOf(foodDTO.getCategory()), value)).orElse(null);
    }
    public FoodDTO toFoodDTO(Food food){
        return new FoodDTO(food.getName(),food.getCategory().getCode(),food.getRestaurant().getName());
    }


}
