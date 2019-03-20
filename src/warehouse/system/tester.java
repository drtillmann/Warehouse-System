package warehouse.system;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 *
 * @author drtil
 */
public class tester {
    
    public static void print(String val){
        System.out.println(val);
    }
    
    public static void print(Iterator iterator){
        if(iterator == null){
            print("iterator is null");
        }else{
            while(iterator.hasNext()){
                print(iterator.next().toString());
            }
        }
    }
    
    
    
    
    
    
    
    
    public static void main(String[] args){
        
        Warehouse warehouse = Warehouse.instance();
        
        String[] cNames = {"Jordan", "Daniel"};
        String[] cAddresses = {"a street", "an avenue"};
        String[] cPhones = {"320#######", "612#######"};
        Client client = null;
        
        String[] mNames = {"HP", "Dell"};
        String[] mIDs = {"101", "102"};
        Manufacturer manu = null;
        
        String[] pNames = {"Laptop", "Mac"};
        String[] idOfManus = {"101", "102"};
        String[] qtyOfProducts = {"5", "10"};
        String[] pPrices = {"999.99", "1499.99"};
        Product product = null;
        
        String clientID;
        List<String> pIDs = new ArrayList<>();
        List<String> pQtys = new ArrayList<>();
        pQtys.add("7");
        pQtys.add("9");
        
        
        /**
         * Add Clients here
         */
        print("Adding Clients.");
        for(int i = 0; i < 2; i++){
            client = warehouse.addClient(cNames[i], cAddresses[i], cPhones[i]);
            if(client != null){
                print("Client added: " + client);
            }else{
                print("Unable to add Client.");
            } 
        }                  
        clientID = client.getId();
        
        print("");
        print("*** SHOW ALL CLIENTS ***");
        Iterator allClients = warehouse.getClients();
        print(allClients);
        print("");
        
        /**
         * Add Manufacturers here
         */
        print("Adding Manufacturers.");
        for(int i = 0; i < 2; i++){
            manu = warehouse.addManufacturer(mNames[i], mIDs[i]);
            if(manu != null){
                
                print("Manufacturer added: " + manu);
            }else{
                print("Unable to add the Manufacturer.");
            }
        }    
        
        print("");
        print("*** SHOW ALL MANUFACTURERS ***");
        Iterator allManufacturers = warehouse.getManufacturers();
        print(allManufacturers);
        print("");
        print("TEST FOR NON-EXISTING MANUFACTURER: Manufacturer ID 999 does not exist: " + !warehouse.manufacturerExists("999"));
        print("TEST FOR EXISTING MANUFACTURER: Manufacturer ID 101 does exist: " + warehouse.manufacturerExists("101"));
        print("");
      
        /**
         * Add Products here
         */
        print("Adding Products.");
        for(int i = 0; i < 2; i++){
            product = warehouse.addProduct(pNames[i], idOfManus[i], qtyOfProducts[i], pPrices[i]);
            pIDs.add(product.getID());
            int result = warehouse.assignProduct(product.getID(), idOfManus[i]);
            if(product != null && result == 3){
                print("Product added and assigned to a manufacturer: " + product);
            }else{
                print("Unable to add the product.");
            }
        }
        
        print("*** SHOW MANUFACTURER'S PRODUCTS ***");
        Iterator manusProducts = warehouse.getManuProducts("102");
        print(manusProducts);
        print("");

        print("*** SHOW ALL PRODUCTS ***");
        Iterator allProducts = warehouse.getProducts();
        print(allProducts);
        print("");
        
        print("TEST FOR NON-EXISTING PRODUCT: Product ID P10 does not exist: " + !warehouse.productExists("P10"));
        print("TEST FOR EXISTING PRODUCT: Product ID P1 does exist: " + warehouse.productExists("P1"));
        print("");
        
        print("*** TEST PLACING AN ORDER ***");
        
        boolean orderFulfilled = warehouse.placeClientOrder(clientID, pIDs, pQtys);
        
        if(!orderFulfilled){
            print("Ordered Product(s) added to Waitlist");
        }else{
            print("Order Fulfilled.");
        }
        print("*** Orders ***\n");
        Iterator orders = warehouse.getOrders();
        print(orders);
        print("*** Waitlists ***\n");
        Iterator wls = warehouse.getWaitlists();
        print(wls);
        
        print("*** TEST UNASSIGN PRODUCT ***");
        boolean unassigned = false;
        try{
            unassigned = warehouse.unassignProduct("P2", "102");
        }catch(Exception e){
            print(e.getMessage()); 
        }
        if(unassigned){
            warehouse.removeProduct("P2");
            print("Product unassigned and removed.");
        }else{
            print("Unable to unassign product from manufacturer.");
        }
        
    }
    
}
