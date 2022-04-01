package com.example.poatenumergi.service;

import com.example.poatenumergi.model.Customer;
import com.example.poatenumergi.model.CustomerDTO;
import com.example.poatenumergi.model.RestaurantAdministrator;
import com.example.poatenumergi.model.RestaurantAdministratorDTO;



public class AccountsMapper {
    public Customer fromDTOtoCustomer(CustomerDTO customerDTO){
        return new Customer(customerDTO.getUsername(), customerDTO.getEmail(), customerDTO.getPassword(), customerDTO.getFirstName(), customerDTO.getLastName());
    }
    public RestaurantAdministrator fromDTOtoRA(RestaurantAdministratorDTO restaurantAdministratorDTO)
    {
        return new RestaurantAdministrator(restaurantAdministratorDTO.getUsername(),restaurantAdministratorDTO.getEmail(),restaurantAdministratorDTO.getPassword());
    }
}
