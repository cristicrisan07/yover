package com.example.poatenumergi.service;

import com.example.poatenumergi.model.Customer;
import com.example.poatenumergi.model.CustomerDTO;
import com.example.poatenumergi.repository.CustomerDatabaseOperations;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerOperationsService {

    private final CustomerDatabaseOperations customerDatabaseOperations;
    private final AccountsMapper accountsMapper;

    public String createCustomer(CustomerDTO customerDTO){
        String validityOfCustomerData=AccountsValidator.isCustomerAccountValid(customerDTO);
        if(validityOfCustomerData.equals("valid")) {

            if(customerDatabaseOperations.findByUsername(customerDTO.getUsername()).isPresent()){
                return "Username already taken.";
            }else{
                if(customerDatabaseOperations.findByEmail(customerDTO.getEmail()).isPresent()){
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
    public CustomerDTO getCustomerwithUserAndPass(String username,String password){
        Optional<Customer> customer=customerDatabaseOperations.findByUsername(username);
        if(customer.isPresent()){
            if(customer.get().getPassword().equals(password)){
                return accountsMapper.fromCustomertoDTO(customer.get());
            }
        }
        return null;
    }


}
