
package com.ecommerce.demo.service;

import com.ecommerce.demo.dto.UserDTO;
import com.ecommerce.demo.mail.CustomMailSender;
import com.ecommerce.demo.mapper.UserMapper;
import com.ecommerce.demo.model.UserData;
import com.ecommerce.demo.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

      @Autowired
     private UserRepo repo;
      @Autowired
     private UserMapper mapper;
      @Autowired
     private PasswordEncoder passwordEncoder;
      @Autowired
      private CustomMailSender customMailSender;
     public String registerUser(UserDTO userModel) {
         if(!exists(userModel)) {
             UserData userData = mapper.convertUserDTOToUserData(userModel);
             userData.setPassword(passwordEncoder.encode(userData.getPassword()));
             repo.save(userData);
             System.out.println("user registered successfully");
             customMailSender.sendRegistrationWelcomeMail(userModel.getEmail());
             return "user registered successfully";
         }
         return "username already exists";
      }
      public boolean exists(UserDTO userDTO) {
         return repo.findByUsername(userDTO.getUsername()).isPresent();
      }
    public void update(String oldEmail, UserDTO userModel) {
            Optional<UserData> optionaluserData = repo.findByEmail(oldEmail);
            if(optionaluserData.isPresent()) {
                UserData userData = optionaluserData.orElseThrow(() -> {
                    throw new NullPointerException(" user with email: "+ oldEmail+" does not exist");
                });
               UserData NewuserData = mapper.convertUserDTOToUserData(userModel);
               NewuserData.setPassword(passwordEncoder.encode(NewuserData.getPassword()));
               NewuserData.setId(userData.getId());
               repo.save(NewuserData);
          }
         System.out.println("update user data successfully");
    }
   public void removeUser(String username) {
       Optional<UserData> optional_userData = repo.findByUsername(username);
       if(optional_userData.isPresent()) {
           UserData userData = optional_userData.orElseThrow(() -> {
               throw new NullPointerException("user does not exist");
           });
          repo.deleteById(userData.getId());
          System.out.println("user data removed successfully");
      }
   }

  public Page<UserDTO> getUsers(Pageable pageable) {
          Page<UserData> list_of_user = repo.findAll(pageable);
          List<UserDTO> list_of_user_dto = new ArrayList<>();
          for(UserData user_data : list_of_user) {
              UserDTO userModel = mapper.convertUserDataToUserDTO(user_data);
              list_of_user_dto.add(userModel);
         }
          Page<UserDTO> userDTOPage = new PageImpl<UserDTO>(
                  list_of_user_dto
          );
          return userDTOPage;
    }
  public UserDTO getUserWithUsername(String username) {
         Optional<UserData> user_data = repo.findByUsername(username);
         UserData myuser_data = null;
         if(user_data.isPresent()) {
               myuser_data = user_data.orElseThrow(() -> {
                         throw new NullPointerException("user not found");
                });
         }
      return mapper.convertUserDataToUserDTO(myuser_data);
   }
}