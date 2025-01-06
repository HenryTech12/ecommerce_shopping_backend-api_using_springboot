package com.ecommerce.demo.dto;


import jakarta.validation.constraints.*;

public class UserDTO {

     @NotNull(message = "name cannot be null")
     @NotBlank(message = "name can't be blank")
     @Size(min = 8, message = "username length should be greater than or equal 8")
     private String name;
     @NotNull
     @NotBlank
     @Size(min = 6, message = "username length should be greater than or equal 6")
     private String username;
     @Email(regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$", message = "Invalid email format")
     private String email;
     @NotNull
     @NotBlank
     @Size(min = 8, message = "password length must be greater than or equal to 8")
     private String password;
     @NotNull
     @NotBlank
     private String address;

     public String getName() {
          return name;
     }

     public void setName(String name) {
          this.name = name;
     }

     public String getUsername() {
          return username;
     }

     public void setUsername(String username) {
          this.username = username;
     }

     public String getEmail() {
          return email;
     }

     public void setEmail(String email) {
          this.email = email;
     }

     public String getPassword() {
          return password;
     }

     public void setPassword(String password) {
          this.password = password;
     }

     public String getAddress() {
          return address;
     }

     public void setAddress(String address) {
          this.address = address;
     }
}
