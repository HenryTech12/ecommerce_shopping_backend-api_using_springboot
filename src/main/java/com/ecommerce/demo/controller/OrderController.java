
package com.ecommerce.demo.controller;
import com.ecommerce.demo.model.Product;
import com.ecommerce.demo.service.*;
import com.ecommerce.demo.dto.*;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.*;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    /*NOTE PRINCIPAL IN MOST METHOD AS PARAMETER IS USED TO FETCH USERNAME OF THE CURRENT L
    * LOGGED IN USER
    * */

    @PostMapping("/initiateOrder")
     public ResponseEntity<String> initiateOrder(Principal principal, @RequestBody @Valid OrderDTO orderModel) {
             ProductDTO product = orderModel.getProduct();
             if(product != null)
                return new ResponseEntity<>(orderService.makeOrder(principal.getName(), product.getCategory(), product.getProductName(), product.getPrice(),orderModel), HttpStatus.OK);
             else
                return new ResponseEntity<>("product does not exists", HttpStatus.ACCEPTED);
    }

      @PostMapping("/cancelOrder/{orderID}")
     public ResponseEntity<String> cancelOrder(Principal principal, @PathVariable String orderID) {
         return new ResponseEntity<>(orderService.cancelOrder(principal.getName(), orderID), HttpStatus.ACCEPTED);
     }

     @PostMapping("/updateOrder/{orderID}")
     public ResponseEntity<OrderDTO> updateOrder(Principal principal, @PathVariable String orderID, @RequestBody @Valid OrderDTO orderDTO) {
        return new ResponseEntity<>(orderService.updateOrder(principal.getName(), orderID, orderDTO), HttpStatus.OK);
     }

     @PostMapping("/generateNewOrderID/{orderID}")
     public ResponseEntity<String> newORDERID(Principal principal, @PathVariable String orderID) {
         return new ResponseEntity<>(orderService.generateNewOrderID(principal.getName(), orderID), HttpStatus.OK);
     }
     @PostMapping("/getAllOrders")
    public ResponseEntity<Page<List<OrderDTO>>> getAllOrders(PageRequestDTO pageRequestDTO) {
        return new ResponseEntity<Page<List<OrderDTO>>>(
           orderService.getAllOrders(new PageRequestDTO().getPageable(pageRequestDTO)),HttpStatus.OK
        );
    }

   public void payForProduct() {


  }

  @PostMapping("/lastOrder")
   public ResponseEntity<Page<OrderDTO>> getCustomerLastOrder(PageRequestDTO pageRequestDTO) {
        return new ResponseEntity<>(orderService.lastOrder(new PageRequestDTO().getPageable(pageRequestDTO)), HttpStatus.OK);
   }

}