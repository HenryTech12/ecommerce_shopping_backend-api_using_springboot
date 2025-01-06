
package com.ecommerce.demo.controller;
import com.ecommerce.demo.dto.PageRequestDTO;
import com.ecommerce.demo.service.UserService;
import com.ecommerce.demo.dto.UserDTO;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

     @RequestMapping("/addUser")
     public ResponseEntity<String> addUser(@RequestBody @Valid UserDTO userModel) {
             if(userModel != null)
                 return new ResponseEntity<>( userService.registerUser(userModel), HttpStatus.CREATED);
            else
                 return new ResponseEntity<>("user registration failed", HttpStatus.OK);
      }

    @RequestMapping("/updateUser/{oldEmail}")
     public ResponseEntity<String> updateUser(@PathVariable String oldEmail, @RequestBody @Valid UserDTO userModel) {
          if(userModel != null && oldEmail != null) {
              userService.update(oldEmail, userModel);
          }
        return new ResponseEntity<>("customer details updated successfully", HttpStatus.ACCEPTED);
    }

    @RequestMapping("/deleteByUsername/{username}")
     public void deleteUser(@PathVariable String username) {
          if(username != null) { 
              userService.removeUser(username);
          }
     }
    @GetMapping("/getAll")
    public ResponseEntity<Page<UserDTO>> getAllCustomers(PageRequestDTO pageRequestDTO) {
             Pageable pageable = new PageRequestDTO().getPageable(pageRequestDTO);
             return new ResponseEntity<>(userService.getUsers(pageable), HttpStatus.OK);
    }

    @GetMapping("/getCustomer/{username}")
   public ResponseEntity<UserDTO> getCustomer(@PathVariable String username) {
          return new ResponseEntity<>(userService.getUserWithUsername(username), HttpStatus.OK);
   }
}