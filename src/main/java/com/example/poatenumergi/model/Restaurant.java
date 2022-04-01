package com.example.poatenumergi.model;



import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="food")
@NoArgsConstructor
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

    public Restaurant(String name,String location,Set<DeliveryZone> deliveryZones){
        this.name=name;
        this.location=location;
        this.deliveryZones=deliveryZones;
    }
}
