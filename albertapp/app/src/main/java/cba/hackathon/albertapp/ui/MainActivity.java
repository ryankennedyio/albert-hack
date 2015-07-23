package cba.hackathon.albertapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;

import cba.hackathon.albertapp.App;
import cba.hackathon.albertapp.R;
import cba.hackathon.albertapp.models.Product;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity extends BaseActivity implements ZXingScannerView.ResultHandler {

    private Button mLookupBtn;
    private Button mDoneBtn;
    private TextView mTotalCost;
    private TextView mUsername;

    private App mApp;

    private LinearLayout mLinearLayout;
    private ZXingScannerView mScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addDrawerItems();
        mTitle.setText("Scan Item");

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
        mUsername = (TextView) findViewById(R.id.text_username);
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
        mUsername.setText(mApp.getUser());
    }

    @Override
    public void onPause() {
        mScannerView.stopCamera();
        super.onPause();
    }

    @Override
    public void handleResult(Result result) {
        mScannerView.startCamera();

        Product product = mApp.getProductList().getProductBySKU(result.getText());
        if (product == null) {
            Toast.makeText(
                    MainActivity.this,
                    "Product not found",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        mApp.getCart().addProduct(product);
        mTotalCost.setText("$" + String.format("%.2f", mApp.getCart().getTotalPrice()));

        // Success Toast (marquee)
        String toastText = product.title + " added to cart";
        if (toastText.length() > 20) {
            toastText = toastText.substring(0,19);
            toastText += "...";
        }
        Toast.makeText(
                MainActivity.this,
                toastText,
                Toast.LENGTH_SHORT
        ).show();
    }
}