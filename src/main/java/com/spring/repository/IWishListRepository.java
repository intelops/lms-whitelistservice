package com.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.spring.dto.AddItem;
import com.spring.model.WishList;


@Repository
public interface IWishListRepository  extends CrudRepository<WishList,Integer>{
	@Query("select a from WishList a where  a.userId=:userId and a.isActive=1") 
	List<WishList> getAllWishList(@Param("userId")int userId);
	@Modifying
@Query("UPDATE  WishList SET isActive=0  WHERE id=:id") 
	void removeItemFromWishList(@Param("id")int id);
		
}
