package com.example.poatenumergi.repository;

import com.example.poatenumergi.model.Food;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodDatabaseOperations extends CrudRepository<Food,Long> {

}
