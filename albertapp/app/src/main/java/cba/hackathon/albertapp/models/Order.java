package cba.hackathon.albertapp.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Order {

    @SerializedName("line_items")
    ArrayList<LineItem> lineItems;

    public Order() {
        lineItems = new ArrayList<>();
    }

    public static class LineItem {
        @SerializedName("product_id")
        public int productId;
        @SerializedName("quantity")
        public int quantity;
    }

    public class PaymentDetails {
        public String methodId;
        public String methodTitle;
        public boolean paid;
    }

    public void addLineItem(LineItem item){
        lineItems.add(item);
    }
}