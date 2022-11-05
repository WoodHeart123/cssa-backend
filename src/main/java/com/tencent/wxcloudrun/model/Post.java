package com.tencent.wxcloudrun.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Generated;

import java.sql.Timestamp;
import java.util.List;

/**
 * post
 */
@Data
public class Post {

    private Integer productID;
    private String userID;
    private String sellerAvatar;
    private String sellerNickname;
    private String productTitle;
    private String productDescription;
    private Integer price;
    private ProductType productType;
    private Condition condition;
    private Timestamp time;
    private String contact;
    private List<String> images;
    private String imagesJSON;
    private String delivery;
  
    // get no need to set
    public Integer getproductID() {
        return productID;
    }

    public Integer getUserID() {
        return userID;
    }
    
    public String getSellerAvatar() {
        return sellerAvatar;
    } 
    
    public Integer getSellerNickname() {
        return sellerNickname;
    }
   
    public Timestamp time() {
        return time;
    }

    // need to set
    public void getProductDescription() {
        return productDescription;
    }
    
    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }
    
    public void getPrice() {
        return productDescription;
    }
    
    public void setPrice(Integer price) {
        this.price = price;
    }
    
    public void getProductType() {
        return productType;
    }
    
    public ProductType setProductType(ProductType productType) {
        this.productType = productType;
    }
    
    public void getCondition() {
        return condtion;
    }
    
    public void setCondition(String condition) {
        this.condition = condition;
    }
    
    public void getProductTitle() {
        return productTitle;
    }
    
    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }
    
    public void getContact() {
        return contact;
    }
    
    public void setContact(String contact) {
        this.contact = contact;
    }
    
    public void getDelivery() {
        return delivery;
    }
    
    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

}
