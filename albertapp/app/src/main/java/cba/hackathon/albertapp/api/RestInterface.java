package cba.hackathon.albertapp.api;

import java.util.List;

import cba.hackathon.albertapp.models.Order;
import cba.hackathon.albertapp.models.Product;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;

/**
 * This interface defines the possible API calls we can make between Android and WooCommerce.
 */
public interface RestInterface {

    @GET("/wc-api/v2/products")
    void listProducts(Callback<List<Product>> productListCallback);

    @POST("/wc-api/v2/orders")
    void createOrder(@Body Order order, Callback<Order> confirmedOrder);

}
