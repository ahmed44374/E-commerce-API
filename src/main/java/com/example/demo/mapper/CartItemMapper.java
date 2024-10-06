package com.example.demo.mapper;

import com.example.demo.dto.CartItemDTO;
import com.example.demo.entity.CartItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartItemMapper {

    CartItemDTO cartItemToCartItemDTO(CartItem cartItem);

    CartItem cartItemDTOToCartItem(CartItemDTO cartItemDTO);
}
