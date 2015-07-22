package cba.hackathon.albertapp.models;

import java.util.ArrayList;

public class Order {

    ArrayList<Product> lineItems;

    public class PaymentDetails {
        public String methodId;
        public String methodTitle;
        public boolean paid;
    }
}

/** CREATING AN ORDER
 * http://woothemes.github.io/woocommerce-rest-api-docs/#create-an-order
 */
