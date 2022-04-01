package com.example.poatenumergi.service;


import com.example.poatenumergi.model.*;
import com.example.poatenumergi.repository.FoodDatabaseOperations;
import com.example.poatenumergi.repository.RADatabaseOperations;
import com.example.poatenumergi.repository.RestaurantDatabaseOperations;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RAOperationsService {

    private final RADatabaseOperations RADatabaseOperations;
    private final RestaurantDatabaseOperations restaurantDatabaseOperations;
    private final AccountsMapper accountsMapper;
    private final RestaurantRelatedObjectsMapper restaurantRelatedObjectsMapper;
    private final FoodDatabaseOperations foodDatabaseOperations;
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
            if(RADatabaseOperations.findRestaurantByname(restaurantDTO.getName()).isPresent()){
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

}
