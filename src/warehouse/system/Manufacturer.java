package warehouse.system;

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
public class Manufacturer implements Serializable {
  private static final long serialVersionUID = 1L;
  private String Name;
  private String ID;
  /**
   * Wrong. do not use ProductList
   */
  //private ProductList manuProducts;
  private List manuProducts;
  
  public Manufacturer(String Name, String ID) {
    setName(Name);
    setId(ID);
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
    this.ID = ID;
  }
  
  public String getId() {
    return ID;
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
  
  
  public Iterator getProducts(){
      //return manuProducts.getProducts();
      return manuProducts.iterator();
  }
  
  public String toString() {
 //     return "title " + title + " Name " + Name 
 //	  + " ID " + ID;
	return "Manufacturer Name: " + Name + ", ID: " + ID;
  }
} 

