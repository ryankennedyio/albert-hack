package cba.hackathon.albertapp.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.aevi.payment.PaymentRequest;
import com.aevi.payment.TransactionResult;

import java.math.BigDecimal;
import java.util.Currency;

import cba.hackathon.albertapp.App;
import cba.hackathon.albertapp.R;
import cba.hackathon.albertapp.api.RestService;
import cba.hackathon.albertapp.models.Cart;
import cba.hackathon.albertapp.models.Order;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class ConfirmActivity extends BaseActivity {

    private static int REQUEST_PAYMENT = 0;
    private static String PAYMENT_APPROVED = "APPROVED";
    private Button mEmptyBtn;
    private Button mPaymentButton;
    private ListView mCartItems;
    private TextView mTotalCost;

    private RestService mApi;
    private Cart mCart;

    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);
        addDrawerItems();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        initResources();
        setListeners();
    }

    @Override
    protected void initResources() {
        mApi = ((App) getApplicationContext()).getApi();
        mCart = ((App) getApplicationContext()).getCart();

        mCartItems = (ListView) findViewById(R.id.list_cart_items);
        mEmptyBtn = (Button) findViewById(R.id.btn_empty);
        mPaymentButton = (Button) findViewById(R.id.btn_pay);
        mTotalCost = (TextView) findViewById(R.id.text_total_cost);
        mTotalCost.setText("$" + String.format("%.2f", mCart.getTotalPrice()));

        //TODO override onRefresh
        mAdapter = new ArrayAdapter<String>(this, R.layout.search_item, R.id.product_name, mCart.getProductsNamesList());
        mCartItems.setAdapter(mAdapter);
    }

    @Override
    protected void setListeners() {

        mEmptyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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

    @Override
    protected void onResume() {
        super.onResume();
        mTotalCost.setText("$" + String.format("%.2f", mCart.getTotalPrice()));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Obtain the transaction result from the returned data.
        TransactionResult result = TransactionResult.fromIntent(data);

        //Create an order to send to WooCommerce
        Order order = new Order(); //TODO not a real order...

        final Context context = this;

        /* If the transaction was approved, make a POST request on the order to WooCommerce */
        if (PAYMENT_APPROVED.equals(result.getTransactionStatus().name())) {
            mApi.createOrder(order, new Callback<Order>() {
                @Override
                public void success(Order order, Response response) {
                    Toast
                            .makeText(context, "Order Confirmation Successful", Toast.LENGTH_SHORT)
                            .show();
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
