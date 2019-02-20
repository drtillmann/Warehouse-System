package LibraryCode.booktest;

public class Tester {
  
  public static void main(String[] s) {
     Book b1 = new Book("qq", "ww", "b1");
     Book b2 = new Book("ee", "rr", "b2");
     Member m1 = new Member("m1"); 
     Member m2 = new Member("m2");
     System.out.println(b1.getBorrower() + " should be null");
     b1.issue(m1); 
     System.out.println(b1.getBorrower() + " should be m1");
     b1.issue(m2); // try issuing to someone else
     System.out.println(b1.getBorrower() + " still issue to m1");
     System.out.println(b1.getDueDate() + " check due date as per Business Rule"); 
     System.out.println(b1.returnBook()); 
     System.out.println(b1.getBorrower() + " should be null");
     System.out.println(b1.getDueDate() + " should be null");
  }
}
