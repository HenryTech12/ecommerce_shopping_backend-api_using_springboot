package com.ecommerce.demo.model;
import jakarta.persistence.*;

@Entity
public class UserOrder {
       @Id
       @GeneratedValue(strategy = GenerationType.AUTO)
       private int id;
       private String orderID;
       private String orderDate;
       private String deliveryDate;
       private String deliveryAddress;
       private String orderTime;
       @OneToOne
       @JoinColumn(name = "product_id")
       private Product product;

       public void setId(int id) {
              this.id = id;
       }

       public int getId() {
              return id;
       }

       public void setOrderID(String orderID) {
              this.orderID = orderID;
       }

       public String getOrderID() {
              return orderID;
       }

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

       public Product getProduct() {
              return product;
       }

       public void setProduct(Product product) {
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