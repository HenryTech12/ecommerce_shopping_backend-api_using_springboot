package com.ecommerce.demo.repo;
import com.ecommerce.demo.model.*;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface OrderRepo extends JpaRepository<UserOrder,Integer> {

   Optional<List<UserOrder>> findAllByOrderByOrderDateDesc(Pageable pageable);
   Optional<UserOrder> findByOrderID(String orderID);
   Optional<UserOrder> findByProduct(Product product_id);
   @Transactional
   void deleteByProduct(Product product_id);
}