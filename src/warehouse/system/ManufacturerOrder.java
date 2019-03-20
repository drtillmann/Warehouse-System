package warehouse.system;


import java.util.*;
import java.lang.*;
import java.io.*;

public class ManufacturerOrder extends Order{
    
    private Product product;
    private static final long serialVersionUID = 1L;
    private String Name;
    private String ID;
    private List manuOrders;
    
    public ManufacturerOrder(String Name, String ID) {
    setName(Name);
    setId(ID);
    manuOrders = new LinkedList();
  }
  
  public void setName(String name){
      this.Name = name;
  }

  public String getName() {
    return Name;
  }
  
  public void setId(String ID){
    this.ID = ID;
  }
  
  public String getId() {
    return ID;
  }
  
  public void addOrder(Order order){
      //this.manuOrders.insertOrder(order);
      this.manuOrders.add(order);
  }
  
  public boolean removeProduct(String oid){
      int index = -1;
      for(int i = 0; i < this.manuOrders.size(); i++){
          Order o = (Order)this.manuOrders.get(i);
          if(o.getID().equals(oid)){
              index = i;
              break;
          }
      }
      if(index != -1){
        this.manuOrders.remove(index);
        return true;
      }
      return false;
  }
  
  
  public Iterator getOrders(){
      //return manuOrders.getOrders();
      return manuOrders.iterator();
  }
  
  public String toString() {
	return "ManufacturerOrder Name: " + Name + ", ID: " + ID;
        
  }
    public ManufacturerOrder(Product product){
        super();
        this.setProduct(product);
        this.setBalance(product.getQty());
    }
    
    private void setProduct(Product product){
        this.product = product;
    }
    
    private void setBalance(int qty){
        super.setBalance(this.product, qty);
    }
    
    
}
