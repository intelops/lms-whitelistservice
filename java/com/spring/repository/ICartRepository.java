package com.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.spring.Dto.AddToCartDto;
import com.spring.model.Cart;


@Repository
public interface ICartRepository  extends PagingAndSortingRepository<Cart,Integer>{
	@Query("select a from cart a where  a.userId=:userId") 
	List<Cart> getAllList(@Param("userId")int userId);
	@Modifying
@Query("UPDATE  Order SET isActive=0  WHERE id=:cartId") 
	void removeItemFromCart(@Param("cartId")int cartId);

}
