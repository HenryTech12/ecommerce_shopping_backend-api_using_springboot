package com.ecommerce.demo.config;

import com.ecommerce.demo.dto.UserDTO;
import com.ecommerce.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDTO userDTO =  userService.getUserWithUsername(username);
        if(userDTO == null)
            throw new UsernameNotFoundException("user doesn't exists");
        else
            return new UserPrincipal(userDTO);
    }
}
