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
    public String getProductDescription() {
        return productDescription;
    }
    
    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }
    
    public Integer getPrice() {
        return productDescription;
    }
    
    public void setPrice(Integer price) {
        this.price = price;
    }
    
    public ProductType getProductType() {
        return productType;
    }
    
    public void setProductType(ProductType productType) {
        this.productType = productType;
    }
    
    public Condition getCondition() {
        return condtion;
    }
    
    public void setCondition(String condition) {
        this.condition = condition;
    }
    
    public String getProductTitle() {
        return productTitle;
    }
    
    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }
    
    public String getContact() {
        return contact;
    }
    
    public void setContact(String contact) {
        this.contact = contact;
    }
    
    public String getDelivery() {
        return delivery;
    }
    
    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

}
