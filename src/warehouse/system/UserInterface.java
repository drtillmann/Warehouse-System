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
  
  //TODO - CHANGE TO WAREHOUSE
  private static Warehouse warehouse;
  
  private static final int EXIT = 0;
  private static final int ADD_CLIENT = 1;
  private static final int ADD_PRODUCT = 2;
  private static final int PLACE_ORDER = 3;
  //private static final int RETURN_BOOKS = 4;
  //private static final int RENEW_BOOKS = 5;
  //private static final int REMOVE_BOOKS = 6;
  //private static final int PLACE_HOLD = 7;
  //private static final int REMOVE_HOLD = 8;
  //private static final int PROCESS_HOLD = 9;
  private static final int GET_TRANSACTIONS = 10;
  private static final int SHOW_CLIENTS = 11;
  private static final int SHOW_PRODUCTS = 12;
  //private static final int SAVE = 13;
  //private static final int RETRIEVE = 14;
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
    System.out.println("Enter a number between 0 and 12 as explained below:");
    System.out.println(EXIT + " to Exit\n");
    System.out.println(ADD_CLIENT + " to add a client");
    System.out.println(ADD_PRODUCT + " to  add products");
    System.out.println(PLACE_ORDER + " to  place a client's order ");
    //System.out.println(RETURN_BOOKS + " to  return books ");
    //System.out.println(RENEW_BOOKS + " to  renew books ");
    //System.out.println(REMOVE_BOOKS + " to  remove books");
    //System.out.println(PLACE_HOLD + " to  place a hold on a book");
    //System.out.println(REMOVE_HOLD + " to  remove a hold on a book");
    //System.out.println(PROCESS_HOLD + " to  process holds");
    System.out.println(GET_TRANSACTIONS + " to  print transactions");
    System.out.println(SHOW_CLIENTS + " to  print members");
    System.out.println(SHOW_PRODUCTS + " to  print books");
    //System.out.println(SAVE + " to  save data");
    //System.out.println(RETRIEVE + " to  retrieve");
    System.out.println(HELP + " for help");
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
      String title = getToken("Enter Product name");
      String bookID = getToken("Enter id");
      String author = getToken("Enter author");
      result = warehouse.addProduct(title, author, bookID);
      if (result != null) {
        System.out.println(result);
      } else {
        System.out.println("Book could not be added");
      }
      if (!yesOrNo("Add more books?")) {
        break;
      }
    } while (true);
  }
  
  public void placeOrder() {
         System.out.println("Dummy Action");
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
	  Client member = (Client)(allClients.next());
          System.out.println(member.toString());
      }
  }

  /*
  public void returnBooks() {
      System.out.println("Dummy Action");
  }
  public void removeBooks() {
      System.out.println("Dummy Action");   
  }
  public void placeHold() {
      System.out.println("Dummy Action");   
  }
  public void removeHold() {
      System.out.println("Dummy Action");   
  }
  public void processHolds() {
      System.out.println("Dummy Action");   
  }
  */
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
        System.out.println("File doesnt exist; creating new library" );
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
        case PLACE_ORDER:       placeOrder();
                                break;
        /*
        case RETURN_BOOKS:      returnBooks();
                                break;
        case REMOVE_BOOKS:      removeBooks();
                                break;
        case RENEW_BOOKS:       renewBooks();
                                break;
        case PLACE_HOLD:        placeHold();
                                break;
        case REMOVE_HOLD:       removeHold();
                                break;
        case PROCESS_HOLD:      processHolds();
                                break;

        */
        case GET_TRANSACTIONS:  getTransactions();
                                break;
        /*
        case SAVE:              save();
                                break;
        case RETRIEVE:          retrieve();
                                break;
        */
        case SHOW_CLIENTS:	showClients();
                                break; 		
        case SHOW_PRODUCTS:	showProducts();
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

