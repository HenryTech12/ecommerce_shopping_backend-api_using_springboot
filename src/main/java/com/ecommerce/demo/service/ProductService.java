package com.ecommerce.demo.service;
import com.ecommerce.demo.dto.*;
import com.ecommerce.demo.repo.*;
import com.ecommerce.demo.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.*;
import com.ecommerce.demo.mapper.*;

@Service
public class ProductService {

    @Autowired
    private ProductRepo repo;
    @Autowired
    private ProductMapper mapper;
    public String createProduct(ProductDTO productDto) {
         if(productDto != null && !exists(productDto)) {
             Status stats = Status.AVAILABLE;
             System.out.println("PRODUCT STATUS" +stats.name());
             productDto.setStatus(stats.name());
             repo.save(mapper.convertProductDTOToProduct(productDto));
            System.out.println("product created successfully");
             return "product created successfully";
        }
         return "product already exists, try changing name or category";
     }

     public boolean exists(ProductDTO productDTO) {
        return repo.findByProductNameAndCategory(productDTO.getProductName(), productDTO.getCategory()).isPresent();
     }

    public void updateProduct(String productName, ProductDTO productDto) {
        Optional<Product> optionalProduct = repo.findByProductName(productName);
         if(optionalProduct.isPresent()) {
             Product prod = optionalProduct.
             orElseThrow(() -> {
                 throw new NullPointerException("product is null");
             });
             Product newProduct = mapper.convertProductDTOToProduct(productDto);
             newProduct.setId(prod.getId());
             if(productDto.getStatus() != null) {
                 newProduct.setStatus(productDto.getStatus());
             }
             else {
                 newProduct.setStatus(Status.AVAILABLE.name());
             }
             repo.save(prod);
             System.out.println("product update successfully");
        }
    }

   public void removeProduct(String productName) {
       Optional<Product> product = repo.findByProductName(productName);
      if(product.isPresent()) {
             repo.deleteById(product.
                     orElseThrow(() -> {
                         throw new NullPointerException("producName does not exist");
                     }).getId());
             System.out.println("product removed successfully");
      }
   }

   public Page<List<ProductDTO>> getProductsByCategory(String category, Pageable pageable) {
         List<Product> products = repo.findByCategory(category,pageable);
         List<ProductDTO> listOfProductDTO = new ArrayList<>();
         ProductDTO dto = null;
         for(Product p : products) {
             dto = mapper.convertProductToProductDTO(p);
             listOfProductDTO.add(dto);
         }
         Page<List<ProductDTO>> pageOfProductDTO = new PageImpl<List<ProductDTO>>(
                 Collections.singletonList(listOfProductDTO)
         );
         return pageOfProductDTO;
   }

    public Page<ProductDTO> getProducts(Pageable pageable) {
        Page<Product> products = repo.findAll(pageable);
        List<ProductDTO> listOfProductDTO = new ArrayList<>();
        ProductDTO dto = null;
        for(Product p : products) {
            dto = mapper.convertProductToProductDTO(p);
            listOfProductDTO.add(dto);
        }
        Page<ProductDTO> pageOfProductDTO = new PageImpl<ProductDTO>(
                listOfProductDTO
        );
        return pageOfProductDTO;
    }

    public String getCategoryByProductName(String product, Pageable pageable) {
        Optional<Product> p = repo.findByProductName(product);
        String result = "";
        if(p.isPresent()) {
            Product myProduct = p.orElse(new Product());
            result = "product : "+ myProduct.getProductName() + " belongs to :"+ myProduct.getCategory() + " category";
        }
        return result;
    }

    public void deleteByProductNameAndCategory(String productName, String category) {
        Optional<Product> optionalProduct = repo.findByProductNameAndCategory(productName, category);
        Product p = optionalProduct.orElseThrow(() -> {
            throw new NullPointerException("product does not exists");
        });
        repo.deleteById(p.getId());
    }
    public Page<List<ProductDTO>> getProductsByPrice(double price, Pageable pageable) {
        List<Product> products = repo.findByPriceGreaterThanEqualOrderByProductName(price,pageable);
        List<ProductDTO> productDTOList = new ArrayList<>();
        if(products != null) {
                for(Product p : products) {
                ProductDTO productDTO = mapper.convertProductToProductDTO(p);
                productDTOList.add(productDTO);
            }
        }

        return new PageImpl<List<ProductDTO>>(Collections.singletonList(productDTOList));
    }

    public ProductDTO updateProductByNameAndCategory(String productName, String category, ProductDTO productModel) {
        Optional<Product> optionalProduct = repo.findByProductNameAndCategory(productName,category);
        ProductDTO updatedDTO = null;
        if(optionalProduct.isPresent()) {
            Product prod = optionalProduct.
                    orElseThrow(() -> {
                        throw new NullPointerException("product is null");
                    });
            Product newProduct = mapper.convertProductDTOToProduct(productModel);
            newProduct.setId(prod.getId());
            repo.save(prod);
            System.out.println("product update successfully");
            updatedDTO = mapper.convertProductToProductDTO(newProduct);
        }
        return updatedDTO;
    }
}