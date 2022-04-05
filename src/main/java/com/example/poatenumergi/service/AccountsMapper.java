package com.example.poatenumergi.service;

import com.example.poatenumergi.model.Customer;
import com.example.poatenumergi.model.CustomerDTO;
import com.example.poatenumergi.model.RestaurantAdministrator;
import com.example.poatenumergi.model.RestaurantAdministratorDTO;
import org.springframework.stereotype.Service;


@Service
public class AccountsMapper {
    public Customer fromDTOtoCustomer(CustomerDTO customerDTO){
        return new Customer(customerDTO.getUsername(), customerDTO.getEmail(), customerDTO.getPassword(), customerDTO.getFirstName(), customerDTO.getLastName());
    }
    public RestaurantAdministrator fromDTOtoRA(RestaurantAdministratorDTO restaurantAdministratorDTO)
    {
        return new RestaurantAdministrator(restaurantAdministratorDTO.getUsername(),restaurantAdministratorDTO.getEmail(),restaurantAdministratorDTO.getPassword());
    }
    public RestaurantAdministratorDTO fromRAtoDTO(RestaurantAdministrator restaurantAdministrator){

        return new RestaurantAdministratorDTO(restaurantAdministrator.getUsername(), restaurantAdministrator.getEmail(), restaurantAdministrator.getPassword());

    }
    public CustomerDTO fromCustomertoDTO(Customer customer){
        return  new CustomerDTO(customer.getUsername(), customer.getEmail() , customer.getPassword(), customer.getFirstName(), customer.getLastName());
    }
}
