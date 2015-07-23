package cba.hackathon.albertapp.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.aevi.helpers.services.AeviServiceConnectionCallback;
import com.aevi.payment.PaymentRequest;
import com.aevi.payment.TransactionResult;
import com.aevi.printing.PrintService;
import com.aevi.printing.PrintServiceProvider;
import com.aevi.printing.model.Alignment;
import com.aevi.printing.model.PrintPayload;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;

import cba.hackathon.albertapp.App;
import cba.hackathon.albertapp.R;
import cba.hackathon.albertapp.api.RestService;
import cba.hackathon.albertapp.models.Cart;
import cba.hackathon.albertapp.models.Order;
import cba.hackathon.albertapp.models.Product;
import cba.hackathon.albertapp.models.ProductAdapter;
import cba.hackathon.albertapp.models.ProductList;
import cba.hackathon.albertapp.models.Wrapper;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class ConfirmActivity extends BaseActivity {

    private static int REQUEST_PAYMENT = 0;
    private static String PAYMENT_APPROVED = "APPROVED";
    private LinearLayout mEmptyBtn;
    private LinearLayout mPaymentButton;
    private ListView mCartItems;
    private TextView mTotalCost;

    private RestService mApi;
    private Cart mCart;

    private ProductAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);
        addDrawerItems();
        getSupportActionBar().setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar);
        mTitle = (TextView) findViewById(R.id.actionbar_title);
        mTitle.setText("Confirm Order");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        initResources();
        setListeners();
    }

    @Override
    protected void initResources() {
        super.initResources();
        mApi = ((App) getApplicationContext()).getApi();
        mCart = ((App) getApplicationContext()).getCart();

        mCartItems = (ListView) findViewById(R.id.list_cart_items);
        mEmptyBtn = (LinearLayout) findViewById(R.id.btn_empty);
        mPaymentButton = (LinearLayout) findViewById(R.id.btn_pay);
        mTotalCost = (TextView) findViewById(R.id.text_total_cost);
        mTotalCost.setText("$" + String.format("%.2f", mCart.getTotalPrice()));

        mAdapter = new ProductAdapter(this, mCart.getProductList(),false);
        mCartItems.setAdapter(mAdapter);
    }

    @Override
    protected void setListeners() {
        super.setListeners();

        mEmptyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wipeCart();
            }
        });

        mPaymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Get the total price in the cart at this time, round the float & validate it
                BigDecimal totalPrice = new BigDecimal(mCart.getTotalPrice());
                totalPrice = totalPrice.setScale(2, BigDecimal.ROUND_HALF_UP);

                //If the price in the cart is $0.0
                if (totalPrice.compareTo(new BigDecimal(0)) <= 0) {
                    Toast.makeText(
                            view.getContext(),
                            "You can't make a payment for $0",
                            Toast.LENGTH_SHORT
                    ).show();
                    return;
                }

                //Construct the payment
                PaymentRequest payment = new PaymentRequest(totalPrice);
                payment.setCurrency(Currency.getInstance("AUD"));

                //Launch the payment app
                startActivityForResult(payment.createIntent(), REQUEST_PAYMENT);
                ConfirmActivity.this.overridePendingTransition(R.anim.push_up_in, R.anim.no_animation);
            }
        });
    }

    private void wipeCart() {
        mCart.wipeItems();
        mAdapter = new ProductAdapter(this, mCart.getProductList(),false);
        mCartItems.setAdapter(mAdapter);
        mTotalCost.setText("$" + String.format("%.2f", mCart.getTotalPrice()));
    }

    @Override
    public void onResume() {
        super.onResume();
        mTotalCost.setText("$" + String.format("%.2f", mCart.getTotalPrice()));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Obtain the transaction result from the returned data.
        TransactionResult result = TransactionResult.fromIntent(data);

        //Create an order to send to WooCommerce
        //TODO not a real order...
        Order order = new Order();

        LinkedHashMap<Product, Integer> productList = mCart.getCartList();
        for ( Product product : productList.keySet() ) {
            Order.LineItem lineItem = new Order.LineItem();

            lineItem.productId = product.id;
            lineItem.quantity = productList.get(product);

            order.addLineItem(lineItem);
        }

        Wrapper wrapper = new Wrapper();
        wrapper.order = order;

        /* If the transaction was approved, make a POST request on the order to WooCommerce */
        final Context context = this;
        if (PAYMENT_APPROVED.equals(result.getTransactionStatus().name())) {
            mApi.createOrder(wrapper, new Callback<Order>() {
                @Override
                public void success(Order order, Response response) {
                    Toast
                            .makeText(context, "Order Confirmation Successful", Toast.LENGTH_SHORT)
                            .show();

                    wipeCart();

                    PrintServiceProvider serviceProvider = new PrintServiceProvider(ConfirmActivity.this);

                    serviceProvider.connect(new AeviServiceConnectionCallback<PrintService>() {
                        @Override
                        public void onConnect(PrintService service) {

                            if (service == null) {
                                Log.v("print", "Print service failed to open");
                                return;
                            }

                            PrintService printService = service;

                            PrintPayload printPayload = new PrintPayload();

                            printPayload.append("Cheap As Chips").align(Alignment.CENTER);
                            printPayload.append("Tax Invoice ABN: 45 024 189 799").align(Alignment.CENTER);
                            printPayload.appendEmptyLine();

                            // Add pic
                            BitmapFactory.Options options = printService.getDefaultPrinterSettings().asBitmapFactoryOptions();
                            Bitmap image = BitmapFactory.decodeResource(getResources(), R.mipmap.receipt_logo, options);
                            printPayload.append(image);

                            printPayload.append("Southern Cross Arcade        Pop-up: 0438").align(Alignment.LEFT);
                            printPayload.append("Adelaide Showground, 39 Goodwood Rd, Wayville SA 5034").align(Alignment.LEFT);
                            printPayload.append("Phone: 0421 321 743          Receipt: 7942").align(Alignment.LEFT);

                            printPayload.append("__________________________").align(Alignment.CENTER);

                            SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
                            Date date = new Date(System.currentTimeMillis());
                            printPayload.append(String.format("Date: %s                                         Time: %s", dateFormatter.format(date), "11:30")).align(Alignment.LEFT);

                            printPayload.append("Albert 5, Served By: Tom - 3244").align(Alignment.LEFT);

                            printPayload.append("__________________________").align(Alignment.CENTER);

                            printPayload.append("$").align(Alignment.RIGHT);

                            for (Product p : mCart.getProductList().getProducts()) {
                                printPayload.append(p.title).align(Alignment.LEFT);
                                printPayload.append("$" + String.format("%.2f", mCart.getProductQuantityPrice(p))).align(Alignment.RIGHT);
                                printPayload.append("   Quantity:   " + Integer.toString(mCart.getProductCount(p)) +
                                    " @ $" + String.format("%.2f", p.price)).align(Alignment.LEFT);
                            }

                            printPayload.appendEmptyLine();

                            printPayload.append("Total for " + Integer.toString(mCart.getAmountOfProducts()) + " items" +
                            "                      $" + String.format("%.2f", mCart.getTotalPrice())).align(Alignment.LEFT);
                            printPayload.append("Gift Card                         0.00").align(Alignment.LEFT);
                            printPayload.append("GST INCLUDED IN TOTAL                      $0.53").align(Alignment.LEFT);

                            // finally print the payload
                            printService.print(printPayload);
                        }
                    });
                }

                @Override
                public void failure(RetrofitError error) {
                    Toast
                            .makeText(context, "Order Failed", Toast.LENGTH_SHORT)
                            .show();
                }
            });
        }
    }
}
