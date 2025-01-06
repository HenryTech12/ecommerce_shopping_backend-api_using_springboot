package com.ecommerce.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ProductDTO {

     @NotNull(message = "product name can't be null")
     @NotBlank(message = "product name can't be blank")
     private String productName;
     private double price;
     private String status;
     @NotNull(message = "category can't be null")
     @NotBlank(message = "category can't be blank")
     private String category;
     @NotNull(message = "description can't be null")
     @NotBlank(message = "description can't be blank")
     private String description;

     public String getDescription() {
          return description;
     }

     public void setDescription(String description) {
          this.description = description;
     }

     public String getProductName() {
          return productName;
     }

     public void setProductName(String productName) {
          this.productName = productName;
     }

     public double getPrice() {
          return price;
     }

     public void setPrice(double price) {
          this.price = price;
     }

     public String getStatus() {
          return status;
     }

     public void setStatus(String status) {
          this.status = status;
     }

     public String getCategory() {
          return category;
     }

     public void setCategory(String category) {
          this.category = category;
     }
}