package com.ecommerce.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.modelmapper.ModelMapper;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private String[] PUBLIC_URLS = {
          //USER URLS
          "/addUser"
    };
      @Bean
     public ModelMapper getModelMapper() {
          return new ModelMapper();
      }

      @Bean
    public PasswordEncoder getPasswordEncoder() {
          return new BCryptPasswordEncoder(12);
      }

      @Bean
      public MyUserDetailsService myUserDetailsService() {
          return new MyUserDetailsService();
      }

      @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
          System.out.println("security config initialized");
          http.csrf(csrf -> csrf.disable())
                  .authorizeHttpRequests(requests -> {
                      requests.requestMatchers(PUBLIC_URLS)
                              .permitAll()
                              .anyRequest().authenticated();
                  }).httpBasic(httpBasic -> httpBasic.init(http));

          http.oauth2Login(Customizer.withDefaults());

          return http.build();
      }
}