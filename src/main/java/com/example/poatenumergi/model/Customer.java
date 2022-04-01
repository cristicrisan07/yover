package com.example.poatenumergi.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="customer")
public class Customer extends User{

    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable=false)
    private String lastName;
    public Customer(String username,String email,String password,String firstName,String lastName){
        super(username, email, password);
        this.firstName=firstName;
        this.lastName=lastName;
    }
    public Customer() {

    }
}
