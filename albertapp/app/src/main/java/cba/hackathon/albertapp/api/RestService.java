package cba.hackathon.albertapp.api;

import java.util.List;

import cba.hackathon.albertapp.models.Order;
import cba.hackathon.albertapp.models.Product;
import cba.hackathon.albertapp.models.ProductList;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;

/**
 * This interface defines the possible API calls we can make between Android and WooCommerce.
 */
public interface RestService {

    @GET("/wc-api/v2/products")
    void getProducts(Callback<ProductList> productListCallback);

    @POST("/wc-api/v2/orders")
    void createOrder(@Body Order order, Callback<Order> confirmedOrder);

}
