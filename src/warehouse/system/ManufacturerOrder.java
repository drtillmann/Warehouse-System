/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warehouse.system;

/**
 *
 * @author drtil
 */
public class ManufacturerOrder extends Order{
    
    private Product product;
    
    public ManufacturerOrder(Product product, int qty){
        super();
        product.setQty(qty);
        this.setProduct(product);
        this.setBalance(qty);
    }
    
    private void setProduct(Product product){
        this.product = product;
    }
    
    private void setBalance(int qty){
        super.setBalance(this.product, qty);
    }
    
    public String toString(){
        return "\n(Manufacturer Order) \nManufacturer: " + product.getManufacturer().getName() + "\n" + this.product;
    }
    
}
