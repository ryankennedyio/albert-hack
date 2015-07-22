package cba.hackathon.albertapp;

import android.app.Application;

import cba.hackathon.albertapp.api.RestService;
import cba.hackathon.albertapp.models.Cart;
import retrofit.RestAdapter;

public class App extends Application {
    public RestService api;
    public static String ENDPOINT = "http://www.example.com";

    private int mCurrentUser;
    private Cart mCart;
    private String mProductList[];

    @Override
    public void onCreate() {
        super.onCreate();

        //Instantiate a singleton instance of the API
        RestAdapter builder = new RestAdapter.Builder()
                .setEndpoint(ENDPOINT)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        api = builder.create(RestService.class);

        // Fetch products
        mProductList = new String[]{"Dell Inspiron", "HTC One X", "HTC Wildfire S", "HTC Sense", "HTC Sensation XE",
                "iPhone 4S", "Samsung Galaxy Note 800",
                "Samsung Galaxy S3", "MacBook Air", "Mac Mini", "MacBook Pro"};

        //Instantiate a instance of the Cart
        mCart = new Cart();
    }

    public void setUser(int user) {
        mCurrentUser = user;
    }

    public Cart getCart() {
        return mCart;
    }

    public String[] getProductList() {
        return mProductList;
    }

}
