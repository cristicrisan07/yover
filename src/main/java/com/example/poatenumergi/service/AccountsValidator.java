package com.example.poatenumergi.service;

import com.example.poatenumergi.model.CustomerDTO;
import com.example.poatenumergi.model.RestaurantAdministratorDTO;

public class AccountsValidator {

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
    public static String isRAAccountValid(RestaurantAdministratorDTO restaurantAdministratorDTO){
        return areCredentialsValid(restaurantAdministratorDTO.getUsername(),restaurantAdministratorDTO.getEmail(),restaurantAdministratorDTO.getPassword());
    }

}
