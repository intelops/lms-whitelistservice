package com.spring.iservice;

import java.util.List;
import java.util.Map;

import com.spring.dto.AddItem;
import com.spring.model.WishList;

public interface IWishListService {
	
	WishList addToWishList(AddItem addItem, int userId);
	List<Map<String, Object>> getAllItemsFromWishList(int userId);
	void removeItemFromWishList(int wishListId);
	void moveItemFromWishListToCart(int parseInt);

}
