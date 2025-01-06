package com.ecommerce.demo.mapper;


import com.ecommerce.demo.dto.UserDTO;
import com.ecommerce.demo.model.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

@Service
public class UserMapper {
 
      @Autowired
      private ModelMapper modelMapper;
     public UserData convertUserDTOToUserData(UserDTO userModel) {
          if(userModel != null) 
             return modelMapper.map(userModel, UserData.class);
          else
              return new UserData();
     }

    public UserDTO convertUserDataToUserDTO(UserData userData) {
        if(userData != null) 
           return modelMapper.map(userData, UserDTO.class);
        else
            return new UserDTO();
     }
}