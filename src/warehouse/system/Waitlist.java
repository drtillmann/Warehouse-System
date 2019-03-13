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
    
    public void addProduct(Product backorderedProduct, int missingQty){
        backorderedProduct.setQty(missingQty);
        this.backorderedProducts.add(backorderedProduct);
    }
    
    public Iterator getBackorderedProducts(){
        return this.backorderedProducts.iterator();
    }
    
    public boolean isFullfilled(){
        for(Product product : this.backorderedProducts){
            if(product.getQty() != 0){
                return false;
            }
        }
        return true;
    }
    
    private void setWaitlistID(int wid){
        this.waitlistID = WAITLIST_STRING + String.valueOf(wid);
    }
}

/*
class BackorderedProduct{
    private Product product;
    private int backorderedQty;
    
    public BackorderedProduct(Product p, int backorderedQty){
        this.setProduct(p);
        this.setBackorderedQty(backorderedQty);
    }
    
    public void setProduct(Product p){
        this.product = p;
    }
    
    public void setBackorderedQty(int qty){
        this.backorderedQty = qty;
    }
    
    public Product getProduct(){
        return this.product;
    }
    
    public int getBackorderedQty(){
        return this.backorderedQty;
    }
}
*/