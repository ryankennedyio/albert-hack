package cba.hackathon.albertapp.models;

/**
 * Created by Konrad on 22/07/2015.
 */
public class Item {
    private final Product mProduct;
    private int mCount;

    public Item(Product product, int count) {
        mProduct = product;
        mCount = count;
    }

    public void increment() {
        ++mCount;
    }

    public void decrement() {
        --mCount;

        // Negative count invalid
        if (mCount < 0) {
            mCount = 0;
        }
    }

    public String getProductName() {
        return mProduct.title;
    }

    public int getCount() {
        return mCount;
    }

    public float getPrice() {
        return mProduct.price;
    }

    public float getTotalPrice() {
        return mCount * getPrice();
    }

    public boolean isEmpty() {
        return mCount == 0;
    }

    public void resetCount() {
        mCount = 0;
    }
}
