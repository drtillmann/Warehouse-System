package warehouse.system;

import java.util.*;

public class ClientTester {
  
  public static void main(String[] s) {
	 ClientList l1 = new ClientList();
     Client c1 = new Client("Jordan", "54321", "12345");
     Client c2 = new Client("Dan", "456", "11123");
     
     System.out.println(c1.getId());
     System.out.println(c2.getId());
     
     l1.insertClient(c1);
     l1.insertClient(c2);
     
     Iterator iterator = l1.getClients();
     
     while (iterator.hasNext()) {
            System.out.print(iterator.next() + " "); 
            System.out.println();
		}
     
  }
}
