package cba.hackathon.albertapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aevi.payment.PaymentRequest;
import com.aevi.payment.TransactionResult;

import java.math.BigDecimal;
import java.util.Currency;

import cba.hackathon.albertapp.R;


public class ConfirmActivity extends BaseActivity {

    private static int REQUEST_PAYMENT = 0;
    private Button mBackBtn;
    private Button mEmptyBtn;
    private Button mPaymentButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        initResources();
        setListeners();
    }

    @Override
    void initResources() {
        mBackBtn = (Button) findViewById(R.id.btn_empty);
        mEmptyBtn = (Button) findViewById(R.id.btn_empty);
        mPaymentButton = (Button) findViewById(R.id.btn_pay);
    }

    @Override
    void setListeners() {

        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exitActivity();
            }
        });

        mEmptyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        mPaymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Construct a $20 payment
                PaymentRequest payment = new PaymentRequest(new BigDecimal("20.00"));
                payment.setCurrency(Currency.getInstance("AUD"));

                //Launch the payment app
                startActivityForResult(payment.createIntent(), REQUEST_PAYMENT);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Obtain the transaction result from the returned data.
        TransactionResult result = TransactionResult.fromIntent(data);
        // Use a toast to show the transaction result.
        Toast.makeText(this, "Transaction result: " + result.getTransactionStatus(), Toast.LENGTH_LONG).show();
    }

    private void exitActivity(){
        finish();
    }
}
