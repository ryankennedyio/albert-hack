package cba.hackathon.albertapp.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import cba.hackathon.albertapp.R;


public class LoginActivity extends AppCompatActivity {

    private Button mLoginBtn;
    private EditText mEditPin;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initResources();
        setListeners();
    }

    void initResources() {
        mLoginBtn = (Button) findViewById(R.id.btn_login);
        mEditPin = (EditText) findViewById(R.id.edit_pin);
    }

    void setListeners() {
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
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