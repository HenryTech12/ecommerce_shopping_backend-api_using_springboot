package com.ecommerce.demo.repo;
import com.ecommerce.demo.model.Product;
import java.util.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ProductRepo extends JpaRepository<Product,Integer> {

      Optional<Product> findByProductName(String productName);
      List<Product> findByCategory(String category, Pageable pageable);
      List<Product> findByPriceGreaterThanEqualOrderByProductName(double price , Pageable pageable);

      Optional<Product> findByProductNameAndCategoryAndPrice(String productName, String category, double price);
      Optional<Product> findByProductNameAndCategory(String productName, String category);
}