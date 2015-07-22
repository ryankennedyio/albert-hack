package cba.hackathon.albertapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import cba.hackathon.albertapp.App;
import cba.hackathon.albertapp.R;
import cba.hackathon.albertapp.models.Cart;

public class MainActivity extends BaseActivity{

    private Button mLookupBtn;
    private Button mDoneBtn;
    private TextView mTotalCost;

    private Cart mCart;

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
        mCart = ((App) getApplicationContext()).getCart();

        mLookupBtn = (Button) findViewById(R.id.btn_lookup_item);
        mDoneBtn = (Button) findViewById(R.id.btn_done);
        mTotalCost = (TextView) findViewById(R.id.text_total_cosi);
        mTotalCost.setText("$" + String.format("%.2f", mCart.getTotalPrice()));
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

}