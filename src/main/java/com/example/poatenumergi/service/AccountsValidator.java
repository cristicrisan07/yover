package com.example.poatenumergi.service;

import com.example.poatenumergi.controller.RAController;
import com.example.poatenumergi.model.CustomerDTO;
import com.example.poatenumergi.model.RestaurantAdministratorDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AccountsValidator {
    private static Logger LOGGER = LoggerFactory.getLogger(AccountsValidator.class);
    /**
     * Helper function for isRAAccountValid that checks for emptiness and correct format of email address.
     * @param username
     * @param email
     * @param password
     * @return status message.
     */
    private static String areCredentialsValid(String username,String email,String password){

        LOGGER.info("Account with username: "+username +" and email address: "+email+" is verified...");
        if(username==null||username.equals("")){
            LOGGER.info("Username cannot be empty");
            return "Username cannot be empty";
        }


        if(email==null||email.equals("")){
            LOGGER.info("Email address cannot be empty");
            return "Email cannot be empty";
        }
        if(!email.contains("@")||!email.contains(".")){
            LOGGER.info("Email address does not contain @ or .");
            return "Invalid email address.";
        }
        if(password==null||password.equals("")){
            LOGGER.info("Password is empty.");
            return "Password cannot be empty";
        }
        LOGGER.info("Account with username: "+username +" and email address: "+email+" is valid.");
        return "valid";
    }

    /**
     *
     * @param customer validating customer account data received from client side.
     * @return status message.
     */
    public static String isCustomerAccountValid(CustomerDTO customer){

        String credentialsValidity=areCredentialsValid(customer.getUsername(),customer.getEmail(),customer.getPassword());
        if(!credentialsValidity.equals("valid")){
            return credentialsValidity;
        }
        else{
            if(customer.getFirstName()==null||customer.getFirstName().equals("")){
                LOGGER.info("First name is empty");
                return "First name field cannot be empty";
            }
            if(customer.getLastName()==null||customer.getLastName().equals("")){
                LOGGER.info("Last name is empty");
                return "Last name field cannot be empty";
            }
        }

        return "valid";
    }

    /**
     *
     * @param restaurantAdministratorDTO validating restaurant administrator account data received from client side.
     * @return status message.
     */
    public static String isRAAccountValid(RestaurantAdministratorDTO restaurantAdministratorDTO){
        return areCredentialsValid(restaurantAdministratorDTO.getUsername(),restaurantAdministratorDTO.getEmail(),restaurantAdministratorDTO.getPassword());
    }

}
