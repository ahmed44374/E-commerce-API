package com.example.demo.controller;

import com.example.demo.dto.CartItemDTO;
import com.example.demo.security.JwtService;
import com.example.demo.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;
    private final JwtService jwtService;

    public CartController(CartService cartService, JwtService jwtService) {
        this.cartService = cartService;
        this.jwtService = jwtService;
    }

    // handler to add item to the cart
    @PostMapping("/add")
    public ResponseEntity<String> addItemToCartHandler(@RequestBody CartItemDTO cartItem, @RequestHeader("Authorization")  String authHandler) {
        String token =  authHandler.substring(7);
        cartService.addProductToCart(cartItem,token);
        return new ResponseEntity<>("Cart Item added successfully", HttpStatus.OK);
    }


}
