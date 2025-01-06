package com.ecommerce.demo.mapper;

import com.ecommerce.demo.dto.*;
import com.ecommerce.demo.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service; 
import java.util.*;
import org.modelmapper.ModelMapper;

@Service
public class ProductMapper {
     
     @Autowired
      private ModelMapper modelMapper;

     public Product convertProductDTOToProduct(ProductDTO  productDto) {
             if(productDto != null) 
               return modelMapper.map(productDto, Product.class);
             else
                 return new Product();
      }

     public ProductDTO convertProductToProductDTO(Product product) {
        if(product!= null)
            return modelMapper.map(product, ProductDTO.class);
        else
            return new ProductDTO();
     }
}