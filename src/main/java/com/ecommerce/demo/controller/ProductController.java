package com.ecommerce.demo.controller;
import com.ecommerce.demo.model.Product;
import com.ecommerce.demo.service.*;
import com.ecommerce.demo.dto.*;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.*;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @RequestMapping("/addProduct")
     public ResponseEntity<String> addProduct(@RequestBody @Valid ProductDTO productModel) {
          return new ResponseEntity<>(productService.createProduct(productModel), HttpStatus.CREATED);
      }

     @RequestMapping("/updateProductByNameAndCategory/{productName}/{category}")
     public ResponseEntity<ProductDTO> updateProduct(@PathVariable String productName, @PathVariable String category , @RequestBody @Valid ProductDTO productModel) {
         ProductDTO updatedDTO = productService.updateProductByNameAndCategory(productName,category,productModel);
         return new ResponseEntity<>(updatedDTO, HttpStatus.ACCEPTED);
     }

     @RequestMapping("/removeProductByNameAndCategory/{productName}/{category}")
     public ResponseEntity<String> deleteProduct(@PathVariable String productName, @PathVariable String category) {
        productService.deleteByProductNameAndCategory(productName,category);
        return new ResponseEntity<>("products successfully removed", HttpStatus.ACCEPTED);
     }

     @GetMapping("/products")
    public ResponseEntity<Page<ProductDTO>> getAllProduct(PageRequestDTO pageRequestDTO) {
        Page<ProductDTO> page = productService.getProducts(new PageRequestDTO().getPageable(pageRequestDTO));
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

   @PostMapping("/getproductsByCategory/{category}")
   public ResponseEntity<Page<List<ProductDTO>>> getProductByCategory(@PathVariable String category, PageRequestDTO pageRequestDTO) {

        Page<List<ProductDTO>> productsDTO = productService.getProductsByCategory(category, new PageRequestDTO().getPageable(pageRequestDTO));
        return new ResponseEntity<Page<List<ProductDTO>>>(productsDTO, HttpStatus.OK);
   }

   @PostMapping("/getproductsByPrice/{price}")
    public ResponseEntity<Page<List<ProductDTO>>> getProductByPrice(@PathVariable double price, PageRequestDTO pageRequestDTO) {
         Page<List<ProductDTO>> productsPagedList = productService.getProductsByPrice(price,
                 new PageRequestDTO().getPageable(pageRequestDTO)
                 );
         return new ResponseEntity<>(productsPagedList, HttpStatus.OK);
    }

    @PostMapping("/categoryByProductName/{product}")
    public ResponseEntity<String> getCategoryByProduct(@PathVariable String product, PageRequestDTO pageRequestDTO) {
        String result = productService.getCategoryByProductName(product, new PageRequestDTO().getPageable(pageRequestDTO));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}