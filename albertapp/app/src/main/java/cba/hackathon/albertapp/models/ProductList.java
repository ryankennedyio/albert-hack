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

    /**
     *
     * @param product
     * @param amount
     * @warn Ensure the amount can actually be decremented
     */
    public void decrementProduct(Product product, int amount) {
        Product currentProduct = mNameList.get(product.title);
        currentProduct.stockQuantity -= amount;
        if (currentProduct.stockQuantity < 0) {
            throw new IllegalArgumentException("Not enough products to decrement");
        }

        addProduct(currentProduct);
    }
}
