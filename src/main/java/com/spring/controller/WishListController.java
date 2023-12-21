package com.spring.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.spring.dto.AddItem;
import com.spring.dto.ApiResponse;
import com.spring.dto.LmsResponse;
import com.spring.iservice.IWishListService;
import com.spring.model.WishList;

@RestController
@RequestMapping("/wish")
public class WishListController {
	@Autowired
	private IWishListService wishListService;

	static Logger logger = Logger.getLogger(WishListController.class);
	@RequestMapping(value = "/addToWishList", method = RequestMethod.POST, headers = "content-type=application/json")	 
	public ResponseEntity<ApiResponse> addToWishList(@RequestBody AddItem addItem) throws SQLException {

		try {
			//	HttpServletRequest req = (HttpServletRequest) request;
			//String auth = req.getHeader("Token");
			//String validateToken=validateToken(auth,"USER");
			//if(validateToken.equalsIgnoreCase("true")) {
			int userId=1;
			wishListService.addToWishList(addItem,userId);
			//  StripeResponse stripeResponse = new StripeResponse(session.getId());
			// send the stripe session id in response


		}
		//else
		//{
		//return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
		//}


		catch (Exception e) {
			String stackTrace = ExceptionUtils.getStackTrace(e);
			logger.error(stackTrace);
			return new ResponseEntity<ApiResponse>(new ApiResponse(false, "service failed due to some exceptions",500), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Added to wishlist",201), HttpStatus.CREATED);

	}
	@RequestMapping(value = "/getAllItemsFromWishList", method = RequestMethod.POST, headers = "content-type=application/json")	 
	public ResponseEntity<LmsResponse> getAllItemsFromWishList(@RequestBody JSONObject req) throws SQLException {
		JSONObject json=new JSONObject();
		List<Map<String, Object>> wishList;
		LmsResponse response=new LmsResponse();
		try {
			//	HttpServletRequest req = (HttpServletRequest) request;
			//String auth = req.getHeader("Token");
			//String validateToken=validateToken(auth,"USER");
			//if(validateToken.equalsIgnoreCase("true")) {
			int userId=1;
			wishList= wishListService.getAllItemsFromWishList(userId);
			response=new LmsResponse(true,wishList,200);
			//  StripeResponse stripeResponse = new StripeResponse(session.getId());
			// send the stripe session id in response
			// if(wishList!=null) {
			//json.put("list",wishList);
			// return new ResponseEntity<String>(uniqueId, HttpStatus.OK);
			//}

		}
		//else
		//{
		//return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
		//}


		catch (Exception e) {
			String stackTrace = ExceptionUtils.getStackTrace(e);
			logger.error(stackTrace);
			response.setStatus(500);
			return new ResponseEntity<LmsResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
				return new ResponseEntity<LmsResponse>(response, HttpStatus.OK);
		       
	}
	@RequestMapping(value = "/removeItemFromWishList", method = RequestMethod.POST, headers = "content-type=application/json")	 
	public ResponseEntity<ApiResponse> removeItemFromWishList(@RequestBody JSONObject req) throws SQLException {
		JSONObject json=new JSONObject();
		WishList wishList;
		//  List<AddToCartDto> cartList;
		try {
			//	HttpServletRequest req = (HttpServletRequest) request;
			//String auth = req.getHeader("Token");
			//String validateToken=validateToken(auth,"USER");
			//if(validateToken.equalsIgnoreCase("true")) {
			int userId=1;
			wishListService.removeItemFromWishList(Integer.parseInt(req.get("wishListId").toString()));
			//  StripeResponse stripeResponse = new StripeResponse(session.getId());
			// send the stripe session id in response


		}
		//else
		//{
		//return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
		//}


		catch (Exception e) {
			String stackTrace = ExceptionUtils.getStackTrace(e);
			logger.error(stackTrace);
			return new ResponseEntity<ApiResponse>(new ApiResponse(false, "service failed due to some exceptions",500), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Item has been removed",201), HttpStatus.OK);

	}
	@RequestMapping(value = "/moveItemFromWishListTocart", method = RequestMethod.POST, headers = "content-type=application/json")	 
	public ResponseEntity<ApiResponse> moveItemFromWishListTocart(@RequestBody JSONObject req) throws SQLException {
		JSONObject json=new JSONObject();
		WishList wishList;
		//  List<AddToCartDto> cartList;
		try {
			//	HttpServletRequest req = (HttpServletRequest) request;
			//String auth = req.getHeader("Token");
			//String validateToken=validateToken(auth,"USER");
			//if(validateToken.equalsIgnoreCase("true")) {
			int userId=1;
			wishListService.moveItemFromWishListToCart(Integer.parseInt(req.get("wishListId").toString()));
			//  StripeResponse stripeResponse = new StripeResponse(session.getId());
			// send the stripe session id in response


		}
		//else
		//{
		//return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
		//}


		catch (Exception e) {
			String stackTrace = ExceptionUtils.getStackTrace(e);
			logger.error(stackTrace);
			return new ResponseEntity<ApiResponse>(new ApiResponse(true, "service failed due to some exceptions",500), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Item has been moved to cart",201), HttpStatus.OK);

	}
}
