package com.example.poatenumergi.service;

import com.example.poatenumergi.model.*;
import com.example.poatenumergi.repository.CustomerDatabaseOperations;
import com.example.poatenumergi.repository.RADatabaseOperations;
import com.example.poatenumergi.repository.RestaurantDatabaseOperations;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerOperationsService {

    private final CustomerDatabaseOperations customerDatabaseOperations;
    private final RestaurantDatabaseOperations restaurantDatabaseOperations;
    private final AccountsMapper accountsMapper;
    private final RestaurantRelatedObjectsMapper restaurantRelatedObjectsMapper;
    private final String secretKey = "JHKLXABYZC!!!!";

    private static Logger LOGGER = LoggerFactory.getLogger(CustomerOperationsService.class);

    /**
     * Method that saves a new Customer account into the database, if the data is valid.
     * @param customerDTO account information of a customer received from the client side.
     * @return status message
     */
    public String createCustomer(CustomerDTO customerDTO){
        String validityOfCustomerData=AccountsValidator.isCustomerAccountValid(customerDTO);
        if(validityOfCustomerData.equals("valid")) {

            if(customerDatabaseOperations.findByUsername(customerDTO.getUsername()).isPresent()){
                LOGGER.info("The username: "+customerDTO.getUsername()+" is already taken.");
                return "Username already taken.";
            }else{
                if(customerDatabaseOperations.findByEmail(customerDTO.getEmail()).isPresent()){
                    LOGGER.info("The email address: "+customerDTO.getEmail()+" is already taken.");
                    return "This email address is already used.";
                }
            }
            customerDatabaseOperations.save(accountsMapper.fromDTOtoCustomer(customerDTO));

       return "The account has been successfully created.";
        }
        else{
            return validityOfCustomerData;
        }
    }

    /**
     *
     * @param username
     * @param password
     * @return if registered in database, a CustomerDTO instance with given username and password.
     */
    public CustomerDTO getCustomerwithUserAndPass(String username,String password){
        Optional<Customer> customer=customerDatabaseOperations.findByUsername(username);
        if(customer.isPresent()){
            if(Objects.equals(PasswordManager.decrypt(customer.get().getPassword(), secretKey), password)){
                return accountsMapper.fromCustomertoDTO(customer.get());
            }
        }
        return null;
    }

    /**
     * @return  all the existing restaurants from the database.
     */
    public List<RestaurantDTO> getRestaurants(){
        Iterable<Restaurant> restaurants= restaurantDatabaseOperations.findAll();
        ArrayList<Restaurant>  restaurantArrayList=new ArrayList<>();
        restaurants.forEach(restaurantArrayList::add);
        return restaurantArrayList.stream().map(restaurantRelatedObjectsMapper::fromRestaurantToDTO).toList();
    }




}
