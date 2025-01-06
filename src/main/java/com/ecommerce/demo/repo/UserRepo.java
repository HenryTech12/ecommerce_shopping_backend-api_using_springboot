package com.ecommerce.demo.repo;
import com.ecommerce.demo.model.*;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<UserData, Integer> {

       Optional<UserData> findByUsername(String username);
       Optional<UserData> findByEmail(String email);
}