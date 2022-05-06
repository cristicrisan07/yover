package com.example.poatenumergi.controller;

import com.example.poatenumergi.model.CustomerDTO;
import com.example.poatenumergi.model.RestaurantDTO;
import com.example.poatenumergi.service.CustomerOperationsService;
import com.example.poatenumergi.service.PasswordManager;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerOperationsService customerOperationsService;
    private final String secretKey = "JHKLXABYZC!!!!";

    private static  Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

    @PostMapping("/addCustomer")
    public ResponseEntity<String> insertCustomer(@RequestBody CustomerDTO customerDTO) {
        CustomerDTO encryptedPasswordCustomer=new CustomerDTO(customerDTO.getUsername(),customerDTO.getEmail(), PasswordManager.encrypt(customerDTO.getPassword(),secretKey),customerDTO.getFirstName(), customerDTO.getLastName());
        String status = customerOperationsService.createCustomer(encryptedPasswordCustomer);
        HttpStatus httpStatus = HttpStatus.OK;
        if (!status.equals("The account has been successfully created.")) {
            httpStatus = HttpStatus.NOT_ACCEPTABLE;
            LOGGER.info("The username:"+customerDTO.getUsername()+" or the email:"+customerDTO.getEmail()+" is already taken.");
        }else {
            LOGGER.info("The account with the username: " + customerDTO.getUsername() + " and email: " + customerDTO.getEmail() + " was successfully created.");
        }
        return ResponseEntity.status(httpStatus).body(status);
    }

    @GetMapping("/loginCustomer/{username}/{password}")
    public ResponseEntity<CustomerDTO> login(@PathVariable String username, @PathVariable String password) {
        CustomerDTO customerDTO = customerOperationsService.getCustomerwithUserAndPass(username, password);
        HttpStatus httpStatus = HttpStatus.OK;
        if (customerDTO == null) {
            httpStatus = HttpStatus.NOT_ACCEPTABLE;
            LOGGER.info("The username password combination of supposed user: "+username+" is invalid.");
            return ResponseEntity.status(httpStatus).body(new CustomerDTO("","","","",""));
        }
        else{
            LOGGER.info("The user with the username: "+ username +" has successfully logged in.");
        }
        return ResponseEntity.status(httpStatus).body(customerDTO);
    }
    @GetMapping("/getRestaurants")
    public  ResponseEntity<List<RestaurantDTO>> getAllRestaurants(){
        List<RestaurantDTO> restaurants=customerOperationsService.getRestaurants();
        HttpStatus httpStatus=HttpStatus.OK;
        if(restaurants.isEmpty()){
            LOGGER.info("No restaurants have been added to database yet.");
            httpStatus=HttpStatus.NOT_ACCEPTABLE;
        }
        else{
            LOGGER.info("All restaurants' information has successfully been dispatched to the client side.");
        }
        return  ResponseEntity.status(httpStatus).body(restaurants);
    }
}