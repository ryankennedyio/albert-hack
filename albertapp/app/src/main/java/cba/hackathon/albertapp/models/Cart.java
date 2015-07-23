package cba.hackathon.albertapp.models;

import java.util.ArrayList;

/**
 * Created by Konrad on 22/07/2015.
 */
public class Cart {
    private ArrayList<Item> mItemList;
    private float mTotalPrice;

    public Cart() {
        mItemList = new ArrayList<>();
        mTotalPrice = 0.0f;
    }

    public void addProduct(Product product) {
        boolean isContained = false;
        for (Item item : mItemList) {
            if (item.getProductName() == product.title) {
                item.increment();
                isContained = true;
                mTotalPrice += item.getPrice();
            }
        }
        if (!isContained) {
            Item item = new Item(product, 1);
            mItemList.add(item);
            mTotalPrice += item.getPrice();
        }
    }

    public void removeProduct(Product product) {
        for (Item item : mItemList) {
            if (item.getProductName() == product.title) {
                item.decrement();
                mTotalPrice -= item.getPrice();
            }
        }
    }

    public void removeAllOfProduct(Product product) {
        for (Item item : mItemList) {
            if (item.getProductName() == product.title) {
                mTotalPrice -= item.getTotalPrice();
                item.resetCount();
            }
        }
    }

    public float getTotalPrice() { return mTotalPrice; }

    public void wipeItems() {
        mItemList.clear();
        mTotalPrice = 0.0f;
    }

    public ArrayList<Item> getProductList() {
        return mItemList;
    }

    public String[] getProductsNamesList() {
        String[] namesList = new String[mItemList.size()];
        for (int i = 0; i < mItemList.size(); ++i) {
            namesList[i] = mItemList.get(i).getProductName();
        }
        return namesList;
    }
}
