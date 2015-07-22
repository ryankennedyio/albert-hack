package cba.hackathon.albertapp.models;

import java.util.ArrayList;

/**
 * Created by Konrad on 22/07/2015.
 */
public class Cart {
    private ArrayList<Item> itemList;

    public void addProduct(Product product) {
        boolean isContained = false;
        for (Item item : itemList) {
            if (item.getProductName() == product.title) {
                item.increment();
                isContained = true;
            }
        }
        if (!isContained) {
            Item item = new Item(product, 1);
            itemList.add(item);
        }
    }

    public void removeProduct(Product product) {
        for (Item item : itemList) {
            if (item.getProductName() == product.title) {
                item.decrement();
            }
        }
    }

    public void wipeItems() {
        itemList.clear();
    }

    public ArrayList<Item> getProductList() {
        return itemList;
    }
}
