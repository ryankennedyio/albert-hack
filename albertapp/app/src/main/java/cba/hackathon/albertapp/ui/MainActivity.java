package cba.hackathon.albertapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cba.hackathon.albertapp.R;

public class MainActivity extends BaseActivity {

    private Button mLoginBtn;
    private Button mLookupBtn;
    private Button mDoneBtn;

    @Override
         protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initResources();
        setListeners();
    }

    @Override
    void initResources() {
        mLoginBtn = (Button) findViewById(R.id.btn_login);
        mLookupBtn = (Button) findViewById(R.id.btn_lookup_item);
        mDoneBtn = (Button) findViewById(R.id.btn_done);
    }

    @Override
    void setListeners() {
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        mLookupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LookupItemActivity.class);
                startActivity(intent);
            }
        });

        mDoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ConfirmActivity.class);
                startActivity(intent);
            }
        });
    }


}
