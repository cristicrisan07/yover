package com.example.poatenumergi.repository;

import com.example.poatenumergi.model.Customer;
import com.example.poatenumergi.model.DeliveryZone;
import com.example.poatenumergi.model.Restaurant;
import com.example.poatenumergi.model.RestaurantAdministrator;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RADatabaseOperations extends CrudRepository<RestaurantAdministrator,Long> {
    @Query(value = "SELECT u from DeliveryZone u where u.name=?1")
    DeliveryZone findDeliveryZoneByname(String name);
    Optional<RestaurantAdministrator> findByUsername(String username);
    Optional<RestaurantAdministrator> findByEmail(String email);
    @Query(value = "SELECT u from Restaurant u where u.name=?1")
    Optional<Restaurant> findRestaurantByname(String name);
}
