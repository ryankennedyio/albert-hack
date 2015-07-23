package cba.hackathon.albertapp;

import android.app.Application;

import cba.hackathon.albertapp.api.RestService;
import cba.hackathon.albertapp.models.Cart;
import cba.hackathon.albertapp.models.Product;
import cba.hackathon.albertapp.models.ProductList;
import retrofit.RestAdapter;

public class App extends Application {
    private String mCurrentUser;
    private Cart mCart;
    private ProductList mProductList;
    private  RestService mApi;
    public static String ENDPOINT = "http://www.example.com";

    @Override
    public void onCreate() {
        super.onCreate();

        //Instantiate a singleton instance of the API
        RestAdapter builder = new RestAdapter.Builder()
                .setEndpoint(ENDPOINT)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        mApi = builder.create(RestService.class);

        mProductList = new ProductList();

        // Mock products
        Product mockProduct1 = new Product();
        mockProduct1.title = "Ravi";
        mockProduct1.sku = "10323";
        mockProduct1.price = 0.01f; //cheap! As chips
        mockProduct1.stockQuantity = 1;
        Product mockProduct2 = new Product();
        mockProduct2.title = "Ryan";
        mockProduct2.sku = "10324";
        mockProduct2.price = 1.01f;
        mockProduct2.stockQuantity = 1;
        Product mockProduct3 = new Product();
        mockProduct3.title = "Emily";
        mockProduct3.sku = "10325";
        mockProduct3.price = 0.28f;
        mockProduct3.stockQuantity = 1;
        Product mockProduct4 = new Product();
        mockProduct4.title = "Jordan";
        mockProduct4.sku = "10326";
        mockProduct4.price = 59.01f;
        mockProduct4.stockQuantity = 1;
        Product mockProduct5 = new Product();
        mockProduct5.title = "Edward";
        mockProduct5.sku = "10327";
        mockProduct5.price = 89.01f;
        mockProduct5.stockQuantity = 1;
        Product mockProduct6 = new Product();
        mockProduct6.title = "Konrad";
        mockProduct6.sku = "10328";
        mockProduct6.price = 14.01f;
        mockProduct6.stockQuantity = 1;
        Product mockProduct7 = new Product();
        mockProduct7.title = "reconSale";
        mockProduct7.sku = "10328";
        mockProduct7.price = 14.01f;
        mockProduct7.stockQuantity = 1;
        Product mockProduct8 = new Product();
        mockProduct8.title = "Gatorade";
        mockProduct8.sku = "10330";
        mockProduct8.price = 10.99f;
        mockProduct8.stockQuantity = 1;
        Product mockProduct9 = new Product();
        mockProduct9.title = "SmashHydro";
        mockProduct9.sku = "10331";
        mockProduct9.price = 4.01f;
        mockProduct9.stockQuantity = 1;
        Product mockProduct10 = new Product();
        mockProduct10.title = "okay";
        mockProduct10.sku = "10332";
        mockProduct10.price = 2.01f;
        mockProduct10.stockQuantity = 1;
        Product mockProduct11 = new Product();
        mockProduct11.title = "Gradle";
        mockProduct11.sku = "10333";
        mockProduct11.price = 72.01f;
        mockProduct11.stockQuantity = 1;
        Product mockProduct12 = new Product();
        mockProduct12.title = "Ravoring";
        mockProduct12.sku = "10334";
        mockProduct12.price = 1042.01f;
        mockProduct12.stockQuantity = 1;
        Product mockProduct13 = new Product();
        mockProduct13.title = "Vaderico";
        mockProduct13.sku = "10335";
        mockProduct13.price = 4.17f;
        mockProduct13.stockQuantity = 1;
        Product mockProduct14 = new Product();
        mockProduct14.title = "newEvidence";
        mockProduct14.sku = "10336";
        mockProduct14.price = 1232.01f;
        mockProduct14.stockQuantity = 1;
        Product mockProduct15 = new Product();
        mockProduct15.title = "Jemsoft";
        mockProduct15.sku = "10336";
        mockProduct15.price = 2.22f;
        mockProduct15.stockQuantity = 1;
        Product mockProduct16 = new Product();
        mockProduct16.title = "Albert";
        mockProduct16.sku = "10337";
        mockProduct16.price = 2131.01f;
        mockProduct16.stockQuantity = 1;
        Product mockProduct17 = new Product();
        mockProduct17.title = "Algore";
        mockProduct17.sku = "10338";
        mockProduct17.price = 41.01f;
        mockProduct17.stockQuantity = 1;
        Product mockProduct18 = new Product();
        mockProduct18.title = "Mobile";
        mockProduct18.sku = "10339";
        mockProduct18.price = 2.41f;
        mockProduct18.stockQuantity = 1;
        Product mockProduct19 = new Product();
        mockProduct19.title = "Mobil";
        mockProduct19.sku = "10340";
        mockProduct19.price = 0.01f;
        mockProduct19.stockQuantity = 1;
        Product mockProduct20 = new Product();
        mockProduct20.title = "Mobil";
        mockProduct20.sku = "10341";
        mockProduct20.price = 0.33f;
        mockProduct20.stockQuantity = 1;

        // Fetch products
        mProductList.addProduct(mockProduct1);
        mProductList.addProduct(mockProduct2);
        mProductList.addProduct(mockProduct3);
        mProductList.addProduct(mockProduct4);
        mProductList.addProduct(mockProduct5);
        mProductList.addProduct(mockProduct6);
        mProductList.addProduct(mockProduct7);
        mProductList.addProduct(mockProduct8);
        mProductList.addProduct(mockProduct9);
        mProductList.addProduct(mockProduct10);
        mProductList.addProduct(mockProduct11);
        mProductList.addProduct(mockProduct12);
        mProductList.addProduct(mockProduct13);
        mProductList.addProduct(mockProduct14);
        mProductList.addProduct(mockProduct15);
        mProductList.addProduct(mockProduct16);
        mProductList.addProduct(mockProduct17);
        mProductList.addProduct(mockProduct18);
        mProductList.addProduct(mockProduct19);
        mProductList.addProduct(mockProduct20);

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
