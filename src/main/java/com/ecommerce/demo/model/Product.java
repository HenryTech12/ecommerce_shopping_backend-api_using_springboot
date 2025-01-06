package com.ecommerce.demo.model;
import jakarta.persistence.*;

@Entity
public class Product {
  
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
     private int id;
     private String productName;
     private double price;
     private String status;
     private String category;
     private String description;

     public String getDescription() {
      return description;
     }

     public void setDescription(String description) {
      this.description = description;
     }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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