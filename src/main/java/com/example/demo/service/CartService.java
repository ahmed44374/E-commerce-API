package com.example.demo.service;

import com.example.demo.dto.CartItemDTO;
import com.example.demo.dto.ProductDTO;
import com.example.demo.entity.AppUser;
import com.example.demo.entity.Cart;
import com.example.demo.entity.CartItem;
import com.example.demo.entity.Product;
import com.example.demo.mapper.CartItemMapper;
import com.example.demo.mapper.ProductMapper;
import com.example.demo.repository.CartItemRepository;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final ProductService productRepository;
    public CartService(CartRepository cartRepository, CartItemRepository cartItemRepository, UserRepository userRepository, JwtService jwtService, ProductService productRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.productRepository = productRepository;
    }

    // add product to cart
    public void addProductToCart(CartItemDTO cartItem,String token) throws RuntimeException{
        ProductDTO product =  productRepository.getProductById(cartItem.getProductId());
        int maxQuantity = product.getQuantity();
        double productPrice = product.getPrice();
        if(maxQuantity < cartItem.getQuantity()) {
            throw new RuntimeException("This quantity isn't available rn");
        }
        String username = jwtService.extractUsername(token);
        AppUser customer = userRepository.findByEmail(username).orElseThrow();
        Cart cart = cartRepository.findCartByCustomer(customer).orElse(new Cart());
        cart.setCustomer(customer);
        CartItem tempCartItem = CartItemMapper.toEntity(cartItem, ProductMapper.toEntity(productRepository.getProductById(cartItem.getProductId())));
        cartItemRepository.save(tempCartItem);
        System.out.println("lol");
        if(cart.getCartItems() == null) {
            List<CartItem> cartItems = new ArrayList<>();
            cartItems.add(tempCartItem);
            cart.setCartItems(cartItems);
            System.out.println("fuck");
        }else {
            cart.getCartItems().add(tempCartItem);
            System.out.println("dick");
        }
        double totalPrice = cart.getTotalPrice();
        totalPrice+= productPrice * cartItem.getQuantity();
        cart.setTotalPrice(totalPrice);
        cartRepository.save(cart);
    }
}
