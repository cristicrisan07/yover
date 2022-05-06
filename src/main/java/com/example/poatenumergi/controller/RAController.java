package com.example.poatenumergi.controller;

import com.example.poatenumergi.model.*;
import com.example.poatenumergi.service.PDFCreator;
import com.example.poatenumergi.service.PasswordManager;
import com.example.poatenumergi.service.RAOperationsService;
import com.fasterxml.jackson.core.JsonEncoding;
import jdk.jfr.ContentType;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@RestController
@RequiredArgsConstructor
public class RAController {
    private final String secretKey = "JHKLXABYZC!!!!";
    private final RAOperationsService RAOperationsService;

    private static Logger LOGGER = LoggerFactory.getLogger(RAController.class);
    @PostMapping("/addRA")
    public ResponseEntity<String> insertRA(@RequestBody RestaurantAdministratorDTO restaurantAdministratorDTO) {
        RestaurantAdministratorDTO RAwithEncryptedpassword=new RestaurantAdministratorDTO(restaurantAdministratorDTO.getUsername(), restaurantAdministratorDTO.getEmail(), PasswordManager.encrypt(restaurantAdministratorDTO.getPassword(),secretKey));
        String status = RAOperationsService.createRA(RAwithEncryptedpassword);
        HttpStatus httpStatus = HttpStatus.OK;
        if (!status.equals("The account has been successfully created.")) {
           httpStatus= HttpStatus.NOT_ACCEPTABLE;

        LOGGER.info("The username:"+restaurantAdministratorDTO.getUsername()+" or the email:"+restaurantAdministratorDTO.getEmail()+" is already taken.");
        }else {
        LOGGER.info("The restaurant administrator account with the username: " + restaurantAdministratorDTO.getUsername() + " and email: " + restaurantAdministratorDTO.getEmail() + " was successfully created.");
        }
            return ResponseEntity.status(httpStatus).body(status);
        }
    @PostMapping("/addRestaurant")
    public  ResponseEntity<String> insertRestaurant(@RequestBody RestaurantDTO restaurantDTO){
        String status=RAOperationsService.addRestaurant(restaurantDTO);
        HttpStatus httpStatus=HttpStatus.OK;
        if(!status.equals("You have successfully added the restaurant.")){
            httpStatus=HttpStatus.NOT_ACCEPTABLE;
            LOGGER.info(restaurantDTO.getRestaurantAdministratorUsername()+" failed to add the restaurant: "+restaurantDTO.getName()+" because a restaurant with this name already exists.");
        }
        else{
            LOGGER.info(restaurantDTO.getRestaurantAdministratorUsername()+" has successfully added the restaurant named: "+restaurantDTO.getName());
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
            LOGGER.info("An internal error occurred in RAOperationsService.");
        }
        else{
            LOGGER.info("The dish with the name: "+ foodDTO.getName()+" has been successfully added in the menu of: "+foodDTO.getRestaurantName());
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
            LOGGER.info("There are no dishes from the category: "+foodCategory+ " available at the restaurant: " +restaurantName);
        }
        else{
            LOGGER.info("The dishes from the category: "+foodCategory+" of: "+restaurantName+" were successfully dispatched to client side.");
        }
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
             LOGGER.info("There are no dishes available at: "+restaurantName);
             return  ResponseEntity.status(httpStatus).body(notFound);
        }
        else{
            LOGGER.info("All the dishes of: "+restaurantName+" were successfully dispatched to client side.");
        }

        return  ResponseEntity.status(httpStatus).body(results);
    }
    @GetMapping("/loginRA/{username}/{password}")
    public ResponseEntity<RestaurantAdministratorDTO> login(@PathVariable String username,@PathVariable String password){
            RestaurantAdministratorDTO RA=RAOperationsService.getRAwithUserAndPass(username,password);
        HttpStatus httpStatus=HttpStatus.OK;
        if(RA==null){
            httpStatus=HttpStatus.NOT_ACCEPTABLE;
            LOGGER.info("The username password combination of supposed restaurant administrator: "+username+" is invalid.");
            return ResponseEntity.status(httpStatus).body(new RestaurantAdministratorDTO("","",""));
        }
        else{
            LOGGER.info("The restaurant administrator with the username: "+ username +" has successfully logged in.");
        }
        return ResponseEntity.status(httpStatus).body(RA);
    }
    @GetMapping("/getRestaurantByAdminUsername/{username}")
    public  ResponseEntity<RestaurantDTO> getRestaurantByAdmin(@PathVariable String username){
        RestaurantDTO restaurantDTO=RAOperationsService.getRestaurantByAdminUsername(username);
        HttpStatus httpStatus=HttpStatus.OK;
        if(Objects.equals(restaurantDTO.getName(), "")){
            httpStatus=HttpStatus.NOT_ACCEPTABLE;
            LOGGER.info("Restaurant administrator: "+username+" doesn't have a restaurant.");
        }
        else{
            LOGGER.info("The restaurant: "+restaurantDTO.getName()+" added by: "+username+" has been successfully dispatched to client side.");
        }
        return  ResponseEntity.status(httpStatus).body(restaurantDTO);
    }
    @GetMapping("/getDeliveryLocations")
    public  ResponseEntity<List<String>> getDeliveryLocations(){
        List<String> deliveryZones=RAOperationsService.getDeliveryZones();
        HttpStatus httpStatus=HttpStatus.OK;
        if(deliveryZones.isEmpty()){
            httpStatus=HttpStatus.NOT_ACCEPTABLE;
            LOGGER.info("There are no delivery zones available at the moment.");
        }
        else{
            LOGGER.info("The information about the delivery zones were dispatched to the client side.");
        }
        return  ResponseEntity.status(httpStatus).body(deliveryZones);
    }
    @GetMapping("/getFoodCategories")
    public  ResponseEntity<List<String>> getFoodCategories(){
        List<String> foodCategories=RAOperationsService.getFoodCategories();
        HttpStatus httpStatus=HttpStatus.OK;
        if(foodCategories.isEmpty()){
            httpStatus=HttpStatus.NOT_ACCEPTABLE;
            LOGGER.info("There was an error in converting the food categories.");
        }
        else{
            LOGGER.info("The information about the food categories were dispatched to the client side.");
        }
        return  ResponseEntity.status(httpStatus).body(foodCategories);
    }
    @GetMapping("/createPDFMenuFor/{restaurant}")
    public  ResponseEntity<?> createPDFMenuFor(@PathVariable String restaurant) throws IOException {
        List<FoodDTO> results =RAOperationsService.getFoodFromRestaurant(restaurant);
        String stat=PDFCreator.menuToPDF(restaurant + "_menu",results);
        HttpStatus httpStatus=HttpStatus.OK;
        if(!stat.equals("Success")){
            httpStatus=HttpStatus.INTERNAL_SERVER_ERROR;
            LOGGER.info("Could not retrieve the dishes of:"+restaurant);
            return ResponseEntity.status(httpStatus).body(stat);
        }

        File fileToSend=new File(restaurant+"_menu.pdf");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type","application/octet-stream");
        headers.add("Content-Disposition","attachment; filename=\""+fileToSend.getName()+"\"");
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        Path path = Paths.get(fileToSend.getAbsolutePath());
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

        LOGGER.info("The PDF version of the menu was successfully generated and sent to the client-side.");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(fileToSend.length())
             //   .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
    @GetMapping("/sendAdvertisingEmails/{restaurantName}")
    public  ResponseEntity<String> sendEmails(@PathVariable String restaurantName){
        List<CustomerDTO> customerDTOS= RAOperationsService.getAllCustomers();
        HttpStatus httpStatus=HttpStatus.OK;
        if(customerDTOS.isEmpty()){
            httpStatus=HttpStatus.INTERNAL_SERVER_ERROR;
            LOGGER.info("There is no customer to send the advertising email to");
            return  ResponseEntity.status(httpStatus).body("Could not fetch the customers.");
        }
        String subject="The weekend brings lots of price drops on a whole selection of dishes from "+restaurantName+
                ".\nMake sure you check the discounts in the app and have something tasty for you today.\nStay safe. Let yover make your day better.";
        for (CustomerDTO customerDTO:customerDTOS) {
            RAOperationsService.sendSimpleMessage(customerDTO.getEmail(),"Cristian from yover.",subject);
        }
        LOGGER.info("The advertising emails for: "+restaurantName+" were successfully sent.");
        return  ResponseEntity.status(httpStatus).body("The emails have been sent.");
    }



}
