/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package warehouse.system;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;

/**
 *
 * @author drtil
 */
public class ManagerState extends WarehouseState{
    
    private static final int EXIT = 0;
    private static final int ASSIGN_PRODUCT = 1;
    private static final int UNASSIGN_PRODUCT = 2;
    private static final int HELP = 3;
    private static ManagerState managerState;
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static Warehouse warehouse;
    
    private ManagerState(){
        warehouse = Warehouse.instance();
    }
    
    public static ManagerState instance(){
        if(managerState == null){
            return managerState = new ManagerState();
        }else{
            return managerState;
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
        print(ASSIGN_PRODUCT + " to assign a product to a manufacturer.");
        print(UNASSIGN_PRODUCT + " to unassign a product from a manufacturer.");
        print(HELP + " for help");
    }
    
    public void process() {
        int command;
        help();
        while ((command = getCommand()) != EXIT) {
          switch (command) {

            case ASSIGN_PRODUCT:    assignProduct();
                                    break;
            case UNASSIGN_PRODUCT:  unassignProduct();
                                    break;
            case HELP:              help();
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
        if ((WarehouseContext.instance()).getLogin() == WarehouseContext.IsManager)
        { //stem.out.println(" going to clerk \n ");
          (WarehouseContext.instance()).changeState(1); // exit with a code 1
        }
        else if (WarehouseContext.instance().getLogin() == WarehouseContext.IsUser)
        {  //stem.out.println(" going to login \n");
         (WarehouseContext.instance()).changeState(0); // exit with a code 2
        }
        else (WarehouseContext.instance()).changeState(2); // exit code 2, indicates error
    }
    
    public void assignProduct(){
        String productID;
        String manufacturerID;
        do{
            productID = getToken("Enter the Product ID: ");
        }while(!warehouse.productExists(productID));
        
        do{
            manufacturerID = getToken("Enter the Manufacturer ID: ");
        }while(!warehouse.manufacturerExists(manufacturerID));
            
        int result = warehouse.assignProduct(productID, manufacturerID);
        switch(result){
            case 1:
                print("Could not find the Product associated with ID: " + productID);
                break;
            case 2:
                print("Could not find the Manufacturer associated with ID: " + manufacturerID);
                break;
            case 3:
                print("Product added to to the Manufacturer");
                break;       
        }
    }
    
    public void unassignProduct(){
        String productID;
        String manuID;
        do{
            productID = getToken("Enter the Product ID: ");
        }while(!warehouse.productExists(productID));
        
        do{
            manuID = getToken("Enter the Manufacturer's ID: ");
        }while(!warehouse.manufacturerExists(manuID));
        
        if(warehouse.unassignProduct(productID, manuID)){
            print("Product " + productID + " removed from Manufacturer " + manuID);
        }else{
            print("The Prooduct was not removed.");
        }
        
    }
}
