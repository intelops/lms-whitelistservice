package com.spring.model;



import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;


@Entity
@Table(name = "wishlist")
public class WishList implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @Column
    private int userId;

    @Column
    private Date createdDate;

    @Column
    private int productId;

    @Column
    private double price;
    @Column
  private boolean isActive=true;



public double getPrice() {
		return price;
	}



	public void setPrice(double price) {
		this.price = price;
	}



public int getId() {
	return id;
}



public void setId(int id) {
	this.id = id;
}



public int getUserId() {
	return userId;
}



public void setUserId(int userId) {
	this.userId = userId;
}



public Date getCreatedDate() {
	return createdDate;
}



public void setCreatedDate(Date createdDate) {
	this.createdDate = createdDate;
}



public int getProductId() {
	return productId;
}



public void setProductId(int productId) {
	this.productId = productId;
}



public boolean isActive() {
	return isActive;
}



public void setActive(boolean isActive) {
	this.isActive = isActive;
}

   
}
