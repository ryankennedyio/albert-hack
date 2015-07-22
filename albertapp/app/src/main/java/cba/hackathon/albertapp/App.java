package cba.hackathon.albertapp;

import android.app.Application;

import cba.hackathon.albertapp.api.RestService;
import retrofit.RestAdapter;

public class App extends Application {

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
    }
}
