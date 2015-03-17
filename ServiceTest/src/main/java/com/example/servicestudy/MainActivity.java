package com.example.servicestudy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import com.example.servicestudy.localservice.activity.LocalActivity;
import com.example.servicestudy.servicelifecycle.activity.LifeCycleActivity;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void toLifeCycle(View v) {
        startActivity(new Intent(this, LifeCycleActivity.class));
    }

    public void toLocal(View v) {
        startActivity(new Intent(this, LocalActivity.class));
    }
}
