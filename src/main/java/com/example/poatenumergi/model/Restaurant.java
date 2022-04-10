package com.example.poatenumergi.model;



import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="restaurant")
@NoArgsConstructor
@Getter
public class Restaurant {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false,unique = true)
    private String name;
    @Column(nullable = false)
    private String location;
    @ManyToMany
    @JoinTable(name="restaurant_delivery_locations",
            joinColumns = @JoinColumn(name="restaurant_id"),
            inverseJoinColumns = @JoinColumn(name="location_id"))
    Set<DeliveryZone> deliveryZones;
    @OneToOne
    @JoinColumn(name = "administrator_username",referencedColumnName = "username")
    private RestaurantAdministrator restaurantAdministrator;


    public Restaurant(String name,String location,Set<DeliveryZone> deliveryZones,RestaurantAdministrator restaurantAdministrator){
        this.name=name;
        this.location=location;
        this.deliveryZones=deliveryZones;
        this.restaurantAdministrator=restaurantAdministrator;
    }
}
