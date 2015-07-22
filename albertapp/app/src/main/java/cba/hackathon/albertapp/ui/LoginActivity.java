package cba.hackathon.albertapp.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import cba.hackathon.albertapp.R;


public class LoginActivity extends AppCompatActivity {

    private Button mLoginBtn;
    private EditText mEditPin;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        initResources();
        setListeners();
    }

    protected void initResources() {
        mLoginBtn = (Button) findViewById(R.id.btn_login);
        mEditPin = (EditText) findViewById(R.id.edit_pin);
        mEditPin.setRawInputType(Configuration.KEYBOARD_QWERTY);
        mEditPin.requestFocus();
        mEditPin.requestFocusFromTouch();
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditPin, InputMethodManager.SHOW_IMPLICIT);
    }

    protected void setListeners() {
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
