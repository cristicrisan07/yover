package com.example.poatenumergi.model;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="restaurant_administrator")
@NoArgsConstructor
public class RestaurantAdministrator extends User{
    @Id
    @GeneratedValue
    private Long id;
    public RestaurantAdministrator(String username,String email,String password)
    {
        super(username,email,password);
    }
}
