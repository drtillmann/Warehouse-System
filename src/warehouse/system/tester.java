/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warehouse.system;

import java.util.Iterator;
import java.util.Scanner;

/**
 *
 * @author drtil
 */
public class tester {
    
    public static void print(String val){
        System.out.println(val);
    }
    
    public static void print(Iterator iterator){
        while(iterator.hasNext()){
            print(iterator.next().toString());
        }
    }
    
    public static void main(String[] args){
        
        Warehouse warehouse = Warehouse.instance();
        
        boolean addC = true, addP = true, addM = true;
        
        String[] cNames = {"Jordan", "Daniel"};
        String[] cAddresses = {"a street", "an avenue"};
        String[] cPhones = {"320#######", "612#######"};
        Client client = null;
        
        String[] mNames = {"HP", "Dell"};
        String[] mIDs = {"101", "102"};
        Manufacturer manu = null;
        
        String[] pNames = {"Laptop", "Mac"};
        String[] idOfManus = {"101", "102"};
        double[] pPrices = {999.99, 1499.99};
        Product product = null;
        
        for(int i = 0; i < 2; i++){
            client = warehouse.addClient(cNames[i], cAddresses[i], cPhones[i]);
            if(client != null){
                print("Client added: " + client);
            }else{
                print("Unable to add Client.");
            } 
        }                  
        
        print("");
        print("*** SHOW ALL CLIENTS ***");
        Iterator allClients = warehouse.getClients();
        print(allClients);
        print("");
        
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
        print("");
      
        
        for(int i = 0; i < 2; i++){
            product = warehouse.addProduct(pNames[i], idOfManus[i], pPrices[i]);
            if(product != null){
                print("Product added: " + product);
            }else{
                print("Unable to add the product.");
            }
        }    

        print("");
        print("*** SHOW ALL PRODUCTS ***");
        Iterator allProducts = warehouse.getProducts();
        print(allProducts);
        print("");
        
        print("TEST FOR NON-EXISTING PRODUCT: Product ID P10 does not exist: " + !warehouse.productExists("P10"));
        print("");
        print("TEST FOR EXISTING PRODUCT: Product ID P1 does exist: " + warehouse.productExists("P1"));
        print("");
        
        
    }
    
}
