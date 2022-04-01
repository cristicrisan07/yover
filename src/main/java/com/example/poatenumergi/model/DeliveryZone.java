package com.example.poatenumergi.model;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="delivery_zones")
@Data
@NoArgsConstructor
public class DeliveryZone {
    @Id
    private Long id;
    @Column(unique=true,nullable = false)
    private String name;
    @ManyToMany(mappedBy = "deliveryZones")
    Set<Restaurant> restaurants;

}
