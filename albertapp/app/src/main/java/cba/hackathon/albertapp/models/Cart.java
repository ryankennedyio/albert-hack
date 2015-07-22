package cba.hackathon.albertapp.models;

import java.util.ArrayList;

/**
 * Created by Konrad on 22/07/2015.
 */
public class Cart {
    private ArrayList<Item> itemList;

    private float mTotalPrice;

    public Cart() {
        mTotalPrice = 0.0f;
    }

    public void addProduct(Product product) {
        boolean isContained = false;
        for (Item item : itemList) {
            if (item.getProductName() == product.title) {
                item.increment();
                isContained = true;
                mTotalPrice += item.getPrice();
            }
        }
        if (!isContained) {
            Item item = new Item(product, 1);
            itemList.add(item);
            mTotalPrice += item.getPrice();
        }
    }

    public void removeProduct(Product product) {
        for (Item item : itemList) {
            if (item.getProductName() == product.title) {
                item.decrement();
                mTotalPrice -= item.getPrice();
            }
        }
    }

    public void removeAllOfProduct(Product product) {
        for (Item item : itemList) {
            if (item.getProductName() == product.title) {
                mTotalPrice -= item.getTotalPrice();
                item.resetCount();
            }
        }
    }

    public float getTotalPrice() { return mTotalPrice; }

    public void wipeItems() {
        itemList.clear();
    }

    public ArrayList<Item> getProductList() {
        return itemList;
    }

}
