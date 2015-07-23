package cba.hackathon.albertapp.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.aevi.payment.PaymentRequest;
import com.aevi.payment.TransactionResult;

import java.math.BigDecimal;
import java.util.Currency;

import cba.hackathon.albertapp.R;

abstract public class BaseActivity extends AppCompatActivity{

    protected ListView mDrawerList;
    protected ArrayAdapter<String> mAdapter;
    protected DrawerLayout mDrawer;
    protected InputMethodManager imm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    protected void addDrawerItems(){
        mDrawerList = (ListView)findViewById(R.id.menu_drawer);
        String[] osArray = { getResources().getString(R.string.logout), getResources().getString(R.string.settings) };
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);
    }

    protected void initResources(){
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    void setListeners(){
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Intent intent = new Intent(BaseActivity.this, LoginActivity.class);
                        startActivity(intent);
                        BaseActivity.this.overridePendingTransition(R.anim.fade_in, R.anim.no_animation);
                        break;
                    case 1:

                        break;
                }
            }
        });
    }

    protected void exitActivity(){
        finish();
        this.overridePendingTransition(R.anim.no_animation, R.anim.push_down_out);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                exitActivity();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();  // optional depending on your needs
        this.overridePendingTransition(R.anim.no_animation, R.anim.push_down_out);
    }

    @Override
    public void onResume(){
        super.onResume();
        mDrawer.closeDrawers();
        imm.hideSoftInputFromWindow(new View(this).getWindowToken(), 0);
        Log.d("test","asdfasdsfd");
    }
}
