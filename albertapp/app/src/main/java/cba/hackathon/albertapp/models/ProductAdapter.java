package cba.hackathon.albertapp.models;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import cba.hackathon.albertapp.R;

/**
 * Created by Konrad on 23/07/2015.
 * An adapter to allow searching (filter) by product name or SKU in lookupitemactivity
 */
public class ProductAdapter extends BaseAdapter implements Filterable {
    private Context mContext;
    private ProductList mProductList;
    private ArrayList<Product> mProductArray;
    private ProductsFilter mProductsFilter;
    private boolean lookUpActivity;

    public ProductAdapter(Context context, ProductList productList, boolean lookUp) {
        lookUpActivity=lookUp;
        mContext = context;
        mProductList = productList;
        mProductArray = productList.getProducts();
    }

    @Override
    public int getCount() {
        return mProductArray.size();
    }

    @Override
    public Object getItem(int position) {
        return mProductArray.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view;

        Product product = mProductArray.get(position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.search_item, null);
        } else {
            view = convertView;
        }

        //Load the image into thumbnail
        ImageView thumbnail = (ImageView) view.findViewById(R.id.product_thumbnail);
        Picasso
                .with(view.getContext())
                .load(product.getImagePath())
                .into(thumbnail);

        // Set contact name and number
        TextView productName = (TextView) view.findViewById(R.id.product_name);
        TextView productSKU = (TextView) view.findViewById(R.id.product_sku);
        TextView productPrice = (TextView) view.findViewById(R.id.product_price);
        TextView productPriceBig = (TextView) view.findViewById(R.id.product_price_big);
        TextView productQuantity = (TextView) view.findViewById(R.id.product_qty);
        Button deleteProduct = (Button) view.findViewById(R.id.btn_delete_item);

        deleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Log.d("test","click listener position: "+position);
            }
        });


        productName.setText(product.title);
        productSKU.setText(product.sku);
        productPrice.setText(String.format("%.2f", product.price));
        productPriceBig.setText(String.format("%.2f", product.price));

        if( lookUpActivity ){
            ViewGroup layout = (ViewGroup) deleteProduct.getParent();
            if(layout!=null) {
                layout.removeView(deleteProduct);
                //layout.removeView(productQuantity);
            }
        }else{
            ViewGroup layout = (ViewGroup) deleteProduct.getParent();
            if(layout!=null) {
                layout.removeView(productPriceBig);
            }
        }

        return view;
    }

    @Override
    public Filter getFilter() {
        if (mProductsFilter == null)
            mProductsFilter = new ProductsFilter();

        return mProductsFilter;
    }

    private class ProductsFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint == null || constraint.length() == 0) {
                results.values = mProductList.getProducts();
                results.count = mProductList.size();
            } else {
                ArrayList<Product> filteredProducts = new ArrayList<>();

                for (Product p : mProductList.getProducts()) {
                    if (p.title.toLowerCase().startsWith(constraint.toString().toLowerCase())
                            || p.sku.startsWith(constraint.toString())) {
                        filteredProducts.add(p);
                    }
                }

                results.values = filteredProducts;
                results.count = filteredProducts.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mProductArray = (ArrayList<Product>) results.values;
            notifyDataSetChanged();
        }
    }
}
