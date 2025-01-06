package com.ecommerce.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class OrderDTO {


       private String orderDate;
       private ProductDTO product;
       @NotBlank(message = "deliveryDate can't be blank")
       @NotNull(message = "deliveryDate can't be null")
       private String deliveryDate;
       @NotBlank(message = "deliveryAddress can't be blank")
       @NotNull(message = "deliveryAddress can't be null")
       private String deliveryAddress;
       private String orderTime;

       public String getOrderTime() {
              return orderTime;
       }

       public void setOrderTime(String orderTime) {
              this.orderTime = orderTime;
       }

       public String getOrderDate() {
              return orderDate;
       }

       public void setOrderDate(String orderDate) {
              this.orderDate = orderDate;
       }

       public ProductDTO getProduct() {
              return product;
       }

       public void setProduct(ProductDTO product) {
              this.product = product;
       }

       public String getDeliveryDate() {
              return deliveryDate;
       }

       public void setDeliveryDate(String deliveryDate) {
              this.deliveryDate = deliveryDate;
       }

       public String getDeliveryAddress() {
              return deliveryAddress;
       }

       public void setDeliveryAddress(String deliveryAddress) {
              this.deliveryAddress = deliveryAddress;
       }

}