package cba.hackathon.albertapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
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

    abstract protected void initResources();

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

    void setListeners(){
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Log.d("Test", "call login");
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
}
