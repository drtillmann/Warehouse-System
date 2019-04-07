/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warehouse.system;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.StringTokenizer;

/**
 *
 * @author drtil
 */
public class Clientstate extends WarehouseState{
    
    private static final int EXIT = 0;
    private static final int PLACE_CLIENT_ORDER = 1;
    private static final int HELP = 2;
    private static Clientstate clientState;
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static Warehouse warehouse;
    
    private Clientstate(){
        warehouse = Warehouse.instance();
    }
    
    public static Clientstate instance(){
        if(clientState == null){
            return clientState = new Clientstate();
        }else{
            return clientState;
        }
    }
    
    private static void print(Object val){
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
        print("Enter a number between " + EXIT + " and " + HELP + " as explained below:");
        print(EXIT + " to Exit\n");
        print(PLACE_CLIENT_ORDER + " to place and process a client order");//client
        print(HELP + " for help");
    }
    
    public void process() {
        int command;
        help();
        while ((command = getCommand()) != EXIT) {
          switch (command) {

            case PLACE_CLIENT_ORDER:    placeClientOrder();
                                        break;
            case HELP:                  help();
                                        break;
          }
        }
        logout();
    }
    
    @Override
    public void run() {
        process();
    }
    
    public void logout(){
        if ((WarehouseContext.instance()).getLogin() == WarehouseContext.IsClient)
        { //stem.out.println(" going to clerk \n ");
            (WarehouseContext.instance()).changeState(4); // exit with a code 4
        }else{ 
            (WarehouseContext.instance()).changeState(2); // exit code 2, indicates error
        }
        /*
        else if (WarehouseContext.instance().getLogin() == WarehouseContext.IsUser)
           {  //stem.out.println(" going to login \n");
            (WarehouseContext.instance()).changeState(0); // exit with a code 2
           }
        else (WarehouseContext.instance()).changeState(2); // exit code 2, indicates error
        */
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
         
  }
    
}
