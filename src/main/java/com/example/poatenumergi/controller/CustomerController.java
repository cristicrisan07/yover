package com.example.poatenumergi.controller;

import com.example.poatenumergi.model.CustomerDTO;
import com.example.poatenumergi.service.CustomerOperationsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerOperationsService customerOperationsService;
    @PostMapping("/addCustomer")
    public ResponseEntity<String> insertCustomer(@RequestBody CustomerDTO customerDTO){

        String status=customerOperationsService.createCustomer(customerDTO);
        HttpStatus httpStatus = HttpStatus.OK;
        if (!status.equals("The account has been successfully created.")) {
            httpStatus= HttpStatus.NOT_ACCEPTABLE;
        }
        return ResponseEntity.status(httpStatus).body(status);
      }

}