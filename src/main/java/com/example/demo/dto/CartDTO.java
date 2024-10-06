package com.example.demo.dto;

import com.example.demo.entity.AppUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartDTO {
    private Long id;
    private double totalPrice;
    private Long customerId;
    private List<CartItemDTO> cartItems;
}
