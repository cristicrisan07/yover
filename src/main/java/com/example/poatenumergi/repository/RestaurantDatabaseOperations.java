package com.example.poatenumergi.repository;

import com.example.poatenumergi.model.Restaurant;
import com.example.poatenumergi.model.RestaurantAdministrator;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RestaurantDatabaseOperations extends CrudRepository<Restaurant,Long> {
    Optional<Restaurant> findByName(String name);
    @Query(value = "SELECT u from Restaurant u where u.restaurantAdministrator.username=?1")
    Optional<Restaurant> findByRestaurantAdministratorUsername(String username);
}
