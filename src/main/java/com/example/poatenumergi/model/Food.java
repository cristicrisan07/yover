package com.example.poatenumergi.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Table(name="food")
@NoArgsConstructor
public class Food {

    @Id
    @GeneratedValue
    private  Long id;
    @Column
    private String name;
    @Column
    private FoodCategory category;
    @ManyToOne
    @JoinColumn(name="restaurant_id")
    private Restaurant restaurant;

    public Food(String name,FoodCategory category,Restaurant restaurant){
        this.name=name;
        this.category=category;
        this.restaurant=restaurant;
    }


}
