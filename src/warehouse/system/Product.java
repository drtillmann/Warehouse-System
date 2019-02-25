//package warehouse.system;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Daniel
 */
import java.util.*;
import java.lang.*;
import java.io.*;
public class Product implements Serializable {
  private static final long serialVersionUProductID = 1L;
  private String productName;
  private String productID;
  private Manufacturer manufacturer;
  private static final String PRODUCT_STRING = "P";
  private double price;

  public Product(String productName, Manufacturer manufacturer, double price) {
    this.productName = productName;
    
    ProductList temp = ProductList.instance();
    if (!temp.isEmpty()){
        String str = null;
        try{
            str = temp.searchName(productName).getID();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        
        if(str == null) {
            this.productID = PRODUCT_STRING + (ProductIdServer.instance()).getId();
        }
        else {
            this.productID = str;
        }
    }else{
        this.productID = PRODUCT_STRING + 
                (ProductIdServer.instance()).getId();
    }
    
    this.manufacturer = manufacturer;
    this.price = price;
  }
  
  public Manufacturer getManufacturer() {
    return manufacturer;
  }
  
  public String getProductName() {
    return productName;
  }
  
  public String getID() {
    return productID;
  }
  
  public double getPrice() {
	return price;
  }
  
  public String toString() {
    return "productName: " + productName + ", productID: " 
	+ productID + ", price: " + String.valueOf(price) + ", Manufacturer Information: " 
            + manufacturer.toString() + "\n";
  }
} 