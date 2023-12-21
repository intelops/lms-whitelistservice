package com.spring.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.dto.AddItem;
import com.spring.dto.CourseRequest;
import com.spring.dto.LmsResponse;
import com.spring.iservice.IWishListService;
import com.spring.model.WishList;
import com.spring.repository.IWishListRepository;
import com.spring.service.BaseServiceImpl;
@Service
@Transactional
public class WishListService extends BaseServiceImpl implements IWishListService {
	@Autowired
	private IWishListRepository repository;;


	

	@Override
	
	public List<Map<String, Object>> getAllItemsFromWishList(int userId) {
		// TODO Auto-generated method stub
		//List<WishList> wishList=null;
		List<Map<String, Object>> mapList=new ArrayList<Map<String,Object>>();
		
		try {
			List<WishList> wishList=repository.getAllWishList(userId);
			for(WishList wish:wishList) {
				Map<String,Object> wishListDtoList=new HashMap<String,Object>();

				//LmsResponse list=getCourseById(wish.getProductId()).getBody();
				//CourseRequest req=new CourseRequest();
				//req.setCourse(wish.getProductId());
				Map<String,Object> list=getCourseById(wish.getProductId()).getBody().getResponse();
				wishListDtoList.put("courseName", list.get("courseName"));
				wishListDtoList.put("InstitueName", list.get("userName"));
				wishListDtoList.put("courseCategory", list.get("courseCategory"));
				wishListDtoList.put("rating", list.get("rating"));
				wishListDtoList.put("courseLevel", list.get("courseLevel"));
				wishListDtoList.put("totalDuration", list.get("totalDuration"));
				wishListDtoList.put("totalChapters", list.get("totalChapters"));
				wishListDtoList.put("image", list.get("image"));

				//wishListDtoList.put("discountPercantage", list.get("courseCategory"));

				wishListDtoList.put("courseId",list.get("courseId") );

				//wishListDtoList.put("wishList", course.getList());
				//wishListDtoList.put("course",course.getMessage1().get("courseId"));
				wishListDtoList.put("price",wish.getPrice());
				wishListDtoList.put("userId",wish.getUserId());
				wishListDtoList.put("id", wish.getId());
				mapList.add(wishListDtoList);
			}
		}
		catch (Exception e) {
			throw e;
		}
		return mapList;
	}

	@Override
	public void removeItemFromWishList(int wishListId) {
		// TODO Auto-generated method stub
		try {
			 if (!repository.existsById(wishListId))
		            throw new com.spring.exception.WishListItemNotExistException("id is invalid : " + wishListId);
		      //  cartRepository.deleteById(id);
			repository.removeItemFromWishList(wishListId);
		}
		catch (Exception e) {
			throw e;
		}
	}

	@Override
	public WishList addToWishList(AddItem addItem, int userId) {
		// TODO Auto-generated method stub
		WishList wishList=new WishList();
		try {
			 wishList.setProductId(addItem.getProductId());
			 wishList.setPrice(addItem.getPrice());
			 wishList.setUserId(userId);
			 wishList.setCreatedDate(new Date());
			 genericDao.create(wishList);
			 
		}
		catch (Exception e) {
			throw e;
		}
		return wishList;
	}

	@Override
	public void moveItemFromWishListToCart(int wishListId) {
		// TODO Auto-generated method stub
		AddItem item=new AddItem();
		genericDao.setClazz(WishList.class);
		WishList wishList=(WishList) genericDao.findOne(wishListId);
		try {
			 if (wishList!=null) {
				 
				 item.setPrice(wishList.getPrice());
				 item.setProductId(wishList.getProductId());
				 item.setUserId(wishList.getUserId());
				 addTocart(item);
				 repository.removeItemFromWishList(wishListId);
			 }
				    
			
		}
		catch (Exception e) {
			throw e;
		}
	}

	
}
