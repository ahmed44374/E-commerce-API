package com.example.demo.mapper;

import com.example.demo.dto.CartItemDTO;
import com.example.demo.entity.CartItem;
import com.example.demo.entity.Product;

public class CartItemMapper {

    // تحويل من DTO إلى Entity
    public static CartItem toEntity(CartItemDTO cartItemDTO, Product product) {
        return CartItem.builder()
                .id(cartItemDTO.getId())
                .quantity(cartItemDTO.getQuantity())
                .product(product)
                .build();
    }


    public static CartItemDTO toDTO(CartItem cartItem) {
        CartItemDTO cartItemDTO = new CartItemDTO();
        cartItemDTO.setId(cartItem.getId());
        cartItemDTO.setProductId(cartItem.getProduct().getId());
        cartItemDTO.setQuantity(cartItem.getQuantity());
        return cartItemDTO;
    }
}
