package cba.hackathon.albertapp.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.zxing.Result;

import cba.hackathon.albertapp.App;
import cba.hackathon.albertapp.R;
import cba.hackathon.albertapp.models.Cart;
import cba.hackathon.albertapp.models.Product;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity extends BaseActivity implements ZXingScannerView.ResultHandler {

    private Button mLookupBtn;
    private Button mDoneBtn;
    private TextView mTotalCost;

    private App mApp;
    private Cart mCart;

    private LinearLayout mLinearLayout;
    private ZXingScannerView mScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addDrawerItems();

        initResources();
        setListeners();

    }

    @Override
    protected void initResources() {
        mApp = ((App) getApplicationContext());
        mCart = mApp.getCart();

        mLookupBtn = (Button) findViewById(R.id.btn_lookup_item);
        mDoneBtn = (Button) findViewById(R.id.btn_done);
        mTotalCost = (TextView) findViewById(R.id.text_total_cosi);
        mTotalCost.setText("$" + String.format("%.2f", mCart.getTotalPrice()));

        mLinearLayout = (LinearLayout) findViewById(R.id.scanner_view);
        mScannerView = new ZXingScannerView(this);
        mScannerView.startCamera();
        mScannerView.setResultHandler(this);
        ViewGroup insertPoint = (ViewGroup) findViewById(R.id.scanner_view);
        insertPoint.addView(mScannerView, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
    }

    @Override
    protected void setListeners() {
        super.setListeners();

        mLookupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LookupItemActivity.class);
                startActivity(intent);
                MainActivity.this.overridePendingTransition(R.anim.push_up_in, R.anim.no_animation);
            }
        });

        mDoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ConfirmActivity.class);
                startActivity(intent);
                MainActivity.this.overridePendingTransition(R.anim.push_up_in, R.anim.no_animation);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mTotalCost.setText("$" + String.format("%.2f", mCart.getTotalPrice()));
    }

    @Override
    public void handleResult(Result result) {
        Log.v("SKU", result.getText());

        Product product = mApp.getProductList().getProductBySKU(result.getText());
        if (product == null) {
            // TODO TOAST FAIL (ITEM NOT FOUND)
            return;
        }

        mCart.addProduct(product);
        // TODO Scan complete TOAST
        x`
    }
}