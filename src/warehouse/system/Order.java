    /*
     * To change this license header, choose License Headers in Project Properties.
     * To change this template file, choose Tools | Templates
     * and open the template in the editor.
     */
//    package warehouse.system;


    import java.io.*;
    import java.util.ArrayList;
    import java.util.Iterator;
    import java.util.List;

    /**
     *
     * @author drtil
     */
    public class Order implements Serializable{
        private static final long serialVersionUID = 1L;
        private List<Product> products = new ArrayList<>();
        private double balance;
        private String id;
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
        
			public void setId(String id){
				this.id=id;
			}
        
            public String getId() {
                return this.id;
            }
            
            public List<Product> getProductList(){
                return this.products;
            }

            public double makePayment(double amt){

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

         // Within Order
        public Product getProduct(){
            return this.products.get(0);
        }
        
        public Boolean isManufacturerOrder(){
            return this instanceof ManufacturerOrder;
        }
        
        public void clearProductQty(){
             for (Iterator iterator = getProducts(); iterator.hasNext(); ) {
                Product product = (Product) iterator.next();
                product.setQty(0);
          }
        }
        //
        
        public String toString(){
            String strProducts = "Order ID: " + this.getId() + "\n Balance: " + this.getBalance() + "\n Products: ";

            for(Product product : this.products){
                strProducts += "\n" + product;
            }        
            return strProducts;
        }
    }
