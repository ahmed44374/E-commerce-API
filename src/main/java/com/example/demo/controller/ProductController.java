package com.example.demo.controller;

import com.example.demo.dto.ProductDTO;
import com.example.demo.entity.Product;
import com.example.demo.service.ProductService;
import com.example.demo.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {
    private final ProductService productService;
    private final UserService userService;

    public ProductController(ProductService productService, UserService userService) {
        this.productService = productService;
        this.userService = userService;
    }

    // handler to show all products
    @GetMapping("/public/products")
    public ResponseEntity<List<ProductDTO>> getAllProductsHandler() {
        List<ProductDTO> products = productService.getAllProducts();
        return new ResponseEntity<>(products,HttpStatus.OK);
    }

    // handler to get product by product id
    @GetMapping("/public/product/{id}")
    public ResponseEntity<ProductDTO> getProductByIdHandler(@PathVariable Long id, HttpServletRequest request) {
        ProductDTO productDTO = productService.getProductById(id);
        return new ResponseEntity<>(productDTO,HttpStatus.OK);
    }


    // handler to add product by an admin
    @PostMapping("/admin/product/add")
    public ResponseEntity<ProductDTO> addProductHandler(@RequestBody Product product, @RequestHeader("Authorization") String authHeader){
            ProductDTO addedProduct = productService.addProduct(product,authHeader);
            return new ResponseEntity<>(addedProduct,HttpStatus.CREATED);
    }

    // handler to delete product by an admin
    @DeleteMapping("/admin/product/{id}")
    public ResponseEntity<String> deleteProductHandler(@PathVariable Long id) {
        String result = productService.deleteProduct(id);
        return new ResponseEntity<>(result,HttpStatus.CREATED);
    }

    // handler to update a product by an admin
    @PutMapping("/admin/product")
    public ResponseEntity<ProductDTO> updateProductHandler(@RequestBody Product product, @RequestHeader("Authorization") String authHeader) {
        ProductDTO result = productService.updateProduct(product,authHeader);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
}
