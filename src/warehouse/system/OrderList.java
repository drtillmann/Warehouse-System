

import java.util.*;
import java.io.*;
public class OrderList implements Serializable {
  private static final long serialVersionUID = 1L;
  private List manufacturers = new LinkedList();
  private static OrderList orderList;
  private OrderList() {
  }
  public static OrderList instance() {
    if (orderList == null) {
      return (orderList = new OrderList());
    } else {
      return orderList;
    }
  }
  public Manufacturer search(String manufacturerId) {
    for (Iterator iterator = manufacturers.iterator(); iterator.hasNext(); ) {
      Manufacturer manufacturer = (Manufacturer) iterator.next();
      if (manufacturer.getId().equals(manufacturerId)) {
        return manufacturer;
      }
    }
    return null;
  }
  
  public boolean insertManufacturer(Manufacturer manufacturer) {
    manufacturers.add(manufacturer);
    return true;
  }
   public boolean removeManufacturer(Manufacturer manufacturer) {
    manufacturers.remove(manufacturer);
    return true;
  }
  private void writeObject(java.io.ObjectOutputStream output) {
    try {
      output.defaultWriteObject();
      output.writeObject(orderList);
    } catch(IOException ioe) {
      ioe.printStackTrace();
    }
  }
  private void readObject(java.io.ObjectInputStream input) {
    try {
      if (orderList != null) {
        return;
      } else {
        input.defaultReadObject();
        if (orderList == null) {
          orderList = (OrderList) input.readObject();
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
    return manufacturers.toString();
  }
  
  public Iterator getManufacturers(){
      return manufacturers.iterator();
  }
  
  public Iterator getOrdertManufacturers(String orderName){
      List OrderManufacturers = new ArrayList();
      for(int i = 0; i < manufacturers.size(); i++){
          Manufacturer manu = (Manufacturer) manufacturers.get(i);
          Iterator ordeIterator = manu.getOrders();
          while(ordeIterator.hasNext()){
              Order orde = (Order) ordeIterator.next();
              if(orde.getOrderName().equals(orderName)){
                  OrderManufacturers.add(manufacturers.get(i));
                  break;
              }
          }
      }
      
      return OrderManufacturers.iterator();
  }
  
  public void addOrder(Order order){
      orders.add(order);
  }
}







/*
import java.util.*;
import java.io.*;

public class OrderList implements Serializable {
  private static final long serialVersionUID = 1L;
  private List orders = new LinkedList();
  private static OrderList orderList;
  
  public OrderList() {
  }
  
  public static OrderList instance() {
    if (orderList == null) {
      return (orderList = new OrderList());
    } else {
      return orderList;
    }
  }
  
  
  
  public void addOrder(Order order){
      orders.add(order);
  }

  public boolean insertOrder(Order order) {
    orders.add(order);
    return true;
  }

  public Iterator getOrders(){
     return orders.iterator();
  }
  
  private void writeObject(java.io.ObjectOutputStream output) {
    try {
      output.defaultWriteObject();
      output.writeObject(orderList);
    } catch(IOException ioe) {
      ioe.printStackTrace();
    }
  }
  
  private void readObject(java.io.ObjectInputStream input) {
    try {
      if (orderList != null) {
        return;
      } else {
        input.defaultReadObject();
        if (orderList == null) {
          orderList = (OrderList) input.readObject();
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
    return orders.toString();
  }
  
}
*/