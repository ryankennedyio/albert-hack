package cba.hackathon.albertapp.models;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Konrad on 22/07/2015.
 */
public class ProductList {
    private HashMap<String, Product> mNameList;
    private HashMap<String, Product>  mSKUList;
    private ArrayList<Product> mProductList;

    public ProductList() {
        mNameList = new HashMap<String, Product>();
        mSKUList = new HashMap<String, Product>();
        mProductList = new ArrayList<>();
    }

    public void addProduct(Product product) {
        mNameList.put(product.title, product);
        mSKUList.put(product.sku, product);
        mProductList.add(product);
    }

    public Product getProductByName(String name) {
        return mNameList.get(name);
    }

    public String[] getProductsNamesList() {
        return mNameList.keySet().toArray(new String[0]);
    }

    public ArrayList<Product> getProducts() {
        return mProductList;
    }

    public Product get(int index) {
        return mProductList.get(index);
    }

    public int size() {
        return mProductList.size();
    }

}
