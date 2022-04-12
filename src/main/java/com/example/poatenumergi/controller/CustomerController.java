package com.example.poatenumergi.controller;

import com.example.poatenumergi.model.CustomerDTO;
import com.example.poatenumergi.model.RestaurantDTO;
import com.example.poatenumergi.service.CustomerOperationsService;
import com.example.poatenumergi.service.PasswordManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerOperationsService customerOperationsService;
    private final String secretKey = "JHKLXABYZC!!!!";
    @PostMapping("/addCustomer")
    public ResponseEntity<String> insertCustomer(@RequestBody CustomerDTO customerDTO) {
        CustomerDTO encryptedPasswordCustomer=new CustomerDTO(customerDTO.getUsername(),customerDTO.getEmail(), PasswordManager.encrypt(customerDTO.getPassword(),secretKey),customerDTO.getFirstName(), customerDTO.getLastName());
        String status = customerOperationsService.createCustomer(encryptedPasswordCustomer);
        HttpStatus httpStatus = HttpStatus.OK;
        if (!status.equals("The account has been successfully created.")) {
            httpStatus = HttpStatus.NOT_ACCEPTABLE;
        }
        return ResponseEntity.status(httpStatus).body(status);
    }

    @GetMapping("/loginCustomer/{username}/{password}")
    public ResponseEntity<CustomerDTO> login(@PathVariable String username, @PathVariable String password) {
        CustomerDTO customerDTO = customerOperationsService.getCustomerwithUserAndPass(username, password);
        HttpStatus httpStatus = HttpStatus.OK;
        if (customerDTO == null) {
            httpStatus = HttpStatus.NOT_ACCEPTABLE;
            return ResponseEntity.status(httpStatus).body(new CustomerDTO("","","","",""));
        }
        return ResponseEntity.status(httpStatus).body(customerDTO);
    }
    @GetMapping("/getRestaurants")
    public  ResponseEntity<List<RestaurantDTO>> getAllRestaurants(){
        List<RestaurantDTO> restaurants=customerOperationsService.getRestaurants();
        HttpStatus httpStatus=HttpStatus.OK;
        if(restaurants.isEmpty()){
            httpStatus=HttpStatus.NOT_ACCEPTABLE;
        }
        return  ResponseEntity.status(httpStatus).body(restaurants);
    }
}