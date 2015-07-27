package cba.hackathon.albertapp.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
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

    private LinearLayout mLookupBtn;
    private LinearLayout mDoneBtn;
    private TextView mTotalCost;
    private TextView mUsername;

    private App mApp;

    private HandlerThread mScannerHandlerThread;
    private Handler mScannerHandler;

    private LinearLayout mLinearLayout;
    private ZXingScannerView mScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addDrawerItems();
        getSupportActionBar().setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar);
        mTitle = (TextView) findViewById(R.id.actionbar_title);
        mTitle.setText("Scan Item");

        initResources();
        initScanner();
        setListeners();
    }

    @Override
    protected void initResources() {
        super.initResources();
        mApp = ((App) getApplicationContext());

        mLookupBtn = (LinearLayout) findViewById(R.id.btn_lookup_item);
        mDoneBtn = (LinearLayout) findViewById(R.id.btn_done);

        mLinearLayout = (LinearLayout) findViewById(R.id.scanner_view);
        mTotalCost = (TextView) findViewById(R.id.text_total_cost);
        mUsername = (TextView) findViewById(R.id.text_username);
        mTotalCost.setText("$" + String.format("%.2f", mApp.getCart().getTotalPrice()));
    }

    private void initScanner() {
            mScannerHandlerThread = new HandlerThread("SCANNER_THREAD");
            mScannerHandlerThread.start();
            mScannerHandler = new Handler(mScannerHandlerThread.getLooper());

            mScannerHandler.post(new Runnable() {
                @Override
                public void run() {
                    mScannerView = new ZXingScannerView(MainActivity.this);
                    mScannerView.startCamera();
                    mScannerView.setResultHandler(MainActivity.this);

                    // Inflate view
                    final ViewGroup insertPoint = (ViewGroup) findViewById(R.id.scanner_view);
                    insertPoint.post(new Runnable() {
                        @Override
                        public void run() {
                            insertPoint.addView(mScannerView, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
                        }
                    });

                    Log.v("scanner", "Started scanner");
//        mScannerView.setFlash(true);
                }
            });
    }

    @Override
    protected void setListeners() {
        super.setListeners();

        mLookupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mScannerView.stopCamera();
                mLookupBtn.animate()
                        .setDuration(0)
                        .alpha(.70f)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                mLookupBtn.setAlpha(1.0f);

                                Intent intent = new Intent(MainActivity.this, LookupItemActivity.class);
                                startActivity(intent);
                                MainActivity.this.overridePendingTransition(R.anim.push_up_in, R.anim.no_animation);
                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {
                                onAnimationEnd(animation);
                            }

                            @Override
                            public void onAnimationStart(Animator animation) {
                            }
                        });
            }
        });

        mDoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mScannerView.stopCamera();
                mDoneBtn.animate()
                    .setDuration(0)
                    .alpha(.70f)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mDoneBtn.setAlpha(1.0f);

                            Intent intent = new Intent(MainActivity.this, ConfirmActivity.class);
                            startActivity(intent);
                            MainActivity.this.overridePendingTransition(R.anim.push_up_in, R.anim.no_animation);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {
                            onAnimationEnd(animation);
                        }

                        @Override
                        public void onAnimationStart(Animator animation) {
                        }
                    });
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        mTotalCost.setText("$" + String.format("%.2f", mApp.getCart().getTotalPrice()));
        mUsername.setText(mApp.getUser());

        initScanner();
    }

    @Override
    public void onPause() {
        super.onPause();

//        mScannerView.stopCamera();
//        mScannerView = null;
    }

    @Override
    public void handleResult(Result result) {
//        initScanner();

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