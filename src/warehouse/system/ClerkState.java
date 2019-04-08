/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.*;
import java.text.*;
import java.io.*;
public class ClerkState extends WarehouseState {
  private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
  private static Warehouse warehouse;
  private WarehouseContext context;
  private static ClerkState instance;
  private static final int EXIT = 0;
  private static final int ADD_CLIENT = 1;
  private static final int ADD_PRODUCT = 2;
  private static final int SHOW_CLIENTS = 3;
  private static final int SHOW_PRODUCTS = 4;
  private static final int SHOW_MANUFACTURERS = 5;
  private static final int SHOW_MANUFACTURERS_PRODUCTS = 6;
  private static final int SHOW_PRODUCT_MANUFACTURERS = 7;
  private static final int PLACE_MANUFACTURER_ORDER = 8;	
  private static final int SHOW_WAITLISTED_ORDERS_BY_PRODUCTID = 9;
  private static final int ACCEPT_CLIENT_PAYMENT = 10;
  private static final int OUTSTANDING_BALANCE = 11;
  private static final int WAITLIST_ORDER_CLIENT = 12;
  private static final int ORDERS_PLACED_WITH_MANU = 13;
  private static final int RECEIVE_SHIPMENT = 14;
  private static final int SAVE = 15;
  private static final int RETRIEVE = 16;
  private static final int CLIENTMENU = 17;
  private static final int HELP = 18;
  private ClerkState() {
      super();
      warehouse = Warehouse.instance();
      //context = WarehouseContext.instance();
  }

  public static ClerkState instance() {
    if (instance == null) {
      instance = new ClerkState();
    }
    return instance;
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
    print("Enter a number between 0 and 18 as explained below:");
    print(EXIT + " to Exit\n");
    print(ADD_CLIENT + " to add a client");
    print(ADD_PRODUCT + " to add products and assign them to a manufacturer");
    print(SHOW_CLIENTS + " to print clients");
    print(SHOW_PRODUCTS + " to print products");
    print(SHOW_MANUFACTURERS + " to print manufacturers");
    print(SHOW_MANUFACTURERS_PRODUCTS + " to print the specified manufacturer's products");
    print(SHOW_PRODUCT_MANUFACTURERS + " to print the specified product's manufacturers");
    print(PLACE_MANUFACTURER_ORDER + " to place an order to a manufacturer");
    print(SHOW_WAITLISTED_ORDERS_BY_PRODUCTID + " to print a list of waitlisted orders containing a product id");
    print(ACCEPT_CLIENT_PAYMENT + " to process a client payment on an order");
    print(OUTSTANDING_BALANCE + " to list all clients with an outstanding balance.");
    print(WAITLIST_ORDER_CLIENT + " to get a list of waitlisted orders for a client.");
    print(ORDERS_PLACED_WITH_MANU + " to get a list of orders placed with a manufacturer.");
    print(RECEIVE_SHIPMENT + " to receive manufacturer shipment");
    print(SAVE + " to save data");
    print(RETRIEVE + " to retrieve");
    print(CLIENTMENU + " to become a client");
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
          warehouse.assignProduct(result.getID(), manuID);
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
 
   public void showClients() {
      Iterator allClients = warehouse.getClients();
      while (allClients.hasNext()){
	  Client client = (Client)(allClients.next());
          System.out.println(client.toString());
      }
  }
  
   public void showProducts() {
      Iterator allProducts = warehouse.getProducts();
      while (allProducts.hasNext()){
	  Product product = (Product)(allProducts.next());
          System.out.println(product.toString());
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
  
  public void acceptPayment(){
	  Scanner s=new Scanner(System.in);
	 
	  String oID=getToken("Enter in the OrderID of the order you would like to make a payment on.\n");
	  System.out.println("Enter in the amount you would like to pay.\n");
	  double amt;
	  amt=s.nextDouble();
	  warehouse.makePayment(oID, amt);
  }
  
  public void clientsWithOutstandingBalance(){
	  warehouse.outstandingBalance();
  }
  
  public void waitlistedOrdersForClient(){
	  String cid=getToken("Please enter the cID of the client to see their waitlisted orders.");
	  warehouse.getWaitlistedOrdersByClientID(cid);

  }
  
   public void ordersWithManu(){
	  String mid=getToken("Please enter the mID of the manufacturer to see their orders");
	  warehouse.getManuOrders(mid);
  }
  
  public void receiveOrder(Order order){
      warehouse.receiveOrder(order);
      print("Shipment successfully received.");
  }
  
  public void receiveShipment(){
      String oid = getToken("Enter the Order ID to print its details.");
      Order temp = warehouse.getManufacturerOrder(oid);
      if(temp != null){
          print(temp.toString());
          receiveOrder(temp);
      }
      else{
        print("Order was not found.");
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
  
  
  private void save() {
    if (warehouse.save()) {
      System.out.println(" The warehouse has been successfully saved in the file WarehouseData \n" );
    } else {
      System.out.println(" There has been an error in saving \n" );
    }
  }
  
  

  public void clientMenu()
  {
    String clerkID = getToken("Please input the user id: ");
    if (Warehouse.instance().searchMembership(clerkID) != null){
      (WarehouseContext.instance()).setClient(clerkID);      
      (WarehouseContext.instance()).changeState(1);
    }
    else 
      System.out.println("Invalid user id."); 
  }

  public void logout()
  {
    (WarehouseContext.instance()).changeState(4); // exit with a code 0
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
        case PLACE_MANUFACTURER_ORDER: placeManufacturerOrder();  
                                break;
        case HELP:              help();
                                break;
        case RECEIVE_SHIPMENT:  receiveShipment();
                                break;
        case ACCEPT_CLIENT_PAYMENT:  acceptPayment();
                                break;
        case OUTSTANDING_BALANCE: clientsWithOutstandingBalance();
								break;
		case ORDERS_PLACED_WITH_MANU : ordersWithManu();
								break;
		case WAITLIST_ORDER_CLIENT: waitlistedOrdersForClient();
								break;
		case CLIENTMENU:          clientMenu();
                                break;
      }
    }
    logout();
  }
  public void run() {
    process();
  }
}
