package com.example.poatenumergi.repository;

import com.example.poatenumergi.model.Restaurant;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RestaurantDatabaseOperations extends CrudRepository<Restaurant,Long> {
    Optional<Restaurant> findByName(String name);
}
