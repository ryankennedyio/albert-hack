package cba.hackathon.albertapp.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import cba.hackathon.albertapp.App;
import cba.hackathon.albertapp.R;
import cba.hackathon.albertapp.models.Product;
import cba.hackathon.albertapp.models.ProductAdapter;

public class LookupItemActivity extends BaseActivity {

    private EditText mSearchInput;
    private ListView mStockItemsList;

    private ProductAdapter mAdapter;

    private App mApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lookup_item);
        addDrawerItems();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mTitle.setText("Lookup item");
        initResources();
        setListeners();

        mApp = ((App) getApplicationContext());
        mAdapter = new ProductAdapter(this, mApp.getProductList(), true);
        mStockItemsList.setAdapter(mAdapter);

        mStockItemsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Product product = (Product) mStockItemsList.getAdapter().getItem(position);
                mApp.getCart().addProduct(product);
                Toast.makeText(
                        view.getContext(),
                        "Added " + product.title + " to cart",
                        Toast.LENGTH_SHORT
                ).show();
                finish();
            }
        });
    }

    @Override
    protected void initResources() {
        super.initResources();
        mSearchInput = (EditText) findViewById(R.id.edit_search);
        mSearchInput.setSelected(false);
        mStockItemsList = (ListView) findViewById(R.id.stock_items);
    }

    @Override
    protected void setListeners() {
        super.setListeners();

        /**
         * Enabling Search Filter
         * */
        mSearchInput.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                // Filter by Name and SKU
                mAdapter.getFilter().filter(cs.toString());
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
    public void onResume(){
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        super.onResume();
    }
}
