package warehouse.system;


import java.util.*;
import java.text.*;
import java.io.*;
public class ClientTester {
	
  public static Client addClient (String cName, String cAddr, String cPhone){
	  Client client= new Client(cName, cAddr, cPhone);
	  return client;
  }
  
  public static void main(String[] s) {
	 ClientList l1 = new ClientList();     
     Scanner scanner = new Scanner(System.in);
     
     boolean loop=true;
     
     while(loop){
     	 System.out.print("Enter new client's name: ");
		 String cName = scanner.next();

		 System.out.print("Enter new client's address: ");
		 String cAddr = scanner.next();

		 System.out.print("Enter new client's phone number: ");
		 String cPhone = scanner.next();	

		 Client client=addClient(cName, cAddr, cPhone);
		 l1.insertClient(client);
		 
		 System.out.print(client.getName() + " has been sucessfully added with a client ID of " + client.getId());
		 
		 System.out.print("\n\nIf you would like to stop adding clients, type N/n. Else, type any other key to continue adding clients.\n");		 
		 char Answer = scanner.next().charAt(0);
		 
		 if (Answer=='n' || Answer=='N')
			loop=false;
			
		 else if (Answer!='n' || Answer!='N')
			continue;		 
	}
	 
     
     System.out.print("\nHere is a printed listing of all client's and their attributes in the ClientList:\n ");
     Iterator iterator = l1.getClients();
     
     while (iterator.hasNext()) {
            System.out.print(iterator.next() + " "); 
            System.out.println();
		}
     
  }
}
