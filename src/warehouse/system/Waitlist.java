

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
    private List<Client> backorderedClients = new ArrayList<>();
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
   
    public void addClient(Client backorderedClient, int missingQty){
        backorderedClient.setQty(missingQty);
        this.backorderedClients.add(backorderedClient);
    } 
    
    public Iterator getBackorderedClients(){
        return this.backorderedClients.iterator();
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
        
        for(Client client : this.backorderedClients){
            if(client.getQty() != 0){
                return false;
            }
        }
        
        return true;
    }
    
      public boolean containsClient(String pid){
        for(Client p : this.backorderedClients){
            if(p.getID().equals(pid)){
                return true;
            }
        }
        return false;
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
         for(Client p : this.backorderedClients){
            sWaitlist += "\n"+ p;
        }
        return sWaitlist;
    }
    
    
    private void setWaitlistID(int wid){
        this.waitlistID = WAITLIST_STRING + String.valueOf(wid);
    }
}