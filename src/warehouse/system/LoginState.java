import java.util.*;
import java.text.*;
import java.io.*;
public class LoginState extends WarehouseState{
  private static final int CLIENT_LOGIN = 0;
  private static final int SALESCLERK_LOGIN = 1;
  private static final int MANAGER_LOGIN = 2;
  private static final int EXIT = 3;
  private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));  
  private WarehouseContext context;
  private static LoginState instance;
  
  Security security=new Security();
  private LoginState() {
      super();
     // context = LibContext.instance();
  }

  public static LoginState instance() {
    if (instance == null) {
      instance = new LoginState();
    }
    return instance;
  }

  public int getCommand() {
    do {
      try {
        int value = Integer.parseInt(getToken("Enter command:" ));
        if (value <= EXIT && value >= CLIENT_LOGIN) {
          return value;
        }
      } catch (NumberFormatException nfe) {
        System.out.println("Enter a number");
      }
    } while (true);
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



  private void client(){
    String clientID = getToken("Please input the user id: ");
    if (Warehouse.instance().searchMembership(clientID) != null){
	  String clientPass = getToken("Please input the password: ");
	  
	  while(security.verifyPassword(clientID, clientPass) == false){
		  clientPass = getToken("Incorrect password, please try again: ");
	  }
	  
      (WarehouseContext.instance()).setLogin(WarehouseContext.IsClient);
      (WarehouseContext.instance()).setClient(clientID);      
      (WarehouseContext.instance()).changeState(1);
    }
    else 
      System.out.println("Invalid user id.");
  } 
  
  private void clerk(){
	
	String clerkID = getToken("Please input the salesclerk ID: ");  
	
	while(clerkID.equals("salesclerk") != true){
		clerkID = getToken("Incorrect salescrek ID, please try again: ");
	}
	
	String clerkPass = getToken("Please input the salesclerk password: ");  
	
	while(security.verifyPassword(clerkID, clerkPass) == false){
		clerkPass = getToken("Incorrect password, please try again: ");
	}
	
	  
    (WarehouseContext.instance()).setLogin(WarehouseContext.IsClerk);
    (WarehouseContext.instance()).changeState(0);
  }
  
  private void manager(){

	String managerID = getToken("Please input the manager ID: ");  
	
	while(managerID.equals("manager") != true){
		managerID = getToken("Incorrect manager ID, please try again: ");
	}
	
	String managerPass = getToken("Please input the manager password: ");  
	
	while(security.verifyPassword(managerID, managerPass) == false){
		managerPass = getToken("Incorrect password, please try again: ");
	}

    (WarehouseContext.instance()).setLogin(WarehouseContext.IsManager);    
    (WarehouseContext.instance()).changeState(2);
  }
  

  public void process() {
    int command;
    System.out.println("Please input 0 to login as Client\n"+ 
                        "input 1 to login as SalesClerk\n" +
                        "input 2 to login as Manager\n" +
                        "input 3 to exit the system\n");     
    while ((command = getCommand()) != EXIT) {

      switch (command) {
        case CLIENT_LOGIN:        client();
                                break;
        case SALESCLERK_LOGIN:       clerk();
                                break;
        case MANAGER_LOGIN:       manager();
                                break;


        default:                System.out.println("Invalid choice");
                                
      }
    System.out.println("Please input 0 to login as Client\n"+ 
                        "input 1 to login as SalesClerk\n" +
                        "input 2 to login as Manager\n" +
                        "input 3 to exit the system\n"); 
    }
    (WarehouseContext.instance()).changeState(3);
  }

  public void run() {
	  
    process();
  }
}
