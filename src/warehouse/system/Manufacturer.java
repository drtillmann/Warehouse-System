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
import java.io.*;
public class Manufacturer implements Serializable {
  private static final long serialVersionUID = 1L;
  private String Name;
  private String id;
  /**
   * Wrong. do not use ProductList
   */
  //private ProductList manuProducts;
  private List manuProducts;

  private static final String MANU_STRING = "M";
  
  public Manufacturer(String Name) {
    setName(Name);
        this.id = MANU_STRING + (ManufacturerIdServer.instance()).getId();


    //this.manuProducts = ProductList.instance();
    manuProducts = new LinkedList();
  }
  
  public void setName(String name){
      this.Name = name;
  }

  public String getName() {
    return Name;
  }
  
  public void setId(String ID){
    this.id = ID;
  }
  
  public String getId() {
    return id;
  }
  
  public void addProduct(Product product){
      //this.manuProducts.insertProduct(product);
      this.manuProducts.add(product);
  }
  
  public boolean removeProduct(String pid){
      int index = -1;
      for(int i = 0; i < this.manuProducts.size(); i++){
          Product p = (Product)this.manuProducts.get(i);
          if(p.getID().equals(pid)){
              index = i;
              break;
          }
      }
      if(index != -1){
        this.manuProducts.remove(index);
        return true;
      }
      return false;
  }
  
  public boolean containsProduct(String pid){
      Iterator products = this.getProducts();
      while(products.hasNext()){
          Product p = (Product)products.next();
          if(p.getID().equals(pid)){
              return true;
          }
      }
      return false;
  }


    Iterator getOrders() {
        return manuProducts.iterator(); 
    }
  
  
  public Iterator getProducts(){
      //return manuProducts.getProducts();
      return manuProducts.iterator();
  }
  
  public String toString() {
 //     return "title " + title + " Name " + Name 
 //	  + " ID " + ID;
	return "Manufacturer Name: " + Name + ", ID: " + id;
  }
} 

