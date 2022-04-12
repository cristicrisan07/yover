package com.example.poatenumergi.controller;

import com.example.poatenumergi.model.*;
import com.example.poatenumergi.service.PasswordManager;
import com.example.poatenumergi.service.RAOperationsService;
import com.fasterxml.jackson.core.JsonEncoding;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequiredArgsConstructor
public class RAController {
    private final String secretKey = "JHKLXABYZC!!!!";
    private final RAOperationsService RAOperationsService;
    @PostMapping("/addRA")
    public ResponseEntity<String> insertRA(@RequestBody RestaurantAdministratorDTO restaurantAdministratorDTO) {
        RestaurantAdministratorDTO RAwithEncryptedpassword=new RestaurantAdministratorDTO(restaurantAdministratorDTO.getUsername(), restaurantAdministratorDTO.getEmail(), PasswordManager.encrypt(restaurantAdministratorDTO.getPassword(),secretKey));
        String status = RAOperationsService.createRA(RAwithEncryptedpassword);
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
        System.out.println(foodDTO.getName()+" "+foodDTO.getCategory() +" "+foodDTO.getRestaurantName());
        String status=RAOperationsService.addFood(foodDTO);
        HttpStatus httpStatus=HttpStatus.OK;
        if(!status.equals("Food added successfully.")){
            httpStatus=HttpStatus.NOT_ACCEPTABLE;
        }
        return  ResponseEntity.status(httpStatus).body(status);
    }
    @GetMapping("/getFood/{restaurantName}/{foodCategory}")
    public  ResponseEntity<String> getFoodFromResWithCategory(@PathVariable String restaurantName,@PathVariable String foodCategory){
        List<FoodDTO> results =RAOperationsService.getFoodWithKnownRestaurantAndCategory(restaurantName,foodCategory);
        HttpStatus httpStatus=HttpStatus.OK;
        String status="Good food ahead.";
        if(results.isEmpty()){
            httpStatus=HttpStatus.NOT_ACCEPTABLE;
            status="Unfortunately, at the moment we don't have any dish from this category available.";
        }
//        else {
//            results.stream().map(FoodDTO::getName).forEach(System.out::println);
//        }
        return  ResponseEntity.status(httpStatus).body(status);
    }
    @GetMapping("/getFood/{restaurantName}")
    public  ResponseEntity<List<FoodDTO>> getAllFoodFromRes(@PathVariable String restaurantName){
        List<FoodDTO> results =RAOperationsService.getFoodFromRestaurant(restaurantName);
        HttpStatus httpStatus=HttpStatus.OK;
        if(results.isEmpty()){
            httpStatus=HttpStatus.NOT_ACCEPTABLE;
             ArrayList<FoodDTO> notFound= new ArrayList<>();
             notFound.add(new FoodDTO("","",""));
             return  ResponseEntity.status(httpStatus).body(notFound);
        }

        return  ResponseEntity.status(httpStatus).body(results);
    }
    @GetMapping("/loginRA/{username}/{password}")
    public ResponseEntity<RestaurantAdministratorDTO> login(@PathVariable String username,@PathVariable String password){
            RestaurantAdministratorDTO RA=RAOperationsService.getRAwithUserAndPass(username,password);
        HttpStatus httpStatus=HttpStatus.OK;
        if(RA==null){
            httpStatus=HttpStatus.NOT_ACCEPTABLE;
            return ResponseEntity.status(httpStatus).body(new RestaurantAdministratorDTO("","",""));
        }
        return ResponseEntity.status(httpStatus).body(RA);
    }
    @GetMapping("/getRestaurantByAdminUsername/{username}")
    public  ResponseEntity<RestaurantDTO> getRestaurantByAdmin(@PathVariable String username){
        RestaurantDTO restaurantDTO=RAOperationsService.getRestaurantByAdminUsername(username);
        HttpStatus httpStatus=HttpStatus.OK;
        if(Objects.equals(restaurantDTO.getName(), "")){
            httpStatus=HttpStatus.NOT_ACCEPTABLE;
        }
        return  ResponseEntity.status(httpStatus).body(restaurantDTO);
    }
    @GetMapping("/getDeliveryLocations")
    public  ResponseEntity<List<String>> getFoodsWith(){
        List<String> deliveryZones=RAOperationsService.getDeliveryZones();
        HttpStatus httpStatus=HttpStatus.OK;
        if(deliveryZones.isEmpty()){
            httpStatus=HttpStatus.NOT_ACCEPTABLE;
        }
        return  ResponseEntity.status(httpStatus).body(deliveryZones);
    }
    @GetMapping("/getFoodCategories")
    public  ResponseEntity<List<String>> getFoodCategories(){
        List<String> foodCategories=RAOperationsService.getFoodCategories();
        HttpStatus httpStatus=HttpStatus.OK;
        if(foodCategories.isEmpty()){
            httpStatus=HttpStatus.NOT_ACCEPTABLE;
        }
        return  ResponseEntity.status(httpStatus).body(foodCategories);
    }


}
