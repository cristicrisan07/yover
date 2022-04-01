package com.example.poatenumergi.repository;

import com.example.poatenumergi.model.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerDatabaseOperations extends CrudRepository<Customer,Long> {
    Optional<Customer> findByUsername(String username);
    Optional<Customer> findByEmail(String email);
}