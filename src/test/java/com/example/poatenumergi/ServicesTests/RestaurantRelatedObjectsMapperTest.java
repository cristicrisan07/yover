package com.example.poatenumergi.ServicesTests;

import com.example.poatenumergi.model.*;
import com.example.poatenumergi.repository.RADatabaseOperations;
import com.example.poatenumergi.repository.RestaurantDatabaseOperations;
import com.example.poatenumergi.service.RestaurantRelatedObjectsMapper;
import lombok.RequiredArgsConstructor;
import org.junit.Before;
import org.junit.Test;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
//import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

//import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

//
// @SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class RestaurantRelatedObjectsMapperTest {
    private  RestaurantRelatedObjectsMapper restaurantRelatedObjectsMapper;
    private RestaurantDatabaseOperations restaurantDatabaseOperations;
    private RADatabaseOperations raDatabaseOperations;
    @Before
    public void setup(){
        restaurantDatabaseOperations=mock(RestaurantDatabaseOperations.class);
         raDatabaseOperations=mock(RADatabaseOperations.class);
            restaurantRelatedObjectsMapper=new RestaurantRelatedObjectsMapper(raDatabaseOperations,restaurantDatabaseOperations);
    }

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
    @Test
    @Transactional
    public void invalidConversionOfRestaurantDTOtoRestaurant(){

        String[] deliveryZones=new String[]{"Micro","Turda Noua","Poiana","Bai","Centru"};

        for(String location:deliveryZones){
            HashSet<String> locations=new HashSet<>();
            locations.add(location);
            RestaurantDTO restaurantDTO=new RestaurantDTO("","",locations,"admim");
            assertNotNull(restaurantRelatedObjectsMapper.fromRestaurantDTO(restaurantDTO));
        }


    }
    @Test
    @Transactional
    public void validConversionOfFoodDTOtoFood(){

        FoodDTO validRestaurantValidCategory=new FoodDTO("name","Lunch","Las Vericus");
        assertNotNull(restaurantRelatedObjectsMapper.fromFoodDTO(validRestaurantValidCategory));


    }
    @Test
    @Transactional
    public void invalidConversionOfFoodDTOtoFood(){
        FoodDTO validRestaurantInvalidCategory=new FoodDTO("name","invalid_category","Las Vericus");
        FoodDTO invalidRestaurantValidCategory=new FoodDTO("name","lunch","invalid_restaurant");
        FoodDTO invalidRestaurantInvalidCategory=new FoodDTO("name","invalid_category","invalid_restaurant");


        assertNotNull(restaurantRelatedObjectsMapper.fromFoodDTO(validRestaurantInvalidCategory));
        assertNotNull(restaurantRelatedObjectsMapper.fromFoodDTO(invalidRestaurantValidCategory));
        assertNotNull(restaurantRelatedObjectsMapper.fromFoodDTO(invalidRestaurantInvalidCategory));
    }
}
