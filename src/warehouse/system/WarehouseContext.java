import java.util.*;
import java.text.*;
import java.io.*;
public class WarehouseContext {
  
  private int currentState;
  private static Warehouse warehouse;
  private static WarehouseContext context;
  private int currentClient;
  private String userID;
  private BufferedReader reader = new BufferedReader(new 
                                      InputStreamReader(System.in));
  public static final int IsClerk = 0;
  public static final int IsClient = 1;
  public static final int IsManager = 3;
  private boolean wasClerk = false;
  private boolean wasManager = false;
  private WarehouseState[] states;
  private int[][] nextState;

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

  public void setLogin(int code)
  {currentClient = code;}

  public void setClient(String uID)
  { userID = uID;}

  public int getLogin()
  { return currentClient;}

  public String getClient()
  { return userID;}

  private WarehouseContext() { //constructor
    if (yesOrNo("Look for saved data and  use it?")) {
      retrieve();
    } else {
      warehouse = Warehouse.instance();
    }
    // set up the FSM and transition table;
    states = new WarehouseState[4];
    states[0] = ClerkState.instance();
    states[1] = ClientState.instance(); 
    states[2]=  LoginState.instance();
	states[3] = ManagerState.instance();
    nextState = new int[4][7];
	//				ClerkStatelogin   Clientstatelogin   managerstatelogin   exit   logout   wasClerkLogout   wasManagerLogout
	// ClerkState          0                  1                  -2           -2      2            -2               3                 
	// ClientState         -2                 1                  -2           -2      2             1               0
	// LoginState          0                  1                   3           -1     -2            -2              -2
	// ManagerState        0                  0                   3           -2      2            -2              -2
	
    nextState[0][0] = 0;nextState[0][1] = 1;nextState[0][2] = -2;nextState[0][3] = -2;nextState[0][4] = 2;nextState[0][5] = -2;nextState[0][6] = 3;
    nextState[1][0] = -2;nextState[1][1] = 1;nextState[1][2] = -2;nextState[1][3] = -2;nextState[1][4] = 2;nextState[1][5] = 0;nextState[1][6] = 0;
    nextState[2][0] = 0;nextState[2][1] = 1;nextState[2][2] = 3;nextState[2][3] = -1;nextState[2][4] = -2;nextState[2][5] = -2;nextState[2][6] = -2;
	nextState[3][0] = 0;nextState[3][1] = 0;nextState[3][2] = 3;nextState[3][3] = -2;nextState[3][4] = 2;nextState[3][5] = -2;nextState[3][6] = -2;
    currentState = 2;
  }

  public void changeState(int transition)
  {
	 
	if (currentState == 0 && nextState[currentState][transition] == 1)
		wasClerk = true;
	if (currentState == 3 && nextState[currentState][transition] == 0)
		wasManager = true;
	
	//To change from a regular logout to a logout to clerk
	if (wasClerk && transition == 4)
	{
		transition = 5;
		wasClerk = false;
	}
	
	if (wasManager && transition == 4)
	{
		transition  = 6;
		wasManager = false;
	}
	
    //System.out.println("current state " + currentState + " \n \n ");
    currentState = nextState[currentState][transition];
    if (currentState == -2) 
      {System.out.println("Error has occurred"); terminate();}
    if (currentState == -1) { System.out.println("here\n");
      terminate();}
    //System.out.println("current state " + currentState + " \n \n ");
	
    states[currentState].run();
  }

  private void terminate()
  {
   if (yesOrNo("Save data?")) {
      if (warehouse.save()) {
         System.out.println(" The warehouse has been successfully saved in the file WarehouseData \n" );
       } else {
         System.out.println(" There has been an error in saving \n" );
       }
     }
   System.out.println(" Goodbye \n "); System.exit(0);
  }

  public static WarehouseContext instance() {
    if (context == null) {
      context = new WarehouseContext();
    }
    return context;
  }

  public void process(){
    states[currentState].run();
  }
  
  public static void main (String[] args){
    WarehouseContext.instance().process();
     
  }


}
