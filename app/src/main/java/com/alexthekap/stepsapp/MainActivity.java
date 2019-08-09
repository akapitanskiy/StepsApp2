package com.alexthekap.stepsapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvStepsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvStepsList = findViewById(R.id.rv_stepsList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvStepsList.setLayoutManager(linearLayoutManager);
    }
}
