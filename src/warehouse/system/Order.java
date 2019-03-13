    /*
     * To change this license header, choose License Headers in Project Properties.
     * To change this template file, choose Tools | Templates
     * and open the template in the editor.
     */
    package warehouse.system;


    import java.io.*;
    import java.util.ArrayList;
    import java.util.Iterator;
    import java.util.List;

    /**
     *
     * @author drtil
     */
    public class Order implements Serializable{
        private Client client;
        private List<Product> products = new ArrayList<>();
        private double balance;
        private String id;
        private static final String ORDER_STRING = "O";

        public Order(){

        }

        public Order(Client client){
            this.id = ORDER_STRING + (OrderIDServer.instance()).getId();
            this.setClient(client);    
        }

        public void setClient(Client client){
            this.client = client;
        }

        public Client getClient(){
            return this.client;
        }

        public void addProduct(Product product, int qty){
            product.setQty(qty);
            this.products.add(product);
            this.setBalance(product, qty);
        }

            private void setBalance(Product product, int qty){
                this.balance += product.getPrice()*qty;
            }

        public double getBalance(){
            return this.balance;
        }

            public String getId() {
                return this.id;
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
            String strOrder = "Client: " + client + "\n Products: ";

            for(Product product : this.products){
                strOrder += product + "\n";
            }        
            return strOrder;
        }
    }