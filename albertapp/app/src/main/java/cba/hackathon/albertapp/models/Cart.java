package cba.hackathon.albertapp.models;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by Konrad on 22/07/2015.
 */
public class Cart {
    private LinkedHashMap<Product, Integer> mCartList;

    private float mTotalPrice;

    public Cart() {
        mCartList = new LinkedHashMap<>();
        mTotalPrice = 0.0f;
    }

    public void addProduct(Product product) {
        Integer mappedProduct = mCartList.get(product);
        if (mappedProduct == null) {
            mCartList.put(product, 1);
        } else {
            mCartList.put(product, mappedProduct+1);
        }
        mTotalPrice += product.price;
    }

    public void removeProduct(Product product) {
        Integer mappedProduct = mCartList.get(product);
        if (mappedProduct == null) {
            throw new RuntimeException();
        }

        if (mappedProduct == 1) {
            mCartList.remove(product);
            mTotalPrice -= product.price;

            return;
        }

        if (mappedProduct != null) {
            mCartList.put(product, mappedProduct - 1);
            mTotalPrice -= product.price;
        }
    }

    public void removeAllOfProduct(Product product) {
        Integer mappedProduct = mCartList.get(product);
        if (mappedProduct != null) {
            mTotalPrice -= product.price * mappedProduct;
            mCartList.remove(product);

        }
    }

    public float getTotalPrice() {
        if (mTotalPrice < 0) {
            throw new RuntimeException();
        }
        return mTotalPrice;
    }

    public void wipeItems() {
        mCartList.clear();
        mTotalPrice = 0.0f;
    }

    public String[] getProductsNamesList() {
        String[] namesList = new String[mCartList.size()];
        int i = 0;
        for (Product product : mCartList.keySet()) {
            namesList[i] = product.title;
            ++i;
        }
        return namesList;
    }

    public ProductList getProductList(){
        ProductList returnList = new ProductList();
        for ( Product product : mCartList.keySet()) {
            returnList.addProduct(product);
        }
        return returnList;
    }
}
