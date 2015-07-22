package cba.hackathon.albertapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.aevi.payment.PaymentRequest;
import com.aevi.payment.TransactionResult;

import java.math.BigDecimal;
import java.util.Currency;

abstract public class BaseActivity extends AppCompatActivity{

    abstract void initResources();
    abstract void setListeners();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}
