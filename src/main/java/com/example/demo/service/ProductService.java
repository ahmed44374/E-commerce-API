package com.example.demo.service;

import com.example.demo.dto.ProductDTO;
import com.example.demo.entity.AppUser;
import com.example.demo.entity.Product;
import com.example.demo.exceptions.ProductNotFoundException;
import com.example.demo.mapper.ProductMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final UserMapper userMapper;


    public ProductService(ProductRepository productRepository, UserService userService, UserRepository userRepository, UserMapper userMapper) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    // find all products
    public List<ProductDTO> getAllProducts() {
        List<Product> products;
        products = productRepository.findAll();
        List<ProductDTO> productDTOS = new ArrayList<>();
        for(Product product : products) {
            productDTOS.add(ProductMapper.toDto(product));
        }
        return productDTOS;
    }


    public ProductDTO getProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if(product.isEmpty()) {
            throw new RuntimeException("product not found");
        }
        return ProductMapper.toDto(product.get());
    }

    public ProductDTO addProduct(Product product, String authHeader) {
        String token = authHeader.substring(7);
        AppUser seller = userService.getCurrentLoggedInUser(token);
        product.setSeller(seller);
        productRepository.save(product);
        return ProductMapper.toDto(product);
    }

    public String deleteProduct(Long id) throws ProductNotFoundException {
        Optional<Product> product = productRepository.findById(id);
        if(product.isPresent()) {
            productRepository.delete(product.get());
            return "product deleted successfully";
        }
        throw new ProductNotFoundException("can't find product with id = " + id);
    }

    public ProductDTO updateProduct(Product product,String authHandler) throws ProductNotFoundException{
        String token = authHandler.substring(7);
        AppUser seller = userService.getCurrentLoggedInUser(token);
        Optional<Product> prod = productRepository.findById(product.getId());
        if(prod.isPresent()) {
            product.setSeller(seller);
             productRepository.save(product);
            return ProductMapper.toDto(product);
        }
        throw  new ProductNotFoundException("product not found");
    }
}
