/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warehouse.system;

import java.util.Iterator;

/**
 *
 * @author drtil
 */
public class ManufacturerOrder extends Order{
    
    private Product product;
    
    public ManufacturerOrder(Product product){
        super();
        this.setProduct(product);
        this.setBalance(product.getQty());
    }
    
    private void setProduct(Product product){
        this.product = product;
    }
    
    // Within ManufacturerOrder
    public Product getProduct(){
        return this.product;
    }
    
    public void clearProductQty(){
            product.setQty(0);
        }
    //
    
    private void setBalance(int qty){
        super.setBalance(this.product, qty);
    }
    
    public String toString(){
        return "\n(Manufacturer Order) \nManufacturer: " + product.getManufacturer().getName() + "\n" + this.product;
    }
    
}

