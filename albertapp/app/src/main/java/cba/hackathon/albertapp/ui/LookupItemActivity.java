package cba.hackathon.albertapp.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import cba.hackathon.albertapp.R;

public class LookupItemActivity extends BaseActivity {

    private EditText mSearchInput;
    private Button mSearchBtn;
    private ListView mStockItemsList;
    private Button mCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lookup_item);

        initResources();
        setListeners();
    }

    @Override
    void initResources() {
        mSearchInput = (EditText) findViewById(R.id.edit_search);
        mSearchInput.setSelected(false);
        mSearchBtn = (Button) findViewById(R.id.btn_search);
        mStockItemsList = (ListView) findViewById(R.id.stock_items);
        mCancel = (Button) findViewById(R.id.btn_cancel);
    }

    @Override
    void setListeners() {
        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exitActivity();
            }
        });
    }

    private void exitActivity(){
        finish();
    }
}
