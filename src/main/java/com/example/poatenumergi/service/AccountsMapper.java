package com.example.poatenumergi.service;

import com.example.poatenumergi.model.Customer;
import com.example.poatenumergi.model.CustomerDTO;
import com.example.poatenumergi.model.RestaurantAdministrator;
import com.example.poatenumergi.model.RestaurantAdministratorDTO;
import org.springframework.stereotype.Service;


@Service
public class AccountsMapper {
    /**
     *
     * @param customerDTO customer data received from website.
     * @return a Customer instance, necessary for adding a customer account to database.
     */
    public Customer fromDTOtoCustomer(CustomerDTO customerDTO){
        return new Customer(customerDTO.getUsername(), customerDTO.getEmail(), customerDTO.getPassword(), customerDTO.getFirstName(), customerDTO.getLastName());
    }

    /**
     *
     * @param restaurantAdministratorDTO restaurant administrator data received from website.
     * @return a RestaurantAdministrator instance, necessary for adding a restaurant administrator account to database.
     */
    public RestaurantAdministrator fromDTOtoRA(RestaurantAdministratorDTO restaurantAdministratorDTO)
    {
        return new RestaurantAdministrator(restaurantAdministratorDTO.getUsername(),restaurantAdministratorDTO.getEmail(),restaurantAdministratorDTO.getPassword());
    }

    /**
     *
     * @param restaurantAdministrator restaurant administrator account data received from database.
     * @return a RestaurantAdministratorDTO instance, necessary for sending account data to the client side.
     */
    public RestaurantAdministratorDTO fromRAtoDTO(RestaurantAdministrator restaurantAdministrator){

        return new RestaurantAdministratorDTO(restaurantAdministrator.getUsername(), restaurantAdministrator.getEmail(), restaurantAdministrator.getPassword());

    }

    /**
     *
     * @param customer Customer account data received from database.
     * @return a CustomerDTO, necessary for sending account data to the client side.
     */
    public CustomerDTO fromCustomertoDTO(Customer customer){
        return  new CustomerDTO(customer.getUsername(), customer.getEmail() , customer.getPassword(), customer.getFirstName(), customer.getLastName());
    }
}
