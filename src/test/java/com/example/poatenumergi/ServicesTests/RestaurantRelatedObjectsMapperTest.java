package com.example.poatenumergi.ServicesTests;

import com.example.poatenumergi.model.DeliveryZone;
import com.example.poatenumergi.model.Restaurant;
import com.example.poatenumergi.model.RestaurantAdministrator;
import com.example.poatenumergi.model.RestaurantDTO;
import com.example.poatenumergi.repository.RestaurantDatabaseOperations;
import com.example.poatenumergi.service.RestaurantRelatedObjectsMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
public class RestaurantRelatedObjectsMapperTest {

    /*
    <dependency>
            <groupId>org.mockito</groupId>
            <artifactId>mockito-core</artifactId>
            <version>4.5.1</version>
        </dependency>
        */

    @Autowired
    private  RestaurantRelatedObjectsMapper restaurantRelatedObjectsMapper;

    @Test
    @Transactional
    public void validConversionOfRestaurantDTOtoRestaurant(){

        String[] deliveryZones=new String[]{"Andrei Muresanu",
                "Becas" ,
                "Borhanci" ,
                "Bulgaria" ,
                "Buna Ziua" ,
                "Centru" ,
                "Dambul Rotund" ,
                "Europa" ,
                "Faget" ,
                "Gheorgheni" ,
                "Grigorescu" ,
                "Gruia" ,
                "Intre Lacuri" ,
                "Iris" ,
                "Manastur" ,
                "Marasti" ,
                "Plopilor" ,
                "Someseni" ,
                "Sopor" ,
                "Zorilor"};

        for(String location:deliveryZones){
            HashSet<String> locations=new HashSet<>();
            locations.add(location);
            RestaurantDTO restaurantDTO=new RestaurantDTO("","",locations,"admin");
            assertNotNull(restaurantRelatedObjectsMapper.fromRestaurantDTO(restaurantDTO));
        }


    }
}
