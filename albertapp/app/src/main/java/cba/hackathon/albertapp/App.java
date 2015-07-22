package cba.hackathon.albertapp;

import android.app.Application;

import cba.hackathon.albertapp.api.RestService;
import cba.hackathon.albertapp.models.Cart;
import retrofit.RestAdapter;

public class App extends Application {

    private int mCurrentUser;
    private Cart mCart;

    public RestService api;
    public static String ENDPOINT = "http://www.example.com";

    @Override
    public void onCreate() {
        super.onCreate();

        //Instantiate a singleton instance of the API
        RestAdapter builder = new RestAdapter.Builder()
                .setEndpoint(ENDPOINT)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        api = builder.create(RestService.class);

        //Instantiate a instance of the Cart
        mCart = new Cart();
    }

    public void setUser(int user) {
        mCurrentUser = user;
    }

    public Cart getCart() {
        return mCart;
    }
}
