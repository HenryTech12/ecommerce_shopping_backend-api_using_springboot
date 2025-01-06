package com.ecommerce.demo.service;
import com.ecommerce.demo.mail.CustomMailSender;
import com.ecommerce.demo.repo.*;
import com.ecommerce.demo.dto.*;
import com.ecommerce.demo.model.*;
import com.ecommerce.demo.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class OrderService {

     @Autowired
    private OrderRepo repo;
     @Autowired
     private ProductRepo productRepo;
     @Autowired
     private OrderMapper orderMapper;
     @Autowired
     private CustomMailSender customMailSender;
     @Autowired
     private UserRepo userRepo;

     public String makeOrder(String principal, String category , String productName, double price, OrderDTO orderDTO) {
        Optional<Product> optionalProduct = productRepo.findByProductNameAndCategoryAndPrice(productName,category,price);
        String orderId = "";
        if(optionalProduct.isPresent() && !orderExists(optionalProduct)) {
            if(orderDTO.getDeliveryDate() != null && orderDTO.getDeliveryAddress() != null) {
                if (orderDTO.getOrderDate() == null) {
                    UserOrder userOrder = new UserOrder();

                    LocalDateTime dateTime = LocalDateTime.now();
                    orderId = generateOrderId();// GENERATE ORDER ID
                    userOrder.setOrderID(orderId);//SET ORDER ID
                    customMailSender.sendMail(userRepo.findByUsername(principal).get().getEmail(),
                            "<h1 style='color: red'>YOUR ORDER ID FOR PRODUCT "+productName+"" +
                                    " IS : "+ orderId+" <br> AFTER 24 HOURS OF GETTING ORDER ID YOUR ORDER ID BECOMES INVALID" +
                                    "</h1>"
                            );
                    //SET DATE FOR SPECIFIC ORDER
                    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    String date = dateFormat.format(dateTime);

                    System.out.println("Date Format: " + date);

                    userOrder.setOrderDate(date); // SET ORDER DATE

                    if(orderDTO.getOrderTime() == null) {
                        //SET TIME FOR SPECIFIC ORDER
                        userOrder.setOrderTime(dateTime.getHour() + ":" + dateTime.getMinute() + ":" + dateTime.getSecond());
                    }
                    userOrder.setProduct(optionalProduct.get());
                    userOrder.setDeliveryDate(orderDTO.getDeliveryDate());
                    userOrder.setDeliveryAddress(orderDTO.getDeliveryAddress());

                    repo.save(userOrder);
                        return "proceed to purchase product with given ORDER_ID : Your order id is :" + orderId;
                    }
                }
            }
           else {
               return "can't create order of empty delivery date or empty delivery address";
        }
           return "product does not exists";
     }

     public String cancelOrder(String principal, String orderId) {
         Optional<UserOrder> optionaluserOrder = repo.findByOrderID(orderId);
         UserOrder userOrder = null;
         if(optionaluserOrder.isPresent() && !invalidOrderID(principal,orderId)) {
             userOrder = optionaluserOrder.orElseThrow(
                     () -> {
                         throw new NullPointerException("invalid order id");
                     }
             );
             Optional<UserOrder> optionalUserOrder = repo.findByOrderID(orderId);
             if(optionalUserOrder.isPresent()) {
                 UserOrder userOrder1 = optionalUserOrder.orElseThrow(() -> {
                     throw new NullPointerException("order not made for designed product");
                 });
                 repo.deleteByProduct(optionalUserOrder.get().getProduct());
                 repo.deleteById(userOrder1.getId());
             }
             System.out.println("order successfully canceled");
         }
         else {
             return "request failed, invalid order_id";
         }
         Product product = userOrder.getProduct();
         return "order successfully canceled for product: "+product.getProductName() + " under "+product.getCategory()+" category";
     }

     public String generateOrderId() {
         return UUID.randomUUID().toString();
     }

     public OrderDTO updateOrder(String principal, String orderId, OrderDTO orderDTO) {
        Optional<UserOrder> userOrderOptional = repo.findByOrderID(orderId);
        UserOrder newUserOrder = null;
        if(userOrderOptional.isPresent() && !invalidOrderID(principal, orderId)) {
            UserOrder userOrder = userOrderOptional.orElse(new UserOrder());
            newUserOrder = orderMapper.convertOrderDTOToUserOrder(orderDTO);
            if(orderDTO.getOrderDate() == null) {
                newUserOrder.setOrderTime(userOrder.getOrderTime());
                newUserOrder.setOrderDate(userOrder.getOrderDate());
            }
            newUserOrder.setOrderID(generateOrderId());
            newUserOrder.setId(userOrder.getId());
        }
        return orderMapper.convertUserOrderToOrderDTO(newUserOrder);

     }
     public Page<List<OrderDTO>> getAllOrders(Pageable pageable) {
         Page<UserOrder> userOrdersList = repo.findAll(pageable);
         List<OrderDTO> orderDTOList = new ArrayList<>();
         for(UserOrder userOrder: userOrdersList) {
             OrderDTO orderDTO = orderMapper.convertUserOrderToOrderDTO(userOrder);
             orderDTOList.add(orderDTO);
         }
         return new PageImpl<List<OrderDTO>>(Collections.singletonList(orderDTOList));

     }

     public Page<OrderDTO> lastOrder(Pageable pageable) {
        Optional<List<UserOrder>> optionalUserOrder = repo.findAllByOrderByOrderDateDesc(pageable);
         OrderDTO orderDTO = null;
         if(optionalUserOrder.isPresent()) {
             List<UserOrder> orders = optionalUserOrder.orElseThrow(() -> {
                 throw new NullPointerException("product does not exist");
             });
             if(!orders.isEmpty()) {
                 orderDTO = orderMapper.convertUserOrderToOrderDTO(orders.get(0));
             }
         }
        return new PageImpl<>(Collections.singletonList(orderDTO));
     }

     public boolean orderExists(Optional<Product> optionalProduct) {
         Optional<UserOrder> optionalUserOrder = Optional.empty();
         if(optionalProduct.isPresent()) {
             Product product = optionalProduct.get();
             optionalUserOrder = repo.findByProduct(product);
         }
         return optionalUserOrder.isPresent();
     }

     public boolean invalidOrderID(String principal, String orderId) {
         boolean result = false;
         Optional<UserOrder> optionalUserOrder = repo.findByOrderID(orderId);
         if(optionalUserOrder.isPresent()) {
             UserOrder userOrder = optionalUserOrder.orElseThrow(
                     () -> {
                         throw new NullPointerException("orderID is invalid");
                     }
             );
             int[] int_time = getTime(userOrder.getOrderTime());
             LocalTime LT_orderTime = LocalTime.of(int_time[0],int_time[1],int_time[2]);
             LocalTime orderTime = LocalTime.now();
             if(LT_orderTime.plusHours(24).isAfter(orderTime)) {
                 result = LT_orderTime.plusHours(24).isAfter(orderTime);

                 customMailSender.sendMail(userRepo.findByUsername(principal).get().getEmail(),
                         "<h1 style='color: red'>YOUR ORDER ID FOR PRODUCT "+userOrder.getProduct().getProductName()+"" +
                                 " HAS EXPIRED, GENERATE A NEW ORDER_ID FOR SPECIFIC PRODUCT"+
                                 "</h1>"
                 );
             }
         }
         return result;
     }

     public String generateNewOrderID(String principal, String orderID) {
          Optional<UserOrder> optionalUserOrder = repo.findByOrderID(orderID);
          UserOrder userOrder = null;
          Product p = null;
          if(optionalUserOrder.isPresent() && !invalidOrderID(principal, orderID)) {
              String  newOrderID =  generateOrderId();
              userOrder = optionalUserOrder.orElse(new UserOrder());
              userOrder.setOrderID(newOrderID);
              repo.save(userOrder);
              p = userOrder.getProduct();
              customMailSender.sendMail(userRepo.findByUsername(principal).get().getEmail(),
                      "<h1 style='color: red'>YOUR NEW ORDER ID FOR PRODUCT "+p.getProductName()+"" +
                              " IS : "+ orderID+" <br> AFTER 24 HOURS OF GETTING ORDER ID YOUR ORDER ID BECOMES INVALID" +
                              "</h1>"
              );
              return "orderID generated successfully";
          }
          else {
              if (optionalUserOrder.isPresent())
                  return "ORDER_ID PRODUCT " + optionalUserOrder.orElse(new UserOrder()).getProduct().getProductName() + " IS STILL VALID";
          }
          return "";
     }

     public int[] getTime(String time) {
         String[] result = time.split(":");
         return new int[] {
                 Integer.parseInt(result[0]),
                 Integer.parseInt(result[1]),
                 Integer.parseInt(result[2])
         };
     }
}