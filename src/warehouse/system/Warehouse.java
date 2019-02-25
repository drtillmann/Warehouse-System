/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package warehouse.system;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Iterator;

/**
 *
 * @author drtil
 */
public class Warehouse implements Serializable {
    
    
      private static final long serialVersionUID = 1L;
  public static final int BOOK_NOT_FOUND  = 1;
  public static final int BOOK_NOT_ISSUED  = 2;
  public static final int BOOK_HAS_HOLD  = 3;
  public static final int BOOK_ISSUED  = 4;
  public static final int HOLD_PLACED  = 5;
  public static final int NO_HOLD_FOUND  = 6;
  public static final int OPERATION_COMPLETED= 7;
  public static final int OPERATION_FAILED= 8;
  public static final int NO_SUCH_MEMBER = 9;
  private ProductList inventory;
  private ClientList clientList;
  private ManufacturerList manuList;
  private static Warehouse warehouse;
  
  
  private Warehouse() {
    inventory = ProductList.instance();
    clientList = ClientList.instance();
    manuList = ManufacturerList.instance();
  }
  public static Warehouse instance() {
    if (warehouse == null) {
      ClientIdServer.instance(); // instantiate all singletons
      ProductIdServer.instance();
      ManufacturerIdServer.instance();
      return (warehouse = new Warehouse());
    } else {
      return warehouse;
    }
  }
  public Product addProduct(String productName, String manuID , double price) {
    Manufacturer manu = manuList.search(manuID);
    Product product = new Product(productName, manu, price);
    if (inventory.insertProduct(product)) {
      return (product);
    }
    return null;
  }
  
  public Client addClient(String name, String address, String phone) {
    Client client = new Client(name, address, phone);
    if (clientList.insertClient(client)) {
      return (client);
    }
    return null;
  }
  
  public Manufacturer addManufacturer(String name, String id){
      Manufacturer manu = new Manufacturer(name, id);
      if(manuList.insertManufacturer(manu)){
          return (manu);
      }
      return null;
  }
  
  public boolean manufacturerExists(String mid){
      Iterator manuIterator = manuList.getManufacturers();
      while(manuIterator.hasNext()){
          Manufacturer manu = (Manufacturer) manuIterator.next();
          if(manu.getId().equals(mid)){
              return true;
          }
      }
      return false;
  }
  
  
  public boolean productExists(String pid){
      if(inventory.productExists(pid)) return true;
      return false;
  }
  
  public int assignProduct(String pid, String mid){
      Product product = inventory.search(pid);
      if(product == null){
          return 1;
      }
      Manufacturer manu = manuList.search(mid);
      if(manu == null){
          return 2;
      }
      manu.addProduct(product);
      return 3;
  }
  
  public boolean unassignProduct(String pid, String mid){
      Manufacturer manu = manuList.search(mid);
      return manu.removeProduct(pid) && removeProduct(pid);
  }
  
  public boolean removeProduct(String pid){
      return inventory.removeProduct(pid);
  }

  public Iterator getProducts() {
      return inventory.getProducts();
  }

  public Iterator getClients() {
      return clientList.getClients();
  }
  
  public Iterator getManufacturers(){
      return manuList.getManufacturers();
  }
  
  public Iterator getManuProducts(String mid){
      Manufacturer manu = manuList.search(mid);
      return manu.getProducts();
  }
  
  public Iterator getProductManufacturers(String pid){
      return manuList.getProductManufacturers(pid);
  }
  
  public static Warehouse retrieve() {
    try {
      FileInputStream file = new FileInputStream("WarehouseData");
      ObjectInputStream input = new ObjectInputStream(file);
      input.readObject();
      ClientIdServer.retrieve(input);
      return warehouse;
    } catch(IOException ioe) {
      ioe.printStackTrace();
      return null;
    } catch(ClassNotFoundException cnfe) {
      cnfe.printStackTrace();
      return null;
    }
  }
  public static  boolean save() {
    try {
      FileOutputStream file = new FileOutputStream("WarehouseData");
      ObjectOutputStream output = new ObjectOutputStream(file);
      output.writeObject(warehouse);
      output.writeObject(ClientIdServer.instance());
      return true;
    } catch(IOException ioe) {
      ioe.printStackTrace();
      return false;
    }
  }
  private void writeObject(java.io.ObjectOutputStream output) {
    try {
      output.defaultWriteObject();
      output.writeObject(warehouse);
    } catch(IOException ioe) {
      System.out.println(ioe);
    }
  }
  private void readObject(java.io.ObjectInputStream input) {
    try {
      input.defaultReadObject();
      if (warehouse == null) {
        warehouse = (Warehouse) input.readObject();
      } else {
        input.readObject();
      }
    } catch(IOException ioe) {
      ioe.printStackTrace();
    } catch(Exception e) {
      e.printStackTrace();
    }
  }
  public String toString() {
    return inventory + "\n" + clientList;
  }
    
}
