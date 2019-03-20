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
public class ClientOrder extends Order{
    
    private Client client;
       
    public ClientOrder(Client client){
        super();
        this.setClient(client); 
    }
    
    public void setClient(Client client){
        this.client = client;
    }

    public Client getClient(){
        return this.client;
    }
    
    
    public void addProduct(Product p){
        Product product = p;
        super.addProduct(product);
        super.setBalance(product, product.getQty());
    }
    
    public String toString(){
        return "\n(Client Order) \nClient: " + this.getClient() + "\n" + super.toString();
    }
    

    
    
}
