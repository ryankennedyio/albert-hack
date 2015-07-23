package cba.hackathon.albertapp.ui;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
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

    private LinearLayout mLinearLayout;
    private ZXingScannerView mScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addDrawerItems();
        getSupportActionBar().setTitle("Scan Item");
        getSupportActionBar().setCustomView(R.layout.actionbar);

        initResources();
        setListeners();

    }

    @Override
    protected void initResources() {
        super.initResources();
        mApp = ((App) getApplicationContext());

        mLookupBtn = (Button) findViewById(R.id.btn_lookup_item);
        mDoneBtn = (Button) findViewById(R.id.btn_done);


        mLinearLayout = (LinearLayout) findViewById(R.id.scanner_view);
        mScannerView = new ZXingScannerView(this);
        mScannerView.startCamera();

        mScannerView.setResultHandler(this);
        ViewGroup insertPoint = (ViewGroup) findViewById(R.id.scanner_view);
        insertPoint.addView(mScannerView, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
        mTotalCost = (TextView) findViewById(R.id.text_total_cost);
        mTotalCost.setText("$" + String.format("%.2f", mApp.getCart().getTotalPrice()));
    }

    @Override
    protected void setListeners() {
        super.setListeners();

        mLookupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mScannerView.stopCamera();

                Intent intent = new Intent(MainActivity.this, LookupItemActivity.class);
                startActivity(intent);
                MainActivity.this.overridePendingTransition(R.anim.push_up_in, R.anim.no_animation);
            }
        });

        mDoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mScannerView.stopCamera();

                Intent intent = new Intent(MainActivity.this, ConfirmActivity.class);
                startActivity(intent);
                MainActivity.this.overridePendingTransition(R.anim.push_up_in, R.anim.no_animation);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.startCamera();
        mTotalCost.setText("$" + String.format("%.2f", mApp.getCart().getTotalPrice()));
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result result) {
        Log.v("SKU", result.getText());

        mScannerView.startCamera();

        Product product = mApp.getProductList().getProductBySKU(result.getText());
        if (product == null) {
            // TODO TOAST FAIL (ITEM NOT FOUND)
            Log.v("scan", "product is null");
            return;
        }

        Log.v("scan", "product is not null");
        mApp.getCart().addProduct(product);
        mTotalCost.setText("$" + String.format("%.2f", mApp.getCart().getTotalPrice()));
        // TODO Scan complete TOAST
    }
}