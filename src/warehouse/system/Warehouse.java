    /*
     * To change this license header, choose License Headers in Project Properties.
     * To change this template file, choose Tools | Templates
     * and open the template in the editor.
     */
//    package warehouse.system;

import java.io.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
    import java.io.FileInputStream;
    import java.io.FileOutputStream;
    import java.io.IOException;
    import java.io.ObjectInputStream;
    import java.io.ObjectOutputStream;
    import java.io.Serializable;
import java.util.ArrayList;
    import java.util.Iterator;
import java.util.List;
import javax.swing.JOptionPane;

    /**
     *
     * @author drtil
     */
    public class Warehouse implements Serializable {


        private static final long serialVersionUID = 1L;
      
        
        private ProductList inventory;
        private ClientList clientList;
        private ManufacturerList manuList;
        private OrderList orderList;
        private WaitlistList waitlistList;
        private static Warehouse warehouse;


      private Warehouse() {
        inventory = ProductList.instance();
        clientList = ClientList.instance();
        manuList = ManufacturerList.instance();
        orderList = OrderList.instance();
        waitlistList = WaitlistList.instance();
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
      public Product addProduct(String productName, String manuID , String quantity, String price) {
        Manufacturer manu = manuList.search(manuID);
        int productQty = Integer.parseInt(quantity);
        double productPrice = Double.parseDouble(price);
        Product product = new Product(productName, manu, productQty, productPrice);
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

      public Manufacturer addManufacturer(String name){
          Manufacturer manu = new Manufacturer(name);
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
      
      public Client searchClient(String clientID){
          return clientList.search(clientID);
      }
      
      public Product searchProduct(String productID){
          return inventory.search(productID);
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
      
      public boolean manufacturerContainsProduct(String pid){
          Iterator manus = this.getManufacturers();
          while(manus.hasNext()){
              Manufacturer manu = (Manufacturer)manus.next();
              if(manu.containsProduct(pid)){
                  return true;
              }
          }
          return false;
      }
           
      
      public boolean placeClientOrder(String clientID, List<String> productIDs, List<String> orderQty){
          Client client = clientList.search(clientID);
          ClientOrder clientOrder = new ClientOrder(client);
			Iterator iterator=orderList.getOrders();
			while(iterator.hasNext()){
				Object o=(Object)iterator.next();
				
				if(o.getClass().getName().equals("ClientOrder")){
					ClientOrder temp=(ClientOrder)o;
						if(temp.getId().equals(clientOrder.getId())){
							String oldID=clientOrder.getId();
							int idNum=Integer.parseInt(oldID.substring(1));
							idNum++;
							String newID="O"+String.valueOf(idNum);
							clientOrder.setId(newID);
						}
				}
			}
          Waitlist wl = new Waitlist();
          wl.setOrderID(clientOrder.getId());
          wl.setClientID(clientID);
          boolean noWaitlistsAdded = true;
          
          for(int i = 0; i < productIDs.size(); i++){
              String id = productIDs.get(i);
              int orderedQty = Integer.parseInt(orderQty.get(i));
              Product productInInventory = inventory.search(id);
              Product clientsOrderProduct = (Product)deepCopy(productInInventory);
              
              Product wlProduct = (Product)deepCopy(productInInventory);
              int inventoryQty = productInInventory.getQty();
              int remainingOrderQty = 0;
              
              clientsOrderProduct.setQty(orderedQty);
              clientOrder.addProduct(clientsOrderProduct);             
              
              if(inventoryQty > orderedQty){
                  inventory.updateQty(id, inventoryQty - orderedQty);
              }else{
                  remainingOrderQty = Math.abs(inventoryQty - orderedQty);
                  inventory.updateQty(id, 0);
                  //Process waitlist here  
					
                    wl.addProduct(wlProduct, remainingOrderQty);
                    waitlistList.addWaitlist(wl);
                    noWaitlistsAdded = false;
              }
          }
          orderList.addOrder(clientOrder);
          System.out.println(clientOrder);
          return noWaitlistsAdded;
      }
      
      public void placeManufacturerOrder(String productID, String orderedQty){
          ManufacturerOrder manuOrder;
          Product manuOrderProduct = (Product)deepCopy(inventory.search(productID));
          manuOrderProduct.setQty(Integer.parseInt(orderedQty));
          manuOrder = new ManufacturerOrder(manuOrderProduct);
			Iterator iterator=orderList.getOrders();
			while(iterator.hasNext()){				
				Object o=(Object)iterator.next();
				
				if(o.getClass().getName().equals("ManufacturerOrder")){
					ManufacturerOrder temp=(ManufacturerOrder)o;
					if(temp.getId().equals(manuOrder.getId())){
						String oldID=manuOrder.getId();
						int idNum=Integer.parseInt(oldID.substring(1));
						idNum++;
						String newID="O"+String.valueOf(idNum);
						manuOrder.setId(newID);
					}
				}
			}
          orderList.addOrder(manuOrder); 
          System.out.println(manuOrder.toString());
      }
      
      public Iterator getWaitlistedOrdersByProductID(String pid){
        String productID = pid;
        List<Waitlist> waitlistsContainingProduct = new ArrayList<>();
        Iterator waitlists = this.getWaitlists();
        while(waitlists.hasNext()){
            Waitlist wl = (Waitlist)(waitlists.next());
            if(wl.containsProduct(productID)){
                //System.out.println(wl.toString());
                waitlistsContainingProduct.add(wl);
            }
        }
        
        
        
        return waitlistsContainingProduct.iterator();
      }
      
      public void getWaitlistedOrdersByClientID(String cid){
		Iterator iterator = waitlistList.getWaitlists();
		Client client = clientList.search(cid);	
		while (iterator.hasNext()) {
			
			Waitlist wl=(Waitlist)iterator.next();
			
			if(wl.getClientID().equals(cid)){
				System.out.println(wl.toString()); 
			}

		}
	  }
	  
     public void getManuOrders(String mid){
         Iterator iterator=orderList.getOrders();
     
         while (iterator.hasNext()){

			 ManufacturerOrder manuOrder=(ManufacturerOrder)iterator.next();

			 if (manuOrder.getProduct().getManufacturer().getId().equals(mid)){
				 System.out.println(manuOrder.toString()); 
			 }
			 
		 }
         
     }      
      
      public Iterator getOrders(){
          return orderList.getOrders();
      }
      
      
      public Iterator getWaitlists(){
          return waitlistList.getWaitlists();
      }
      

      
       // Within Warehouse
      public Order getManufacturerOrder(String orderID){
          return orderList.getManufacturerOrder(orderID);
      }
      
      public void receiveOrder(Order order){
            Product product = order.getProduct();
            int newQuantity = waitlistList.fulfill(product.getID()
                     , product.getQty()
                     , product.getManufacturer().getId());
            product.setQty(newQuantity);
            inventory.addStock(product);
            orderList.clearOrderQty(order.getId());
      }
      //


	  public void makePayment(String oID, double amt){
			
			Iterator iterator=orderList.getOrders();

			int i=0;
			
			
			
			while(iterator.hasNext()){
				Object or=(Object)iterator.next();

				
				
				if(or.getClass().getName().equals("ClientOrder")){
					
				
					ClientOrder order=(ClientOrder)or;		
					if(order.getId().equals(oID)){
						System.out.println("here\n");
						System.out.println(order.makePayment(amt));
						orderList.orders.set(i, or);
						break;
					}
					i++;
			}
			}
	  }
	  
	  public void outstandingBalance(){
		Iterator iterator = orderList.getOrders(); 	
		
		while (iterator.hasNext()) {	
			Object or=(Object)iterator.next();
			
			if(or.getClass().getName().equals("ClientOrder")){

				ClientOrder order=(ClientOrder)or;					
				if(order.getBalance()>0)
					System.out.println(order.getClient()+"\n"); 
			}	
		}		  
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
      
 /**
 * Makes a deep copy of any Java object that is passed.
 */
 private static Object deepCopy(Object object) {
   try {
     ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
     ObjectOutputStream outputStrm = new ObjectOutputStream(outputStream);
     outputStrm.writeObject(object);
     ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
     ObjectInputStream objInputStream = new ObjectInputStream(inputStream);
     return objInputStream.readObject();
   }
   catch (Exception e) {
     e.printStackTrace();
     return null;
   }
 }

    }
