package warehouse.system;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author drtil
 */

import java.util.*;
import java.text.*;
import java.io.*;


public class UserInterface {
  private static UserInterface userInterface;
  private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
  private static Warehouse warehouse;
  
  private static final int EXIT = 0;
  private static final int ADD_CLIENT = 1;
  private static final int ADD_MANUFACTURER = 2;
  private static final int ADD_PRODUCT = 3;
  private static final int UNASSIGN_PRODUCT = 4;
  private static final int SHOW_CLIENTS = 5;
  private static final int SHOW_PRODUCTS = 6;
  private static final int SHOW_MANUFACTURERS = 7;
  private static final int SHOW_MANUFACTURERS_PRODUCTS = 8;
  private static final int SHOW_PRODUCT_MANUFACTURERS = 9;
  private static final int PLACE_CLIENT_ORDER = 10;
  private static final int PLACE_MANUFACTURER_ORDER = 11;
  private static final int SHOW_WAITLISTED_ORDERS_BY_PRODUCTID = 12;
  private static final int SAVE = 13;
  private static final int RETRIEVE = 14;
  private static final int HELP = 15;
  
  private UserInterface() {
    if (yesOrNo("Look for saved data and  use it?")) {
      retrieve();
    } else {
      warehouse = Warehouse.instance();
    }
  }
  public static UserInterface instance() {
    if (userInterface == null) {
      return userInterface = new UserInterface();
    } else {
      return userInterface;
    }
  }
  
  public void print(String val){
      System.out.println(val);
  }
  
  public String getToken(String prompt) {
    do {
      try {
        System.out.println(prompt);
        String line = reader.readLine();
        StringTokenizer tokenizer = new StringTokenizer(line,"\n\r\f");
        if (tokenizer.hasMoreTokens()) {
          return tokenizer.nextToken();
        }
      } catch (IOException ioe) {
        System.exit(0);
      }
    } while (true);
  }
 
  private boolean yesOrNo(String prompt) {
    String more = getToken(prompt + " (Y|y)[es] or anything else for no");
    if (more.charAt(0) != 'y' && more.charAt(0) != 'Y') {
      return false;
    }
    return true;
  }
  
  
  public int getNumber(String prompt) {
    do {
      try {
        String item = getToken(prompt);
        Integer num = Integer.valueOf(item);
        return num.intValue();
      } catch (NumberFormatException nfe) {
        System.out.println("Please input a number ");
      }
    } while (true);
  }
  public Calendar getDate(String prompt) {
    do {
      try {
        Calendar date = new GregorianCalendar();
        String item = getToken(prompt);
        DateFormat df = SimpleDateFormat.getDateInstance(DateFormat.SHORT);
        date.setTime(df.parse(item));
        return date;
      } catch (Exception fe) {
        System.out.println("Please input a date as mm/dd/yy");
      }
    } while (true);
  }
  public int getCommand() {
    do {
      try {
        int value = Integer.parseInt(getToken("Enter command:" + HELP + " for help"));
        if (value >= EXIT && value <= HELP) {
          return value;
        }
      } catch (NumberFormatException nfe) {
        System.out.println("Enter a number");
      }
    } while (true);
  }

  public void help() {
    print("Enter a number between 0 and 12 as explained below:");
    print(EXIT + " to Exit\n");
    print(ADD_CLIENT + " to add a client");
    print(ADD_MANUFACTURER + " to add a manufacturer");
    print(ADD_PRODUCT + " to add products and assign them to a manufacturer");
    print(UNASSIGN_PRODUCT + " to unassign a product from a manufacturer");
    print(SHOW_CLIENTS + " to print clients");
    print(SHOW_PRODUCTS + " to print products");
    print(SHOW_MANUFACTURERS + " to print manufacturers");
    print(SHOW_MANUFACTURERS_PRODUCTS + " to print the specified manufacturer's products");
    print(SHOW_PRODUCT_MANUFACTURERS + " to print the specified product's manufacturers");
    print(PLACE_CLIENT_ORDER + " to place and process a client order");
    print( PLACE_MANUFACTURER_ORDER + " to place an order to a manufacturer");
    print(SHOW_WAITLISTED_ORDERS_BY_PRODUCTID + " to print a list of waitlisted orders containing a product id");
    print(SAVE + " to save data");
    print(RETRIEVE + " to retrieve");
    print(HELP + " for help");
  }

  public void addClient() {
    String name = getToken("Enter client name");
    String address = getToken("Enter address");
    String phone = getToken("Enter phone");
    
    Client result = warehouse.addClient(name, address, phone);
    if (result == null) {
      System.out.println("Could not add client");
    }
    System.out.println(result);
  }

  public void addProducts() {
    Product result;
    do {
      String name = getToken("Enter Product Name:");
      String manuID = getToken("Enter Manufacturer ID:");  
      String quantity = getToken("Enter Product Quantity:");
      String price = getToken("Enter Price");
      if(warehouse.manufacturerExists(manuID)){
        result = warehouse.addProduct(name, manuID, quantity, price);
        if (result != null) {
          assignProduct(result.getID(), manuID);
          System.out.println(result);
      } else {
        System.out.println("Product could not be added");
        }
      }
      if (!yesOrNo("Add more Products?")) {
        break;
      }
    } while (true);
  }
  
  public void addManufacturer(){
      String name = getToken("Enter Manufacturer Name: ");
      String id = getToken("Enter Manufacturer ID: ");
      Manufacturer result = warehouse.addManufacturer(name, id);
      if(result != null){
          System.out.println(result);
      }else{
          System.out.println("Manufacturer could not be added.");
      }
  }
  
  private void assignProduct(String pid, String mid){
      
      int result = warehouse.assignProduct(pid, mid);
      switch(result){
          case 1:
              print("Could not find the Product associated with ID: " + pid);
              break;
          case 2:
              print("Could not find the Manufacturer associated with ID: " + mid);
              break;
          case 3:
              print("Product added to to the Manufacturer");
              break;       
      }
  }
  
  public void unassignProduct(){
    String productID = getToken("Enter the Product ID: ");
    String manuID = getToken("Enter the Manufacturer's ID: ");
    if(!warehouse.manufacturerExists(manuID)){
        print("The Manufacturer ID " + manuID + " does not exist.");
    }else if(!warehouse.productExists(productID)){
        print("The Product ID " + productID + " does not exist.");
    }else{
        if(warehouse.unassignProduct(productID, manuID)){
              print("Product " + productID + " removed from Manufacturer " + manuID);
        }else{
            print("The Prooduct was not removed.");
        }
    }
  }
  
  
  public void placeClientOrder() {
         String clientID = null;
         String productID = null;
         String orderQty = null;
         List<String> orderedProductIDs = new ArrayList<>();
         List<String> orderedQtys = new ArrayList<>();
         Product validProduct;
         do{
               clientID = getToken("Enter the Client ID: ").trim();
            }while(warehouse.searchClient(clientID) == null);
         do{
            do{
                productID = getToken("Enter the Product ID: ").trim();
                validProduct = warehouse.searchProduct(productID);
                if(validProduct != null){
                    orderedProductIDs.add(productID);
                    orderQty = getToken("Enter the Order Qty of the Product: ").trim();
                    orderedQtys.add(orderQty);
                }
            }while(validProduct == null);
         }while(yesOrNo("Add Another Product to the Order?"));
         boolean result = warehouse.placeClientOrder(clientID, orderedProductIDs, orderedQtys);
         if(result){
             print("100% of the order was filled.");
         }else{
             print("Order Added to Waitlist List.");
         }
         print("Manufacturer order(s) automatically placed.");
  }
  
  public void placeManufacturerOrder(){
      String manuID = null;
      String productID = null;
      String orderQty = null;
      do{
          manuID = getToken("Enter the Manufacturer ID:");
      }while(!warehouse.manufacturerExists(manuID));
      
      do{
          productID = getToken("Enter the Product ID:");
      }while(warehouse.searchProduct(productID) == null && !warehouse.manufacturerContainsProduct(productID));
      orderQty = getToken("Enter Order Qty:");
      warehouse.placeManufacturerOrder(productID, orderQty);
  }
  
  public void showWaitlistedOrdersByProductID(){
      String productID;
      do{
        productID = getToken("Enter the Product ID: ");
      }while(warehouse.searchProduct(productID) == null);
      Iterator waitlists = warehouse.getWaitlistedOrdersByProductID(productID);
      while(waitlists.hasNext()){
          print(waitlists.next().toString());
      }
  }
  /*
  public void renewBooks() {
      System.out.println("Dummy Action");
  }
*/
  public void showProducts() {
      Iterator allProducts = warehouse.getProducts();
      while (allProducts.hasNext()){
	  Product product = (Product)(allProducts.next());
          System.out.println(product.toString());
      }
  }

  public void showClients() {
      Iterator allClients = warehouse.getClients();
      while (allClients.hasNext()){
	  Client client = (Client)(allClients.next());
          System.out.println(client.toString());
      }
  }
  
  public void showManufacturers(){
      Iterator allManus = warehouse.getManufacturers();
      while(allManus.hasNext()){
          Manufacturer manu = (Manufacturer) allManus.next();
          System.out.println(manu.toString());
      }
      
  }
  
  public void showManuProducts(){
      String mid = getToken("Enter the Manufacturer's ID to display all of their Procucts.");
      Iterator allManuProducts = warehouse.getManuProducts(mid);
      while(allManuProducts.hasNext()){
          Product prod = (Product) allManuProducts.next();
          print(prod.toString());
      }
  }
  
  public void showProductManufacturers(){
      String pid = getToken("Enter the Product ID to print its Manufacturers.");
      Iterator prodManufacturers = warehouse.getProductManufacturers(pid);
      while(prodManufacturers.hasNext()){
          Manufacturer manu = (Manufacturer) prodManufacturers.next();
          print(manu.toString());
      }
  }


  public void getTransactions() {
      System.out.println("Dummy Action");   
  }
  private void save() {
    if (warehouse.save()) {
      System.out.println(" The warehouse has been successfully saved in the file WarehouseData \n" );
    } else {
      System.out.println(" There has been an error in saving \n" );
    }
  }
  private void retrieve() {
    try {
      Warehouse tempWarehouse = Warehouse.retrieve();
      if (tempWarehouse != null) {
        System.out.println(" The warehouse has been successfully retrieved from the file WarehouseData \n" );
        warehouse = tempWarehouse;
      } else {
        System.out.println("File doesnt exist; creating new warehouse" );
        warehouse = Warehouse.instance();
      }
    } catch(Exception cnfe) {
      cnfe.printStackTrace();
    }
  }
  public void process() {
    int command;
    help();
    while ((command = getCommand()) != EXIT) {
      switch (command) {
        case ADD_CLIENT:        addClient();
                                break;
        case ADD_PRODUCT:       addProducts();
                                break;
        case ADD_MANUFACTURER:  addManufacturer();
                                break;                      
        case UNASSIGN_PRODUCT:  unassignProduct();
                                break;
        case SAVE:              save();
                                break;
        case RETRIEVE:          retrieve();
                                break;
        case SHOW_CLIENTS:	print("(Client List)"); showClients();
                                break; 		
        case SHOW_PRODUCTS:	print("(Inventory)"); showProducts();
                                break;
        case SHOW_MANUFACTURERS: print("(Manufacturer List)"); showManufacturers();
                                break;
        case SHOW_MANUFACTURERS_PRODUCTS: print("(Manufacturer's Products)"); showManuProducts();
                                break;
        case SHOW_PRODUCT_MANUFACTURERS: print("(Product's Manufacturers)"); showProductManufacturers();
                                break;
        case SHOW_WAITLISTED_ORDERS_BY_PRODUCTID: print("(Waitlisted Orders By Product ID)"); showWaitlistedOrdersByProductID();
                                break;
        case PLACE_CLIENT_ORDER: placeClientOrder();
                                break;
        case PLACE_MANUFACTURER_ORDER: placeManufacturerOrder();  
                                break;
        case HELP:              help();
                                break;
      }
    }
  }
  public static void main(String[] s) {
    UserInterface.instance().process();
  }
}

