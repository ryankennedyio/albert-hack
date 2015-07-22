package cba.hackathon.albertapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cba.hackathon.albertapp.R;

public class MainActivity extends BaseActivity {

    private Button mLoginBtn;
    private Button mLookupBtn;
    private Button mDoneBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initResources();
        setListeners();
    }

    @Override
    void initResources() {
        mLoginBtn = (Button) findViewById(R.id.btn_login);
        mLookupBtn = (Button) findViewById(R.id.btn_lookup_item);
        mDoneBtn = (Button) findViewById(R.id.btn_done);
    }

    @Override
    void setListeners() {
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        mLookupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LookupItemActivity.class);
                startActivity(intent);
            }
        });

        mDoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ConfirmActivity.class);
                startActivity(intent);
            }
        });
    }
}




//
//
//    package cba.hackathon.albertapp;
//
//    import android.content.Intent;
//    import android.support.v7.app.AppCompatActivity;
//    import android.os.Bundle;
//    import android.view.Menu;
//    import android.view.MenuItem;
//    import android.view.View;
//    import android.widget.Button;
//    import android.widget.Toast;
//
//    import com.aevi.payment.PaymentRequest;
//    import com.aevi.payment.TransactionResult;
//
//    import java.math.BigDecimal;
//    import java.util.Currency;
//
//    import cba.hackathon.albertapp.api.RestService;
//    import cba.hackathon.albertapp.models.Order;
//    import retrofit.Callback;
//    import retrofit.RetrofitError;
//    import retrofit.client.Response;
//
//    public class MainActivity extends AppCompatActivity {
//        public static int REQUEST_PAYMENT = 0;
//        public static String PAYMENT_APPROVED = "APPROVED";
//
//        private RestService mApi;
//        private Order mockOrder;
//
//        @Override
//        protected void onCreate(Bundle savedInstanceState) {
//            super.onCreate(savedInstanceState);
//            setContentView(R.layout.activity_main);
//
//        /* Set up the API */
//            mApi = ((App) getApplicationContext()).api;
//
//        /* Mock an Order */
//            mockOrder = new Order();
//
//            Button paymentButton = (Button) findViewById(R.id.btn_payment);
//            paymentButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    //Construct a $20 payment
//                    PaymentRequest payment = new PaymentRequest(new BigDecimal("20.00"));
//                    payment.setCurrency(Currency.getInstance("AUD"));
//
//                    //Launch the payment app
//                    startActivityForResult(payment.createIntent(), REQUEST_PAYMENT);
//                }
//            });
//        }
//
//        @Override
//        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//            // Obtain the transaction result from the returned data.
//            TransactionResult result = TransactionResult.fromIntent(data);
//            // Use a toast to show the transaction result.
//            Toast.makeText(this, "Transaction result: " + result.getTransactionStatus(), Toast.LENGTH_LONG).show();
//
//        /* If the transaction was approved, make a POST request on the order to WooCommerce */
//            if ( PAYMENT_APPROVED.equals(result.getTransactionStatus().name())) {
//                mApi.createOrder(mockOrder, new Callback<Order>() {
//                    @Override
//                    public void success(Order order, Response response) {
//                        //Order created
//                    }
//
//                    @Override
//                    public void failure(RetrofitError error) {
//                        //Order failed
//                    }
//                });
//            }
//        }
//    }
//}
