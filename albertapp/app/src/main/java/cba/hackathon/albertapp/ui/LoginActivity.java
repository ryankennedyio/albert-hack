package cba.hackathon.albertapp.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cba.hackathon.albertapp.App;
import cba.hackathon.albertapp.R;


public class LoginActivity extends AppCompatActivity {

    private Button mLoginBtn;
    private EditText mEditPin;

    private App mApp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        initResources();
        setListeners();
    }

    private void initResources() {
        mLoginBtn = (Button) findViewById(R.id.btn_login);
        mEditPin = (EditText) findViewById(R.id.edit_pin);
        mEditPin.setRawInputType(Configuration.KEYBOARD_QWERTY);
        mEditPin.requestFocus();
        mEditPin.requestFocusFromTouch();
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditPin, InputMethodManager.SHOW_IMPLICIT);

        mApp = ((App) getApplicationContext());
    }

    private void setListeners() {
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkLoginValidAndFinish(view);
            }
        });

        mEditPin.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    checkLoginValidAndFinish(v);
                    return true;
                }
                return false;
            }
        });
    }

    private void checkLoginValidAndFinish(View view) {
        String input = mEditPin.getText().toString();
        if (input != null && input.length() != 0) {
            mApp.setUser(input);
            Toast.makeText(
                    view.getContext(),
                    "Logged in as: " + mApp.getUser(),
                    Toast.LENGTH_SHORT
            ).show();
            exitActivity();
        } else {
            Toast.makeText(
                    view.getContext(),
                    "Login Invalid/Missing",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    private void exitActivity() {
        finish();
        LoginActivity.this.overridePendingTransition(R.anim.fade_in, R.anim.no_animation);
    }
}
