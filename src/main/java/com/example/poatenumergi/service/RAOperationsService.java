package com.example.poatenumergi.service;


import com.example.poatenumergi.model.*;
import com.example.poatenumergi.repository.FoodDatabaseOperations;
import com.example.poatenumergi.repository.RADatabaseOperations;
import com.example.poatenumergi.repository.RestaurantDatabaseOperations;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RAOperationsService {

    private final RADatabaseOperations RADatabaseOperations;
    private final RestaurantDatabaseOperations restaurantDatabaseOperations;
    private final AccountsMapper accountsMapper;
    private final RestaurantRelatedObjectsMapper restaurantRelatedObjectsMapper;
    private final FoodDatabaseOperations foodDatabaseOperations;
    private final String secretKey = "JHKLXABYZC!!!!";

    public String createRA(RestaurantAdministratorDTO restaurantAdministratorDTO){

        String validityOfRAdata=AccountsValidator.isRAAccountValid(restaurantAdministratorDTO);
        if(validityOfRAdata.equals("valid")) {
            if(RADatabaseOperations.findByUsername(restaurantAdministratorDTO.getUsername()).isPresent()){
                return "Username already taken.";
            }
            else{
                if(RADatabaseOperations.findByEmail(restaurantAdministratorDTO.getEmail()).isPresent()){
                    return "This email address is already used.";
                }
            }
            RADatabaseOperations.save(accountsMapper.fromDTOtoRA(restaurantAdministratorDTO));
            return "The account has been successfully created.";
        }
        else{
            return validityOfRAdata;
        }
    }
    public String addRestaurant(RestaurantDTO restaurantDTO){
            if(restaurantDatabaseOperations.findByName(restaurantDTO.getName()).isPresent()){
                return "This restaurant name already exists.";
            }
            else{
                restaurantDatabaseOperations.save(restaurantRelatedObjectsMapper.fromRestaurantDTO(restaurantDTO));
                return "You have successfully added the restaurant.";
            }
    }
    public String addFood(FoodDTO foodDTO){
          Food food=restaurantRelatedObjectsMapper.fromFoodDTO(foodDTO);
           if(food!=null){
               foodDatabaseOperations.save(food);
               return "Food added successfully.";
           }
           else{
               return "Internal server error. Contact support!";
           }
    }
    public List<FoodDTO> getFoodWithKnownRestaurantAndCategory(String restaurantName, String foodCategory){
        Optional<Restaurant> restaurant=restaurantDatabaseOperations.findByName(restaurantName);
        if(restaurant.isPresent()) {
            Optional<List<Food>> foodList=foodDatabaseOperations.findAllByRestaurantAndCategory(restaurant.get(),FoodCategory.valueOf(foodCategory));
            return foodList.map(foods -> foods.stream().map(restaurantRelatedObjectsMapper::toFoodDTO).toList()).orElse(null);
        }
        else return null;
    }
    public List<FoodDTO> getFoodFromRestaurant(String restaurantName){
        Optional<Restaurant> restaurant=restaurantDatabaseOperations.findByName(restaurantName);
        if(restaurant.isPresent()) {
            Optional<List<Food>> foodList=foodDatabaseOperations.findAllByRestaurant(restaurant.get());
            return foodList.map(foods -> foods.stream().map(restaurantRelatedObjectsMapper::toFoodDTO).toList()).orElse(null);
        }
        else return null;
    }
    public RestaurantAdministratorDTO getRAwithUserAndPass(String username,String password){
        Optional<RestaurantAdministrator> RA=RADatabaseOperations.findByUsername(username);
        if(RA.isPresent()){
            if(Objects.equals(PasswordManager.decrypt(RA.get().getPassword(), secretKey), password)){
                return accountsMapper.fromRAtoDTO(RA.get());
            }
        }
        return null;
    }
    public RestaurantDTO getRestaurantByAdminUsername(String username){
        Optional<Restaurant> restaurant=restaurantDatabaseOperations.findByRestaurantAdministratorUsername(username);
        if(restaurant.isPresent()){
            return  restaurantRelatedObjectsMapper.fromRestaurantToDTO(restaurant.get());
        }
        else{
            return new RestaurantDTO("","",new HashSet<>(),"");
        }
    }
    public List<String> getDeliveryZones(){
        Optional<List<DeliveryZone>> deliveryZones=RADatabaseOperations.findAllDeliveryZones();
        return deliveryZones.map(zones -> zones.stream().map(DeliveryZone::getName).collect(Collectors.toList())).orElse(null);
    }
    public List<String> getFoodCategories(){
      return Arrays.stream(FoodCategory.values()).map(FoodCategory::getCode).toList();

    }

}
