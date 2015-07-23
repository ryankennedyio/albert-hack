package cba.hackathon.albertapp;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import java.util.List;

import cba.hackathon.albertapp.api.RestService;
import cba.hackathon.albertapp.api.ServiceGenerator;
import cba.hackathon.albertapp.models.Cart;
import cba.hackathon.albertapp.models.Product;
import cba.hackathon.albertapp.models.ProductList;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class App extends Application {
    private String mCurrentUser;
    private Cart mCart;
    private ProductList mProductList;
    private  RestService mApi;
    public static String ENDPOINT = "https://reconsale.com";

    @Override
    public void onCreate() {
        super.onCreate();

        mApi = ServiceGenerator.createService(
                RestService.class,
                ENDPOINT,
                "ck_a769c8f2ea7b00a596b5913a97e8407d",
                "cs_d7c74460a1519935d5804cf77d4ccb91"
        );


        // Fetch the products from the server and populate in the callback
        final Context context = this;
        mApi.getProducts(new Callback<List<Product>>() {
            @Override
            public void success(List<Product> products, Response response) {
                for ( Product product : products ) {
                    mProductList.addProduct(product);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(
                        context,
                        "An error occured in retrieving the product list",
                        Toast.LENGTH_LONG
                ).show();
            }
        });

        //Instantiate a instance of the Cart
        mCart = new Cart();
    }

    public void setUser(String user) {
        mCurrentUser = user;
    }

    public Cart getCart() {
        return mCart;
    }

    public ProductList getProductList() {
        return mProductList;
    }

    public RestService getApi() { return mApi; }
}
