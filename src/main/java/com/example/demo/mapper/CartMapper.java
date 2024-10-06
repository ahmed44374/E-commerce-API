package com.example.demo.mapper;

import com.example.demo.dto.CartDTO;
import com.example.demo.entity.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {

    @Mapping(source = "customer.id", target = "customerId")
    CartDTO cartToCartDTO(Cart cart);

    @Mapping(source = "customerId", target = "customer.id")
    Cart cartDTOToCart(CartDTO cartDTO);
}
