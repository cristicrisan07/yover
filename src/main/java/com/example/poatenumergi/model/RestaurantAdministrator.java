package com.example.poatenumergi.model;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="restaurant_administrator")
@NoArgsConstructor
public class RestaurantAdministrator extends User implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    public RestaurantAdministrator(String username,String email,String password)
    {
        super(username,email,password);
    }
}
