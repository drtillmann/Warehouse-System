//package warehouse.system;


import java.util.*;
import java.io.*;

public class OrderList implements Serializable {
  private static final long serialVersionUID = 1L;
  public List orders = new LinkedList();
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
  
  public ClientOrder getOrder(String oID){
	  int i=0;
	  ClientOrder o=(ClientOrder)orders.get(i);
	  while(i<orders.size()){	  
		  
		  if(o.getId()==oID){
			  break;
		  }
		  
		  i++;
	  
  }
  return o;
}

  public Iterator getOrders(){
     return orders.iterator();
  }
  
  // Within OrderList
   public Order getManufacturerOrder(String orderId) {
    for (Iterator iterator = orders.iterator(); iterator.hasNext(); ) {
      Order order = (Order) iterator.next();
      if (order.getId().equals(orderId) && order instanceof ManufacturerOrder) {
        return order;
      }
    }
    return null;
  }
   
  public void clearOrderQty(String orderId){
      for (Iterator iterator = this.orders.iterator(); iterator.hasNext(); ) {
      Order order = (Order) iterator.next();
      if (order.getId().equals(orderId) && order instanceof ManufacturerOrder) {
        order.clearProductQty();
      }
    }
  }
  //

  
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
