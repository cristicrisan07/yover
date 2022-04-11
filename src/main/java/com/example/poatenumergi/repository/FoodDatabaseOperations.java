package com.example.poatenumergi.repository;

import com.example.poatenumergi.model.Food;
import com.example.poatenumergi.model.FoodCategory;
import com.example.poatenumergi.model.Restaurant;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface FoodDatabaseOperations extends CrudRepository<Food,Long> {
    Optional<List<Food>> findAllByRestaurantAndCategory(Restaurant restaurant, FoodCategory foodCategory);
    Optional<List<Food>> findAllByRestaurant(Restaurant restaurant);
}
