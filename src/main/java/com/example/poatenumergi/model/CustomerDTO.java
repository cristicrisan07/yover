package com.example.poatenumergi.model;


import lombok.Data;

@Data
public class CustomerDTO {

    private final String username;
    private final String email;
    private final String password;
    private final String firstName;
    private final String lastName;

}