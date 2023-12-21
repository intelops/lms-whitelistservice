package com.spring.serviceImpl;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.Dto.AddToCartDto;
import com.spring.iservice.ICartService;
import com.spring.model.Cart;
import com.spring.model.CartItem;
import com.spring.repository.ICartRepository;
import com.spring.service.BaseServiceImpl;
@Service
public class CartService extends BaseServiceImpl implements ICartService {
	@Autowired
	private ICartRepository repository;;


	@Override
	@Transactional
	public Cart addToCart(AddToCartDto addCart,int userId){
	
		Cart cart=new Cart();
		CartItem item=new CartItem();
		try {
			
			cart.setProductId(addCart.getProductId());
			cart.setUserId(userId);
			//cart.setQuantity(addCart.getQuantity());
			cart.setPrice(addCart.getPrice());
			cart.setCreatedDate(new Date());
			genericDao.create(cart);
		}
		catch (Exception e) {
			throw e;
		}
		return cart;
	}

	@Override
	@Transactional
	public List<AddToCartDto> getAllItemsInCart(int userId) {
		// TODO Auto-generated method stub
		List<Cart> cartList=null;
		List<AddToCartDto> cartDtoList=null;
		try {
			cartList=repository.getAllList(userId);
			for(Cart cart:cartList) {
				AddToCartDto cartDto=new AddToCartDto(cart.getId(),cart.getProductId(),cart.getPrice(),cart.getUserId());
				cartDtoList.add(cartDto);
			}
		}
		catch (Exception e) {
			throw e;
		}
		return cartDtoList;
	}

	@Override
	@Transactional
	public void removeItemFromCart(int cartId) {
		// TODO Auto-generated method stub
		try {
			 if (!repository.existsById(cartId))
		            throw new com.spring.exception.CartItemNotExistException("Cart id is invalid : " + cartId);
		      //  cartRepository.deleteById(id);
			repository.removeItemFromCart(cartId);
		}
		catch (Exception e) {
			throw e;
		}
	}
}
