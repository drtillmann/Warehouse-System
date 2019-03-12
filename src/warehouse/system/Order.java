/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warehouse.system;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author drtil
 */
public class Order {
    private Client client;
    private List<Product> products = new ArrayList<Product>();
    
    public Order(){
        
    }
    
    public Order(Client client, Product product, int qty){
        this.setClient(client);
        product.setQy(qty);
        this.addProduct(product);
    }
    
    public void setClient(Client client){
        this.client = client;
    }
    
    public void addProduct(Product product){
        this.products.add(product);
    }
    
    public Client getClient(){
        return this.client;
    }
    
    public Iterator getProducts(){
        return this.products.iterator();
    }
    
    public String toString(){
        String strOrder = "Client: " + client + "\n Products: ";
        
        for(Product product : this.products){
            strOrder += product + "\n";
        }        
        return strOrder;
    }
}
