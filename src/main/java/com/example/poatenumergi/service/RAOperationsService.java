package com.example.poatenumergi.service;


import com.example.poatenumergi.model.*;
import com.example.poatenumergi.repository.CustomerDatabaseOperations;
import com.example.poatenumergi.repository.FoodDatabaseOperations;
import com.example.poatenumergi.repository.RADatabaseOperations;
import com.example.poatenumergi.repository.RestaurantDatabaseOperations;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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
    private final CustomerDatabaseOperations customerDatabaseOperations;
    private final String secretKey = "JHKLXABYZC!!!!";

    @Autowired
    private JavaMailSender emailSender;

    /**
     *
     * @param to receiver's email address.
     * @param subject of the email.
     * @param text representing the content of the email.
     */
    public void sendSimpleMessage(
            String to, String subject, String text) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@yoverapp.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);

    }

    /**
     * This method adds a new instance of Restaurant Administrator in database.
     * @param restaurantAdministratorDTO object containing information received from the client side.
     * @return status message.
     */
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

    /**
     * This method adds a new Restaurant instance in database.
     * @param restaurantDTO object containing information received from the client side.
     * @return status message.
     */
    public String addRestaurant(RestaurantDTO restaurantDTO){
            if(restaurantDatabaseOperations.findByName(restaurantDTO.getName()).isPresent()){
                return "This restaurant name already exists.";
            }
            else{
                restaurantDatabaseOperations.save(restaurantRelatedObjectsMapper.fromRestaurantDTO(restaurantDTO));
                return "You have successfully added the restaurant.";
            }
    }

    /**
     * This method adds a new Food instance in database.
     * @param foodDTO object containing information received from the client side.
     * @return  status message.
     */
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

    /**
     * This method retrieves the dishes of given category from a particular restaurant.
     * @param restaurantName the name of the restaurant to be queried.
     * @param foodCategory the category to select the dishes from.
     * @return a list of the filtered dishes.
     */
    public List<FoodDTO> getFoodWithKnownRestaurantAndCategory(String restaurantName, String foodCategory){
        Optional<Restaurant> restaurant=restaurantDatabaseOperations.findByName(restaurantName);
        if(restaurant.isPresent()) {
            Optional<List<Food>> foodList=foodDatabaseOperations.findAllByRestaurantAndCategory(restaurant.get(),FoodCategory.valueOf(foodCategory));
            return foodList.map(foods -> foods.stream().map(restaurantRelatedObjectsMapper::toFoodDTO).toList()).orElse(null);
        }
        else return null;
    }

    /**
     * This method retrieves the dishes from a particular restaurant.
     * @param restaurantName  the name of the restaurant to be queried.
     * @return a list of the filtered dishes.
     */
    public List<FoodDTO> getFoodFromRestaurant(String restaurantName){
        Optional<Restaurant> restaurant=restaurantDatabaseOperations.findByName(restaurantName);
        if(restaurant.isPresent()) {
            Optional<List<Food>> foodList=foodDatabaseOperations.findAllByRestaurant(restaurant.get());
            return foodList.map(foods -> foods.stream().map(restaurantRelatedObjectsMapper::toFoodDTO).toList()).orElse(null);
        }
        else return null;
    }

    /**
     * Login process of the Restaurant Administrator.
     * @param username
     * @param password
     * @return account data if the login process is successful.
     */
    public RestaurantAdministratorDTO getRAwithUserAndPass(String username,String password){
        Optional<RestaurantAdministrator> RA=RADatabaseOperations.findByUsername(username);
        if(RA.isPresent()){
            if(Objects.equals(PasswordManager.decrypt(RA.get().getPassword(), secretKey), password)){
                return accountsMapper.fromRAtoDTO(RA.get());
            }
        }
        return null;
    }

    /**
     * This method retrieves information about the restaurant of the logged in administrator.
     * @param username
     * @return restaurnt data as a RestaurantDTO instance.
     */
    public RestaurantDTO getRestaurantByAdminUsername(String username){
        Optional<Restaurant> restaurant=restaurantDatabaseOperations.findByRestaurantAdministratorUsername(username);
        if(restaurant.isPresent()){
            return  restaurantRelatedObjectsMapper.fromRestaurantToDTO(restaurant.get());
        }
        else{
            return new RestaurantDTO("","",new HashSet<>(),"");
        }
    }

    /**
     *
     * @return a list of all the delivery zones that are available.
     */
    public List<String> getDeliveryZones(){
        Optional<List<DeliveryZone>> deliveryZones=RADatabaseOperations.findAllDeliveryZones();
        return deliveryZones.map(zones -> zones.stream().map(DeliveryZone::getName).collect(Collectors.toList())).orElse(null);
    }

    /**
     *
     * @return a list of the existent food categories.
     */
    public List<String> getFoodCategories(){
      return Arrays.stream(FoodCategory.values()).map(FoodCategory::getCode).toList();

    }

    /**
     *
     * @return a list of all customers of the application.
     */
    public List<CustomerDTO> getAllCustomers(){
        Iterable<Customer> customers= customerDatabaseOperations.findAll();
        ArrayList<Customer>  customerArrayList=new ArrayList<>();
        customers.forEach(customerArrayList::add);
        return customerArrayList.stream().map(accountsMapper::fromCustomertoDTO).toList();
    }

}
