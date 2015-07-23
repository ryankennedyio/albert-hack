package cba.hackathon.albertapp.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Konrad on 22/07/2015.
 */
public class ProductList {
    @SerializedName("products")
    private ArrayList<Product> mProductList;

    public ProductList() {
        mProductList = new ArrayList<>();
    }

//    public ProductList

    public void addProduct(Product product) {
        mProductList.add(product);
    }

    public Product getProductByName(String name) {
        for (Product p :mProductList) {
            if (p.title.equals(name)) {
                return p;
            }
        }
        return null;
    }

    public Product getProductBySKU(String sku) {
        for (Product p : mProductList) {
            if (p.sku.equals(sku)) {
                return p;
            }
        }
        return null;
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
