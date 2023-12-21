package com.spring.iservice;

import java.util.List;

import com.spring.Dto.AddToCartDto;
import com.spring.model.Cart;

public interface ICartService {
	List<AddToCartDto> getAllItemsInCart(int userId);
	Cart addToCart( AddToCartDto addCart,int userId);
	void removeItemFromCart(int parseInt);

}
