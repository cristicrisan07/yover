package com.example.poatenumergi.controller;

import com.example.poatenumergi.model.FoodDTO;
import com.example.poatenumergi.model.Restaurant;
import com.example.poatenumergi.model.RestaurantAdministratorDTO;
import com.example.poatenumergi.model.RestaurantDTO;
import com.example.poatenumergi.service.RAOperationsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RAController {
    private final RAOperationsService RAOperationsService;
    @PostMapping("/addRA")
    public ResponseEntity<String> insertRA(@RequestBody RestaurantAdministratorDTO RestaurantAdministratorDTO) {
        String status = RAOperationsService.createRA(RestaurantAdministratorDTO);
        HttpStatus httpStatus = HttpStatus.OK;
        if (!status.equals("The account has been successfully created.")) {
           httpStatus= HttpStatus.NOT_ACCEPTABLE;
        }
            return ResponseEntity.status(httpStatus).body(status);
        }
    @PostMapping("/addRestaurant")
    public  ResponseEntity<String> insertRestaurant(@RequestBody RestaurantDTO restaurantDTO){
        String status=RAOperationsService.addRestaurant(restaurantDTO);
        HttpStatus httpStatus=HttpStatus.OK;
        if(!status.equals("You have successfully added the restaurant.")){
            httpStatus=HttpStatus.NOT_ACCEPTABLE;
        }
        return  ResponseEntity.status(httpStatus).body(status);
    }
    @PostMapping("/addFood")
    public  ResponseEntity<String> insertFood(@RequestBody FoodDTO foodDTO){
        String status=RAOperationsService.addFood(foodDTO);
        HttpStatus httpStatus=HttpStatus.OK;
        if(!status.equals("Food added successfully.")){
            httpStatus=HttpStatus.NOT_ACCEPTABLE;
        }
        return  ResponseEntity.status(httpStatus).body(status);
    }

}
