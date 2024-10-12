package com.example.demo.mapper;

import com.example.demo.dto.ProductDTO;
import com.example.demo.entity.AppUser;
import com.example.demo.entity.Product;

public class ProductMapper {

    // Method to convert Product to ProductDTO
    public static ProductDTO toDto(Product product) {
        if (product == null) {
            return null; // Handle null case
        }

        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .quantity(product.getQuantity())
                .price(product.getPrice())
                .sellerId(product.getSeller().getId()) // Get seller ID if not null
                .build();
    }

    // Method to convert ProductDTO to Product
    public static Product toEntity(ProductDTO productDTO) {
        if (productDTO == null) {
            return null; // Handle null case
        }

        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setQuantity(productDTO.getQuantity());
        product.setPrice(productDTO.getPrice());

        // Assuming you have a way to fetch the AppUser by ID, you can create a new AppUser entity here if needed
        if (productDTO.getSellerId() != null) {
            AppUser seller = new AppUser(); 
            seller.setId(productDTO.getSellerId());
            product.setSeller(seller);
        }

        return product;
    }
}
