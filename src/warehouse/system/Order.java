

    import java.io.*;
    import java.util.ArrayList;
    import java.util.Iterator;
    import java.util.List;

    /**
     *
     * @author drtil
     */
    public class Order implements Serializable{
        
        private List<Product> products = new ArrayList<>();
        private double balance;
        private String id;
        private String name;
        private static final String ORDER_STRING = "O";

        public Order(){
            this.id = ORDER_STRING + (OrderIDServer.instance()).getId();
        }
        
        public void addProduct(Product product){
            this.products.add(product);
        }

            public void setBalance(Product product, int qty){
                this.balance += product.getPrice()*qty;
            }

        public double getBalance(){
            return this.balance;
        }

            public String getId() {
                return this.id;
            }
            
            public String getName() {
                return name;
            }

            public double makePayment(Order order, double amt){

                    this.balance-=amt;
                    if (this.balance<0){
                            this.balance+=amt;
                            System.out.println("Error. Paid more than due.\n");
                    }
                    return getBalance();
            }

        public Iterator getProducts(){
            return this.products.iterator();
        }

        public String toString(){
            String strProducts = "Order ID: " + this.getId() + "\n Balance: " + this.getBalance() + "\n Products: ";

            for(Product product : this.products){
                strProducts += "\n" + product;
            }        
            return strProducts;
        }

    Object getID() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    Object getOrderName() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    }