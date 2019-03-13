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
import java.io.*;

public class ProductList implements Serializable {
  
  private static final long serialVersionUID = 1L;
  private List products = new LinkedList();
  private static ProductList productList;
  
  private ProductList() {
  }
  
  public static ProductList instance() {
    if (productList == null) {
      return (productList = new ProductList());
    } else {
      return productList;
    }
  }
  
  public Product search(String productId) {
    for (Iterator iterator = products.iterator(); iterator.hasNext(); ) {
      Product product = (Product) iterator.next();
      if (product.getID().equals(productId)) {
        return product;
      }
    }
    return null;
  }
  
  public boolean productExists(String pid){
      Iterator productIterator = products.iterator();
      while(productIterator.hasNext()){
          Product product = (Product) productIterator.next();
          if(product.getID().equals(pid)){
              return true;
          }
      }
      return false;
  }
  
  public Product searchName(String productName) {
    for (Iterator iterator = products.iterator(); iterator.hasNext(); ) {
      Product product = (Product) iterator.next();
      if (product.getProductName().equals(productName)) {
        return product;
      }
    }
    return null;
  }
  
  public boolean insertProduct(Product product) {
    products.add(product);
    return true;
  }
  
  public boolean removeProduct(Product product) {
	products.remove(product);
	return true;
  }
  
  public boolean removeProduct(String pid){
      int index = -1;
      //JOptionPane.showMessageDialog(null, products.size());
      for(int i = 0; i < products.size(); i++){
          Product product = (Product)products.get(i);
          if(product.getID().equals(pid)){
              index = i;
              break;
          }
      }
      //JOptionPane.showMessageDialog(null, index);
      if(index != -1){
          products.remove(index);
          return true;
      }
      return false;
  }
  
  private void writeObject(java.io.ObjectOutputStream output) {
    try {
      output.defaultWriteObject();
      output.writeObject(productList);
    } catch(IOException ioe) {
      ioe.printStackTrace();
    }
  }
  
  public boolean isEmpty(){
      return products.isEmpty();
  }
  
  private void readObject(java.io.ObjectInputStream input) {
    try {
      if (productList != null) {
        return;
      } else {
        input.defaultReadObject();
        if (productList == null) {
          productList = (ProductList) input.readObject();
        } else {
          input.readObject();
        }
      }
    } catch(IOException ioe) {
      ioe.printStackTrace();
    } catch(ClassNotFoundException cnfe) {
      cnfe.printStackTrace();
    }
  }
  
  public String toString() {
    return products.toString();
  }
  
  public Iterator getProducts(){
      return products.iterator();
  }
  
  /**
   * Definition: Specify the product ID and update that product's quantity
   * @param productID
   * @param qty
   */
  public void updateQty(String productID, int qty){
      Product orderedProduct = this.search(productID);
      orderedProduct.setQty(qty);
  }
  
}