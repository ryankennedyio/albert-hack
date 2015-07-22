package cba.hackathon.albertapp.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import cba.hackathon.albertapp.App;
import cba.hackathon.albertapp.R;

public class LookupItemActivity extends BaseActivity {

    private EditText mSearchInput;
    private Button mSearchBtn;
    private ListView mStockItemsList;
    private Button mCancel;

    private ArrayAdapter<String> mAdapter;

    private App mApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lookup_item);

        initResources();
        setListeners();

        mApp = ((App) getApplicationContext());

        // Adding items to listview
        mAdapter = new ArrayAdapter<String>(this, R.layout.search_item, R.id.product_name, mApp.getProductList());
        mStockItemsList.setAdapter(mAdapter);

        /**
         * Enabling Search Filter
         * */
        mSearchInput.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                mAdapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });
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
