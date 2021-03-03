package com.kingsoft.shiyou.omnisdk.demo.java;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.kingsoft.shiyou.omnisdk.api.OmniSDK;
import com.kingsoft.shiyou.omnisdk.demo.R;

public class JavaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java);
        OmniSDK.getInstance().onCreate(this, savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        OmniSDK.getInstance().onStart(this);
    }


}