package com.wang.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.wang.R;

/**
 * Created by 28724 on 2018/3/26.
 */

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        getSupportActionBar().hide();

    }
}
