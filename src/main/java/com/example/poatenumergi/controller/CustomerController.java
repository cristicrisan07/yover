package com.example.poatenumergi.controller;

import com.example.poatenumergi.model.CustomerDTO;
import com.example.poatenumergi.model.RestaurantAdministratorDTO;
import com.example.poatenumergi.service.CustomerOperationsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerOperationsService customerOperationsService;

    @PostMapping("/addCustomer")
    public ResponseEntity<String> insertCustomer(@RequestBody CustomerDTO customerDTO) {

        System.out.println(customerDTO.getUsername() + " " + customerDTO.getEmail() + " " + customerDTO.getPassword() + " " + customerDTO.getFirstName() + " " + customerDTO.getLastName());
        String status = customerOperationsService.createCustomer(customerDTO);
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
}