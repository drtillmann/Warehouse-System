/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warehouse.system;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author drtil
 */
public class Waitlist implements Serializable{
    
    private String waitlistID;
    private String orderID;
    private List<Product> backorderedProducts = new ArrayList<>();
    public static final String WAITLIST_STRING = "W";
    
    public Waitlist(){
        this.setWaitlistID((WaitlistIDServer.instance()).getId());
    }
    
    public void setOrderID(String id){
        this.orderID = id;
    }
    
    public String getOrderID(){
        return this.orderID;
    }
    
    public String getWaitlistID(){
        return this.waitlistID;
    }
    
    public void addProduct(Product backorderedProduct, int missingQty){
        backorderedProduct.setQty(missingQty);
        this.backorderedProducts.add(backorderedProduct);
    }
    
    public Iterator getBackorderedProducts(){
        return this.backorderedProducts.iterator();
    }
    
     // Within Waitlist
    public int decrement(String productID, int quantity, String manu){
        Iterator iter = getBackorderedProducts();
        for (; iter.hasNext(); ) {
      Product product = (Product) iter.next();
      if (product.getID().equals(productID)
              && product.getManufacturer().getId().equals(manu)) {
        if(quantity <= product.getQty()){
            int qty = product.getQty() - quantity;
            product.setQty(qty);
            quantity = 0;
        }
        else
        {
            quantity = quantity - product.getQty();
            product.setQty(0);
        }
      }
     }
        return quantity;
    }
    
    public Boolean needsProduct(String productID, String manu){
     Iterator iter = getBackorderedProducts();
     for (; iter.hasNext(); ) {
      Product product = (Product) iter.next();
      if (product.getID().equals(productID) 
              && product.getManufacturer().getId().equals(manu)) {
          return true;
      }
     }
     return false;   
    }
    //
    
    public boolean isFullfilled(){
        for(Product product : this.backorderedProducts){
            if(product.getQty() != 0){
                return false;
            }
        }
        return true;
    }
    
    public boolean containsProduct(String pid){
        for(Product p : this.backorderedProducts){
            if(p.getID().equals(pid)){
                return true;
            }
        }
        return false;
    }
    
    public String toString(){
        String sWaitlist =  "Waitlist ID: " + this.waitlistID + "\nOrder ID: " + this.orderID + "\nBackordered Products: ";
        for(Product p : this.backorderedProducts){
            sWaitlist += "\n"+ p;
        }
        return sWaitlist;
    }
    
    private void setWaitlistID(int wid){
        this.waitlistID = WAITLIST_STRING + String.valueOf(wid);
    }
}