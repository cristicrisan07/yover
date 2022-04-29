package com.example.poatenumergi.service;

import com.example.poatenumergi.model.CustomerDTO;
import com.example.poatenumergi.model.RestaurantAdministratorDTO;

public class AccountsValidator {
    /**
     * Helper function for isRAAccountValid that checks for emptiness and correct format of email address.
     * @param username
     * @param email
     * @param password
     * @return status message.
     */
    private static String areCredentialsValid(String username,String email,String password){

        if(username==null||username.equals("")){
            return "Username cannot be empty";
        }
        if(email==null||email.equals("")){
            return "Email cannot be empty";
        }
        if(!email.contains("@")||!email.contains(".")){
            return "Invalid email address.";
        }
        if(password==null||password.equals("")){
            return "Password cannot be empty";
        }
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
                return "First name field cannot be empty";
            }
            if(customer.getLastName()==null||customer.getLastName().equals("")){
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
