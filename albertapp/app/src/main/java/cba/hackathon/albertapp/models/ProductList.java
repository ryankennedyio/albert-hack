package cba.hackathon.albertapp.models;

import java.util.HashMap;

/**
 * Created by Konrad on 22/07/2015.
 */
public class ProductList {
    private HashMap<String, Product>  mNameList;
    private HashMap<String, Product>  mSKUList;

    public ProductList() {
        mNameList = new HashMap<String, Product>();
        mSKUList = new HashMap<String, Product>();
    }

    public void addProduct(Product product) {
        mNameList.put(product.title, product);
        mSKUList.put(product.sku, product);
    }

    public Product getProductByName(String name) {
        return mNameList.get(name);
    }

    public String[] getProductsNamesList() {
        return mNameList.keySet().toArray(new String[0]);
    }

}
