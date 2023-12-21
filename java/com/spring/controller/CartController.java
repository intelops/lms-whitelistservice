package com.spring.controller;

import java.sql.SQLException;
import java.util.List;

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

import com.spring.Dto.AddToCartDto;
import com.spring.iservice.ICartService;
import com.spring.model.ApiResponse;
import com.spring.model.Cart;

@RestController
@RequestMapping("/cart")
public class CartController {
	@Autowired
	private ICartService cartService;

	static Logger logger = Logger.getLogger(CartController.class);
	@RequestMapping(value = "/addToCart", method = RequestMethod.POST, headers = "content-type=application/json")	 
	 public ResponseEntity<ApiResponse> addToCart(@RequestBody AddToCartDto addCart) throws SQLException {
			JSONObject json=new JSONObject();
		     //List<Order> orderList;
			Cart cart;
			try {
			//	HttpServletRequest req = (HttpServletRequest) request;
				//String auth = req.getHeader("Token");
				//String validateToken=validateToken(auth,"USER");
				//if(validateToken.equalsIgnoreCase("true")) {
				int userId=1;
		   cart= cartService.addToCart(addCart,userId);
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
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "service failed due to some exceptions"), HttpStatus.INTERNAL_SERVER_ERROR);
	}
			return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Added to cart"), HttpStatus.CREATED);

		}
	@RequestMapping(value = "/getAllItemsInCart", method = RequestMethod.POST, headers = "content-type=application/json")	 
	 public ResponseEntity<?> getAllItemsInCart(@RequestBody JSONObject req) throws SQLException {
			JSONObject json=new JSONObject();
		     List<AddToCartDto> cartList;
			try {
			//	HttpServletRequest req = (HttpServletRequest) request;
				//String auth = req.getHeader("Token");
				//String validateToken=validateToken(auth,"USER");
				//if(validateToken.equalsIgnoreCase("true")) {
				int userId=1;
		   cartList= cartService.getAllItemsInCart(userId);
	      //  StripeResponse stripeResponse = new StripeResponse(session.getId());
	        // send the stripe session id in response
	        if(cartList!=null) {
	        	json.put("list",cartList);
	        	// return new ResponseEntity<String>(uniqueId, HttpStatus.OK);
	        }
			
		}
		//else
		//{
			//return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
		//}


	catch (Exception e) {
		String stackTrace = ExceptionUtils.getStackTrace(e);
		logger.error(stackTrace);
		return new ResponseEntity<>("service failed due to some exceptions", HttpStatus.INTERNAL_SERVER_ERROR);
	}
			return new ResponseEntity<>(json, HttpStatus.OK);
	       
		}
	@RequestMapping(value = "/removeItemFromcart", method = RequestMethod.POST, headers = "content-type=application/json")	 
	 public ResponseEntity<ApiResponse> removeItemFromCart(@RequestBody JSONObject req) throws SQLException {
			JSONObject json=new JSONObject();
			Cart cart;
		   //  List<AddToCartDto> cartList;
		
			//	HttpServletRequest req = (HttpServletRequest) request;
				//String auth = req.getHeader("Token");
				//String validateToken=validateToken(auth,"USER");
				//if(validateToken.equalsIgnoreCase("true")) {
				int userId=1;
		    cartService.removeItemFromCart(Integer.parseInt(req.get("cartId").toString()));
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
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "service failed due to some exceptions"), HttpStatus.INTERNAL_SERVER_ERROR);
	}
			 return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Item has been removed"), HttpStatus.OK);

		}
}
